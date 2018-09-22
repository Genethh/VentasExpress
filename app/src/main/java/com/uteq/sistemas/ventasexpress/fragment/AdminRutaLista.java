package com.uteq.sistemas.ventasexpress.fragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uteq.sistemas.ventasexpress.R;
import com.uteq.sistemas.ventasexpress.adapter.RutaListAdapter;
import com.uteq.sistemas.ventasexpress.model.Ruta;
import com.uteq.sistemas.ventasexpress.utils.Constants;
import com.uteq.sistemas.ventasexpress.utils.WebServ.Asynchtask;
import com.uteq.sistemas.ventasexpress.utils.WebServ.WebService;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminRutaLista extends Fragment  implements Asynchtask {

    Fragment fragment;
    private FragmentTransaction fragmentTransaction;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Ruta> ruta;
    private View view;
    private String titulo;

    @BindView(R.id.rvrutalista)
    RecyclerView recyclerView;

    @BindView(R.id.txttitulorutalist)
    TextView textotitulo;

/*    @BindView(R.id.lblIDErutanueva)
    TextView ide;*/

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    public AdminRutaLista() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.admin_ruta_lista, container, false);

        ButterKnife.bind(this, view);
        Bundle b = getArguments();
        Constants.ide = b.getString("ide");
        titulo= b.getString("nombre");


        textotitulo.setText("Lista de rutas de "+titulo);
        //ide.setText(idempleado);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bu = new Bundle();
                bu.putString("idempleadorutan", Constants.ide);
               // bu.putString("idrutan", id.getText().toString());
                fragment = new AdminRutaNueva();
                fragmentTransaction = Constants.fragmentManager.beginTransaction().addToBackStack(null);
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
            }
        });

        linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        ConectWS();

        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorAccent);//Aqui podemos cponer los colores que queremos
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        ConectWS();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
        return view;
    }

    private void ConectWS() {
        Map<String, String> params = new HashMap<>();
        //params.put("listar", Constants.empleado.getIdentificacion());
        params.put("empleado",  Constants.ide);//ide.getText().toString());
        WebService ws = new WebService(Constants.WS_RUTA, params, view.getContext(), (Asynchtask) AdminRutaLista.this);
        ws.execute("");
    }

    @Override
    public void processFinish(String result) throws JSONException {
        Log.e("mmmmmmm", result);
        JSONArray objdataarray = new JSONArray(result);
        ruta = Ruta.JsonObjectsBuild(objdataarray);
        RutaListAdapter adapter = new RutaListAdapter(view.getContext(), ruta);
        recyclerView.setAdapter(adapter);

        // ProductoAdapter adapater = new ProductoAdapter(view.getContext(), productos,id);
    }

}
