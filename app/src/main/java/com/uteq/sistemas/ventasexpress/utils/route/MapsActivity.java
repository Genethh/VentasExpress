package com.uteq.sistemas.ventasexpress.utils.route;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import com.uteq.sistemas.ventasexpress.R;
import com.uteq.sistemas.ventasexpress.adapter.PedidoAdapter;
import com.uteq.sistemas.ventasexpress.fragment.VendedorPedidosFragment;
import com.uteq.sistemas.ventasexpress.model.Cliente;
import com.uteq.sistemas.ventasexpress.model.Pedido;
import com.uteq.sistemas.ventasexpress.utils.Constants;
import com.uteq.sistemas.ventasexpress.utils.WebServ.Asynchtask;
import com.uteq.sistemas.ventasexpress.utils.WebServ.WebService;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements Asynchtask{

    private Boolean EMULATOR; // To slow down app. if running on Emulator (see "emulator" in manifest)
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private final int THRESHOLD = 30; // Max number of destinations on the map
    private boolean solveInProgress = false; // Flag for GA_Task or SA_Task being in progress
    private AsyncTask solverTask; // Reference to the GA_Task or SA_Task that is in progress
    private ArrayList<Polyline> polylines = new ArrayList();
    private int publishInterval = 333; // defines publishing rate in milliseconds

    // initialize options drawer
    private SharedPreferences mSharedPreferences;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ArrayList<Pedido> pedidos;

    protected void onCreate(Bundle savedInstanceState) {

        // Setup map
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setMyLocationEnabled(true);

        // Zoom to Current Location
        // Get LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();                                     // Create a criteria object to retrieve provider
        String provider = locationManager.getBestProvider(criteria, true);      // Get the name of the best provider
        double latitude =  -1.0126492;                                           // Get latitude of the current location
        double longitude = -79.4702318;                                         // Get longitude of the current location
        LatLng latLng = new LatLng(latitude, longitude);                        // Create a LatLng object for the current location
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));                 // Show the current location in Google Map
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13));                     // Zoom in the Google Map

        // Setup options & drawer
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        addDrawerItems(R.array.menuItems, mDrawerList);
        mDrawerList.setOnItemClickListener(new SideDrawerClickListener());

        // Adjust application behaviour based on emulator or device (see "emulator" in manifest)
        ApplicationInfo ai = null;
        try {
            ai = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            EMULATOR = bundle.getBoolean("emulator", false);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        ConectWSPedidos();
    }

    private void ConectWSPedidos() {
        Map<String, String> params = new HashMap<>();
        params.put("empleado", Constants.empleado.getIdentificacion());
        WebService ws = new WebService(Constants.WS_PEDIDO, params, this, (Asynchtask) MapsActivity.this);
        ws.execute("");
    }

    @Override
    public void processFinish(String result) throws JSONException {
        Log.e("Resultado", result);
        JSONArray objdataarray = new JSONArray(result);
        pedidos = Pedido.JsonObjectsBuild(objdataarray);
        Marker m = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(-1.0126492,-79.4702318))
                .title("Ubicación Actual"));
        TourManager.addDestination(new Destination(m));
        for(int i =0; i<pedidos.size();i++) {
            Pedido pedido = pedidos.get(i);
            double lat = Double.parseDouble(pedido.getLatlong().split(",")[0]);
            double lon = Double.parseDouble(pedido.getLatlong().split(",")[1]);
            TourManager.addDestination(new Destination( mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lat,lon))
                    .title(pedido.getNegocio()))));
        }

        if (TourManager.numberOfDestinations()==0 || solveInProgress) return;

        GA_Task task = new GA_Task();
        solverTask = task;
        task.execute();

        System.gc();
    }
    //Helper Method
    private void addDrawerItems(int listArr, ListView view ) {
        String[] mOptionsNames;
        mOptionsNames = getResources().getStringArray(listArr);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mOptionsNames);
        view.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private class SideDrawerClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    //** Swaps fragments in the main content view */
    private void selectItem(int position) {
        switch (position) {
            case 0:
                // Contact Info

                break;
            case 1:
                // What is SA

                break;
            case 2:
                // What is GA

                break;
            case 3:
                // Options
                break;
        }
    }

    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link com.google.android.gms.maps.SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap =((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
/*
        // Setting a click event handler for the map
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                if (TourManager.numberOfDestinations() >= THRESHOLD || solveInProgress) {
                    return;
                }
                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);

                // Setting the title for the marker.
                // This will be displayed on taping the marker
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);

//                // Animating to the touched position
//                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Placing a marker on the touched position
                Marker marker = mMap.addMarker(markerOptions);
                // Add the new Destination/Marker to TourManager and to mapMarkers
                TourManager.addDestination(new Destination(marker));
            }
        });

        // Disable clicking on markers
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return true;
            }
        });
*/
    }

    public void clearMap(View view) {

        // if Async in progress, need to press twice to clear map
        if (solveInProgress) solverTask.cancel(true);
        else {
            mMap.clear();
            TourManager.removeAll();
            System.gc();
        }
    }

    public void graphMap(Tour tour) {

        ArrayList<Marker> markers = new ArrayList<Marker>();
        for (Destination D: tour.getAllDest()) {
            markers.add(D.getMarker());
        }

        if (markers.size()==0) return;

        // remove existing polylines
        for(Polyline pl : polylines) {
            pl.remove();
        }

        ArrayList<LatLng> latLngs = new ArrayList<>();
        for (Marker M: markers) {
            latLngs.add(M.getPosition());
        }
        latLngs.add(markers.get(0).getPosition());
        PolylineOptions poly = new PolylineOptions().addAll(latLngs);

        polylines.add(mMap.addPolyline(poly));
    }

    public void TSP_SA(View view) {
        if (TourManager.numberOfDestinations()==0 || solveInProgress) return;

        SA_Task task = new SA_Task();
        solverTask = task;
        task.execute();

        System.gc();
    }

    public void TSP_GA(View view) {
        if (TourManager.numberOfDestinations()==0 || solveInProgress) return;

        GA_Task task = new GA_Task();
        solverTask = task;
        task.execute();

        System.gc();
    }

    // solves and displays TSP using GA
    class GA_Task extends AsyncTask<Void, Tour, Population> {

        Tour bestTourSoFar;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            solveInProgress = true;

        }

        @Override
        protected Population doInBackground(Void... voids) {
            // Initialization
            int popSize = Integer.parseInt(mSharedPreferences.getString("popSize", "50"));
            int generations = Integer.parseInt(mSharedPreferences.getString("generations", "200"));

            Population pop = new Population(popSize, true);
            Tour fittest = pop.getFittest();
            publishProgress(fittest);

            long time = System.currentTimeMillis();
            long lastPublishTime = time;

            for (int i = 0; i < generations; i++) {
                if ( isCancelled() ) break;

                time = System.currentTimeMillis();
                pop = GA.evolvePopulation(pop, mSharedPreferences);
                bestTourSoFar = new Tour(pop.getFittest());
                if (time - lastPublishTime > publishInterval) {
                    lastPublishTime = time;
                    publishProgress(pop.getFittest());
                }

                if (EMULATOR) {
                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            return pop;
        }

        @Override
        protected void onProgressUpdate(Tour... tours) {
            super.onProgressUpdate(tours[0]);
            Tour currentBest = tours[0];
            graphMap(currentBest);
            System.out.println("Current distance: " + currentBest.getDistance());
        }

        @Override
        protected void onPostExecute(Population pop) {
            super.onPostExecute(pop);
            Tour fittest = pop.getFittest();
            graphMap(fittest);
            System.out.println("GA Final distance: " + pop.getFittest().getDistance());

            // Display final distance
            TextView tv1 = (TextView) findViewById(R.id.final_distance);
            int finalDistance = (int) pop.getFittest().getDistance();
            tv1.setText("Distancia: " + finalDistance + " km");


            pop = null;
            solveInProgress = false;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

            // Display final distance
            TextView tv1 = (TextView) findViewById(R.id.final_distance);
            int finalDistance = (int) bestTourSoFar.getDistance();
            tv1.setText("Distancia: " + finalDistance + " km");

            solveInProgress = false;
        }
    }

    // solves and displays TSP using SA
    class SA_Task extends AsyncTask<Void, Void, Void> {

        volatile Tour current;
        volatile Tour best;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            solveInProgress = true;

        }

        @Override
        protected Void doInBackground(Void... voids) {

            // Initialization
            double temp = Double.parseDouble(mSharedPreferences.getString("temperature", "1000000000"));
            double coolingRate = Double.parseDouble(mSharedPreferences.getString("coolingRate", "0.025f"));

            long time = System.currentTimeMillis();
            long lastPublishTime = time;
            
            current = new Tour();
            current.generateIndividual();
            System.out.println("Initial distance: " + current.getDistance());
            best = new Tour(current);
            publishProgress(); // Initial graph

            while (temp > 1) {
                if ( isCancelled() ) break;

                time = System.currentTimeMillis();

                Tour newSolution = new Tour(current);
                newSolution.mutateIndividual();

                double currentEnergy = current.getDistance();
                double neighbourEnergy = newSolution.getDistance();

                // Decide if we should accept the neighbour
                if (TSP_SA.acceptanceProbability(currentEnergy, neighbourEnergy, temp) > Math.random()) {
                    current = new Tour(newSolution);
                }

                // Keep track of the best solution found
                if (current.getDistance() < best.getDistance()) {
                    best = current;
                }

                if (time - lastPublishTime > publishInterval) {
                    lastPublishTime = time;
                    publishProgress();
                }

                if (EMULATOR) {
                    try {
                        Thread.sleep(0, 5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                temp *= 1 - coolingRate;
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            System.out.println("Current distance: " + best.getDistance());
            graphMap(best);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            graphMap(best);
            System.out.println("SA Final distance: " + best.getDistance());

            // Display final distance
            TextView tv1 = (TextView) findViewById(R.id.final_distance);
            int finalDistance = (int) best.getDistance();
            tv1.setText("Distancia: " + finalDistance + " km");

            solveInProgress = false;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

            graphMap(best);
            System.out.println("SA onCancel distance: " + best.getDistance());

            // Display final distance
            TextView tv1 = (TextView) findViewById(R.id.final_distance);
            int finalDistance = (int) best.getDistance();
            tv1.setText("Distancia: " + finalDistance + " km");

            solveInProgress = false;
        }
    }
}
