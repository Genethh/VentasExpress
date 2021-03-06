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
import com.uteq.sistemas.ventasexpress.adapter.DetallePedidoAdapter;
import com.uteq.sistemas.ventasexpress.adapter.DetallePedidoClienteAdapter;
import com.uteq.sistemas.ventasexpress.model.DetallePedido;
import com.uteq.sistemas.ventasexpress.utils.Constants;
import com.uteq.sistemas.ventasexpress.utils.WebServ.Asynchtask;
import com.uteq.sistemas.ventasexpress.utils.WebServ.WebService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClientePedidoDetalleFragment extends Fragment implements Asynchtask {
    private LinearLayoutManager linearLayoutManager;
    private View view;
    private String id;
    private ArrayList<DetallePedido> productos;

    Fragment fragment;
    private FragmentTransaction fragmentTransaction;

    @BindView(R.id.LblNegocio)
    TextView negocio;

    @BindView(R.id.lblEstado)
    TextView estado;

    @BindView(R.id.lblFecha)
    TextView fecha;

    @BindView(R.id.rvClientesR)
    RecyclerView recyclerView;

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    public ClientePedidoDetalleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pedido_detalle_cliente, container, false);
        ButterKnife.bind(this, view);
        Bundle b = getArguments();
        id = b.getString("id");
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        ConectWSClientesR();
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Pasar Datos
                // Creamos un nuevo Bundle
                Bundle args = new Bundle();
                // Colocamos el String
                args.putString("id_pedido", id);


                fragment = new ClienteCategoriasProductoFragment();
                fragment.setArguments(args);
                fragmentTransaction = Constants.fragmentManager.beginTransaction().addToBackStack(null);
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
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

    private void ConectWSClientesR() {
        Map<String, String> params = new HashMap<>();
        params.put("id_pedido", id);
        WebService ws = new WebService(Constants.WS_PEDIDO, params, view.getContext(), (Asynchtask) ClientePedidoDetalleFragment.this);
        ws.execute("");
    }

    @Override
    public void processFinish(String result) throws JSONException {
        Log.e("Resultado: ", result);
        JSONObject obj = new JSONObject(result);
        negocio.setText("Cliente: " + obj.getString("negocio"));
        estado.setText("Estado: " + obj.getString("estado"));
        fecha.setText("Fecha: " + obj.getString("fecha_inicio"));
        productos = DetallePedido.JsonObjectsBuild(obj.getJSONArray("detalle"));
        DetallePedidoClienteAdapter adapater = new DetallePedidoClienteAdapter(view.getContext(), productos, id);
        recyclerView.setAdapter(adapater);
    }
}
