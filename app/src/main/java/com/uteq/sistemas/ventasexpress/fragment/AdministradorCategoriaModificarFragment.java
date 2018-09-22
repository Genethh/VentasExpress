package com.uteq.sistemas.ventasexpress.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.uteq.sistemas.ventasexpress.R;
import com.uteq.sistemas.ventasexpress.utils.Constants;
import com.uteq.sistemas.ventasexpress.utils.WebServ.Asynchtask;
import com.uteq.sistemas.ventasexpress.utils.WebServ.WebService;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdministradorCategoriaModificarFragment extends Fragment implements Asynchtask {
    private LinearLayoutManager linearLayoutManager;
    private View view;
    private String id,detalle;

    Fragment fragment;
    private FragmentTransaction fragmentTransaction;

    @BindView(R.id.BtnAceptar)
    Button btnAceptar;
    @BindView(R.id.BtnCancelar)
    Button btnCancelar;

    @BindView(R.id.lblCodigo)
    TextView lblcodigo;
    @BindView(R.id.txtMCategoria)
    TextView txtCategoria;

    public AdministradorCategoriaModificarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.admin_categoria_modificar, container, false);

        ButterKnife.bind(this, view);
        Bundle b = getArguments();
        id = b.getString("id");
        detalle = b.getString("detalle");
        lblcodigo=(TextView) view.findViewById(R.id.lblCodigo);
        txtCategoria=(TextView) view.findViewById(R.id.txtMCategoria);
        lblcodigo.setText(id);
        txtCategoria.setText(detalle);
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        btnAceptar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        return view;
    }

    public void login() {
        Map<String, String> params = new HashMap<>();
        params.put("editar", "1");
        params.put("codigo", id);
        params.put("detalle", txtCategoria.getText().toString().toUpperCase());
        WebService ws = new WebService(Constants.WS_CATEGORIA, params, view.getContext(), (Asynchtask) AdministradorCategoriaModificarFragment.this);
        ws.execute("");
    }


    @Override
    public void processFinish(String result) throws JSONException, ParseException {
        Log.e("Resultado", result);
        JSONObject obj = new JSONObject(result);
        String r = obj.getString("resultado");
        if (r=="1"){
            Toast.makeText(getContext(), "Datos guardados correctamente", Toast.LENGTH_SHORT).show();
            getFragmentManager().popBackStack();
        }
    }
}


