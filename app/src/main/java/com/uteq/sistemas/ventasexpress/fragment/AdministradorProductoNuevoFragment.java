package com.uteq.sistemas.ventasexpress.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.uteq.sistemas.ventasexpress.R;
import com.uteq.sistemas.ventasexpress.adapter.CategoriaAdapter;
import com.uteq.sistemas.ventasexpress.model.Categoria;
import com.uteq.sistemas.ventasexpress.utils.Constants;
import com.uteq.sistemas.ventasexpress.utils.WebServ.Asynchtask;
import com.uteq.sistemas.ventasexpress.utils.WebServ.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdministradorProductoNuevoFragment extends Fragment implements Asynchtask {

    private LinearLayoutManager linearLayoutManager;
    private View view;
    private String id,detalle;

    @BindView(R.id.BtnAceptar)
    Button btnAceptar;
    @BindView(R.id.BtnCancelar)
    Button btnCancelar;

    @BindView(R.id.txtCodigo)
    TextView txtCodigo;
    @BindView(R.id.txtNombre)
    TextView txtNombre;
    @BindView(R.id.txtStockMaximo)
    TextView txtStockMaximo;
    @BindView(R.id.txtStockMinimo)
    TextView txtStockMinimo;
    @BindView(R.id.txtCantidadP)
    TextView txtCantidadP;
    @BindView(R.id.txtPrecio)
    TextView txtPrecio;
    @BindView(R.id.txtObservacion)
    TextView txtObservacion;

    // @BindView(R.id.txtcodigocat)
    @BindView(R.id.txtcodigocat)
    TextView txtcodigocat;
    @BindView(R.id.txtcat)
    TextView txtcat;


    public AdministradorProductoNuevoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_administrador_producto_nuevo, container, false);
        ButterKnife.bind(this, view);

        Bundle b = getArguments();
        txtcodigocat = (TextView)view.findViewById(R.id.txtcodigocat);
        txtcodigocat.setText(b.getString("codigo"));
        b = getArguments();
        txtcat = (TextView)view.findViewById(R.id.txtcat);
        txtcat.setText(b.getString("detalle"));

        //linearLayoutManager = new LinearLayoutManager(view.getContext());
        //linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);



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
        if (txtCodigo.getText().toString().length()>0 &  txtNombre.getText().toString().length()>0 &
                txtStockMaximo.getText().toString().length()>0 &  txtStockMinimo.getText().toString().length()>0 &
                txtCantidadP.getText().toString().length()>0 &  txtPrecio.getText().toString().length()>0
                &  txtObservacion.getText().toString().length()>0)
        {
            params.put("agregar", "1");
            params.put("codigo", txtCodigo.getText().toString());
            params.put("codigo_categoria",  txtcodigocat.getText().toString());
            params.put("nombre", txtNombre.getText().toString().toUpperCase());
            params.put("stock_maximo", txtStockMaximo.getText().toString());
            params.put("stock_minimo", txtStockMinimo.getText().toString());
            params.put("precio_publico",txtPrecio.getText().toString());
            params.put("observacion", txtObservacion.getText().toString().toUpperCase());
            params.put("cantidad", txtCantidadP.getText().toString());
            WebService ws = new WebService(Constants.WS_PRODUCTO, params, view.getContext(), (Asynchtask) AdministradorProductoNuevoFragment.this);
            ws.execute("");
        }else{
            Toast.makeText(getContext(), "Campos vacios - Ingrese los datos", Toast.LENGTH_SHORT).show();
        }

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
