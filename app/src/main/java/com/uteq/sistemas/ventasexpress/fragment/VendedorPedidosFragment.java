package com.uteq.sistemas.ventasexpress.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uteq.sistemas.ventasexpress.R;
import com.uteq.sistemas.ventasexpress.activity.LoginActivity;
import com.uteq.sistemas.ventasexpress.activity.VendedorActivity;
import com.uteq.sistemas.ventasexpress.adapter.PedidoAdapter;
import com.uteq.sistemas.ventasexpress.model.Pedido;
import com.uteq.sistemas.ventasexpress.utils.Constants;
import com.uteq.sistemas.ventasexpress.utils.WebServ.Asynchtask;
import com.uteq.sistemas.ventasexpress.utils.WebServ.WebService;
import com.uteq.sistemas.ventasexpress.utils.route.MapsActivity;

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
public class VendedorPedidosFragment extends Fragment implements Asynchtask {


    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Pedido> pedidos;
    private View view;
    private String id;

    @BindView(R.id.rvPedidos)
    RecyclerView recyclerView;

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    public VendedorPedidosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_vendedor_pedidos, container, false);
        ButterKnife.bind(this, view);
        Bundle b = getArguments();
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        ConectWSPedidos();
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext().getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorAccent);//Aqui podemos cponer los colores que queremos
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        ConectWSPedidos();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
        return view;

    }

    private void ConectWSPedidos() {
        Map<String, String> params = new HashMap<>();
        params.put("empleado", Constants.empleado.getIdentificacion());
        WebService ws = new WebService(Constants.WS_PEDIDO, params, view.getContext(), (Asynchtask) VendedorPedidosFragment.this);
        ws.execute("");
    }

    @Override
    public void processFinish(String result) throws JSONException {
        Log.e("Resultado", result);
        JSONArray objdataarray = new JSONArray(result);
        pedidos = Pedido.JsonObjectsBuild(objdataarray);
        PedidoAdapter adapater = new PedidoAdapter(view.getContext(), pedidos);
        recyclerView.setAdapter(adapater);
    }
}