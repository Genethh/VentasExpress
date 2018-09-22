package com.uteq.sistemas.ventasexpress.fragment;


import android.content.Intent;
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
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.uteq.sistemas.ventasexpress.R;
import com.uteq.sistemas.ventasexpress.activity.MapsActivity;
import com.uteq.sistemas.ventasexpress.adapter.ClienteAdapter;
import com.uteq.sistemas.ventasexpress.model.Cliente;
import com.uteq.sistemas.ventasexpress.utils.Constants;
import com.uteq.sistemas.ventasexpress.utils.WebServ.Asynchtask;
import com.uteq.sistemas.ventasexpress.utils.WebServ.WebService;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class VendedorRutaClientesFragment extends Fragment implements Asynchtask {

    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Cliente> clientes;
    private View view;
    private String id;

    @BindView(R.id.rvClientesR)
    RecyclerView recyclerView;

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.BtnAgregar)
    Button BtnAgregar;

    public VendedorRutaClientesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ruta_clientes, container, false);
        ButterKnife.bind(this, view);
        Bundle b = getArguments();
        id = Constants.idruta = b.getString("id");
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        ConectWSClientesR();
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext().getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        });
        BtnAgregar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Agregar();
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorAccent);//Aqui podemos cponer los colores que queremos
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        ConectWSClientesR();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
        return view;
    }

    private void Agregar() {
        Bundle b = new Bundle();
        b.putString("id", id);
        VendedorRutaAgregarFragment fragment = new VendedorRutaAgregarFragment();
        fragment.setArguments(b);
        FragmentTransaction fragmentTransaction = Constants.fragmentManager.beginTransaction().addToBackStack(null);
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }

    private void ConectWSClientesR() {
        Map<String, String> params = new HashMap<>();
        params.put("id_ruta", id);
        WebService ws = new WebService(Constants.WS_CLIENTE, params, view.getContext(), (Asynchtask) VendedorRutaClientesFragment.this);
        ws.execute("");
    }

    @Override
    public void processFinish(String result) throws JSONException {
        Log.e("Resultado: ",result);
        JSONArray objdataarray = new JSONArray(result);
        clientes = Cliente.JsonObjectsBuild(objdataarray);
        ClienteAdapter adapater = new ClienteAdapter(view.getContext(), clientes);
        recyclerView.setAdapter(adapater);
    }
}