package com.uteq.sistemas.ventasexpress.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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
import com.uteq.sistemas.ventasexpress.adapter.CategoriaAdapter;
import com.uteq.sistemas.ventasexpress.adapter.ClienteCategoriaProductoAdapter;
import com.uteq.sistemas.ventasexpress.model.Categoria;
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

public class ClienteCategoriasProductoFragment extends Fragment  implements Asynchtask {

    Fragment fragment;
    private FragmentTransaction fragmentTransaction;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Categoria> categorias;
    private View view;
    private String id_pedido;

    @BindView(R.id.rvCategorias)
    RecyclerView recyclerView;

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeRefreshLayout;



    public ClienteCategoriasProductoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cliente_categorias_producto, container, false);

        ButterKnife.bind(this, view);
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        Bundle b = getArguments();
        id_pedido = b.getString("id_pedido");

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
        Map<String, String> params = new HashMap<>(); //HashMap<String, String>();
        params.put("lista", "1");
        WebService ws = new WebService(Constants.WS_CATEGORIA, params, view.getContext(), (Asynchtask) ClienteCategoriasProductoFragment.this);
        ws.execute("");
    }

    @Override
    public void processFinish(String result) throws JSONException {
        Log.e("Resultado", result);
        JSONArray objdataarray = new JSONArray(result);
        categorias = Categoria.JsonObjectsBuild(objdataarray);
        ClienteCategoriaProductoAdapter adapater = new ClienteCategoriaProductoAdapter(view.getContext(), categorias, id_pedido);
        recyclerView.setAdapter(adapater);
    }

}
