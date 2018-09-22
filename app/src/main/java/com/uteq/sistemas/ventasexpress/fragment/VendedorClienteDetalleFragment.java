package com.uteq.sistemas.ventasexpress.fragment;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.uteq.sistemas.ventasexpress.R;
import com.uteq.sistemas.ventasexpress.utils.Constants;
import com.uteq.sistemas.ventasexpress.utils.WebServ.Asynchtask;
import com.uteq.sistemas.ventasexpress.utils.WebServ.WebService;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class VendedorClienteDetalleFragment extends Fragment  implements Asynchtask {
    private static final String[] INITIAL_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_CONTACTS
    };
    private static final String[] LOCATION_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static final int INITIAL_REQUEST=1337;
    private static final int LOCATION_REQUEST=INITIAL_REQUEST+3;

    private View view;


    @BindView(R.id.BtnAceptar)
    Button btnAceptar;
    @BindView(R.id.BtnCancelar)
    Button btnCancelar;
    @BindView(R.id.BtnEditar)
    Button btnEditar;

    @BindView(R.id.txtNegocio)
    TextView txtNegocio;
    @BindView(R.id.txtRUC)
    TextView txtRUC;
    @BindView(R.id.txtPropietario)
    TextView txtPropietario;
    @BindView(R.id.txtCorreo)
    TextView txtCorreo;
    @BindView(R.id.txtDireccion)
    TextView txtDireccion;
    @BindView(R.id.txtTelefono)
    TextView txtTelefono;
    @BindView(R.id.txtLatLong)
    TextView txtLatLong;
    private String id;

    public double latitude;
    public double longitude;
    public LocationManager locationManager;
    public Criteria criteria;
    public String bestProvider;

    public VendedorClienteDetalleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_vendedor_cliente_detalle, container, false);
        ButterKnife.bind(this, view);
        Bundle b = getArguments();
        id = b.getString("id");
        ObtenerCliente();
        txtNegocio.setText(id);
        btnEditar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditarLatLong();
            }
        });
        btnAceptar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                GuardarCliente();
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        List<String> items = new ArrayList<String>();
        items.add("lalalal");
        items.add("lalalal");
        String[] array = items.toArray(new String[0]);

        requestPermissions(LOCATION_PERMS, LOCATION_REQUEST);
        return view;
    }

    public void EditarLatLong(){
        if (true) {
            locationManager = (LocationManager)  getContext().getSystemService(Context.LOCATION_SERVICE);
            criteria = new Criteria();
            bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();
            Location location = locationManager.getLastKnownLocation(bestProvider);
            if (location != null) {
                Log.e("TAG", "GPS is on");
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }else{
                Log.e("TAG", "GPS is off");
                latitude =  -1.0126492;
                longitude = -79.4702318;
            }
            txtLatLong.setText("" + latitude +","+longitude);
        }
    }

    public void ObtenerCliente() {
        Map<String, String> params = new HashMap<>();
        params.put("idcliente", id);
        WebService ws = new WebService(Constants.WS_CLIENTE, params, view.getContext(), (Asynchtask) VendedorClienteDetalleFragment.this);
        ws.execute("");
    }

    public void GuardarCliente() {
        Map<String, String> params = new HashMap<>();
        params.put("editar", "1");
        params.put("identificacion", txtRUC.getText().toString());
        params.put("id_empleado", Constants.empleado.getIdentificacion());
        params.put("negocio", txtNegocio.getText().toString());
        params.put("propietario", txtPropietario.getText().toString());
        params.put("email", txtCorreo.getText().toString());
        params.put("telefono", txtTelefono.getText().toString());
        params.put("direccion", txtDireccion.getText().toString());
        params.put("latlong", txtLatLong.getText().toString());
        params.put("estad", "1");
        WebService ws = new WebService(Constants.WS_CLIENTE, params, view.getContext(), (Asynchtask) VendedorClienteDetalleFragment.this);
        ws.execute("");
    }

    @Override
    public void processFinish(String result) throws JSONException, ParseException {
        Log.e("Resultado", result);
        try {
            JSONObject obj = new JSONObject(result);
            String r = obj.getString("resultado");
            if (r == "1") {
                Toast.makeText(getContext(), "Datos guardados correctamente", Toast.LENGTH_SHORT).show();
                getFragmentManager().popBackStack();
            }
        } catch (Exception e) {
            JSONObject obj = new JSONObject(result);
            txtRUC.setText(obj.getString("identificacion"));
            txtNegocio.setText(obj.getString("negocio"));
            txtPropietario.setText(obj.getString("propietario"));
            txtCorreo.setText(obj.getString("email"));
            txtDireccion.setText(obj.getString("direccion"));
            txtTelefono.setText(obj.getString("telefono"));
            txtLatLong.setText(obj.getString("latlong"));
        }
    }
}
