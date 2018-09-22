package com.uteq.sistemas.ventasexpress.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uteq.sistemas.ventasexpress.R;
import com.uteq.sistemas.ventasexpress.adapter.ProductoVendedorAdapter;
import com.uteq.sistemas.ventasexpress.model.Producto;
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
public class VendedorPedidoDetalleAgregarFragment extends Fragment implements Asynchtask {


    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Producto> productos;

    private View view;
    private String idPedido;

    @BindView(R.id.ListaProductos)
    RecyclerView recyclerView;

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeRefreshLayout;


    public VendedorPedidoDetalleAgregarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pedido_detalle_agregar, container, false);
        ButterKnife.bind(this, view);
        Bundle b = getArguments();
        idPedido = b.getString("id_pedido");
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        ConectWSProductos();

        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorAccent);//Aqui podemos cponer los colores que queremos
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        ConectWSProductos();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
        return view;

    }

    private void ConectWSProductos() {
        Map<String, String> params = new HashMap<>();
        params.put("NombreProducto", "todos");
        WebService ws = new WebService(Constants.WS_PRODUCTO, params, view.getContext(), (Asynchtask) VendedorPedidoDetalleAgregarFragment.this);
        ws.execute("");
    }

    @Override
    public void processFinish(String result) throws JSONException {
        Log.e("Resultado", result);
        JSONArray objdataarray = new JSONArray(result);
        productos = Producto.JsonObjectsBuild(objdataarray);
        ProductoVendedorAdapter adapater = new ProductoVendedorAdapter(view.getContext(),productos,idPedido,VendedorPedidoDetalleAgregarFragment.this);
        recyclerView.setAdapter(adapater);
    }
}
