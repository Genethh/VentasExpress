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

import com.uteq.sistemas.ventasexpress.R;
import com.uteq.sistemas.ventasexpress.adapter.CategoriaNuevoProductoAdapter;
import com.uteq.sistemas.ventasexpress.adapter.ProductoAdministradorAdapter;
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
public class AdministradorProductosFragment extends Fragment implements Asynchtask{

    Fragment fragment;
    private FragmentTransaction fragmentTransaction;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Producto> listaproducto;
    private View view;
    private String id;

    @BindView(R.id.rvProductos)
    RecyclerView recyclerView;

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeRefreshLayout;


    public AdministradorProductosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_administrador_productos, container, false);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // fragment = new AdministradorProductoNuevoFragment();
                fragment = new AdministradorCategoriasNuevoProductoFragment();
                fragmentTransaction = Constants.fragmentManager.beginTransaction().addToBackStack(null);
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
            }
        });

        ButterKnife.bind(this, view);
        Bundle b = getArguments();
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        ConectWSProducto();

        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorAccent);//Aqui podemos cponer los colores que queremos
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        ConectWSProducto();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
        return view;
    }

    private void ConectWSProducto() {
        Map<String, String> params = new HashMap<>();
        params.put("NombreProducto", "todos");
        WebService ws = new WebService(Constants.WS_PRODUCTO, params, view.getContext(), (Asynchtask) AdministradorProductosFragment.this);
        ws.execute("");
    }

    @Override
    public void processFinish(String result) throws JSONException {
        Log.e("Resultado", "ffg" + result);
        JSONArray objdataarray = new JSONArray(result);
        listaproducto = Producto.JsonObjectsBuild(objdataarray);
        ProductoAdministradorAdapter adapater = new ProductoAdministradorAdapter(view.getContext(), listaproducto);
        //  CategoriaNuevoProductoAdapter adapater = new CategoriaNuevoProductoAdapter(view.getContext(), listaproducto);
        recyclerView.setAdapter(adapater);
    }

}
