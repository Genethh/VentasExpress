package com.uteq.sistemas.ventasexpress.fragment;


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
import android.widget.TextView;

import com.uteq.sistemas.ventasexpress.R;
import com.uteq.sistemas.ventasexpress.adapter.BonificacionesAdapter;
import com.uteq.sistemas.ventasexpress.model.Bonificacion;
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
public class AdministradorBonificacionesFragment extends Fragment implements Asynchtask {


    Fragment fragment;
    private FragmentTransaction fragmentTransaction;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Bonificacion> bonificacion;
    private View view;
    private String idempleado,titulo;

    @BindView(R.id.rvBonificacion)
    RecyclerView recyclerView;

    @BindView(R.id.lbltextotitulo)
    TextView textotitulo;

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    public AdministradorBonificacionesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_administrador_bonificaciones, container, false);

        ButterKnife.bind(this, view);
        Bundle b = getArguments();
        idempleado = b.getString("ide");
        titulo= b.getString("nombre");

        textotitulo.setText("Lista de Bonificaciones de "+titulo);

        linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        ConectWSBonificacion();

        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorAccent);//Aqui podemos cponer los colores que queremos
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        ConectWSBonificacion();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
        return view;
    }

    private void ConectWSBonificacion() {
        Map<String, String> params = new HashMap<>();
        //params.put("listar", Constants.empleado.getIdentificacion());
        params.put("listar","1");
        params.put("id_empleado", idempleado);
        WebService ws = new WebService(Constants.WS_BONIFICACION, params, view.getContext(), (Asynchtask) AdministradorBonificacionesFragment.this);
        ws.execute("");
    }

    @Override
    public void processFinish(String result) throws JSONException {
        Log.e("mmmmmmm", result);
        JSONArray objdataarray = new JSONArray(result);
        bonificacion = Bonificacion.JsonObjectsBuild(objdataarray);
        BonificacionesAdapter adapater = new BonificacionesAdapter(view.getContext(), bonificacion);
        recyclerView.setAdapter(adapater);

        // ProductoAdapter adapater = new ProductoAdapter(view.getContext(), productos,id);
    }

}
