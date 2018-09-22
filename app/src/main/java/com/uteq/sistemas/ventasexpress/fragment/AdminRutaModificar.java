package com.uteq.sistemas.ventasexpress.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class AdminRutaModificar extends Fragment implements Asynchtask {

    private View view;

    @BindView(R.id.BtnAceptar)
    Button btnAceptar;
    @BindView(R.id.BtnCancelar)
    Button btnCancelar;
    @BindView(R.id.txtidrutam)
    TextView txtid;
    @BindView(R.id.txtDiaRutam)
    TextView txtdia;

    @BindView(R.id.txtidempl)
    TextView idempl;

    private String idempleado,id,dia,sector,estado,ide;
    public AdminRutaModificar() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.admin_ruta_modificar, container, false);
        ButterKnife.bind(this, view);
        Bundle b = getArguments();
        id = b.getString("id");
        ide = b.getString("ide");
        dia = b.getString("dia");
        //lblcodigo=(TextView) view.findViewById(R.id.lblCodigo);

        txtid.setText(id);
        txtdia.setText(dia);
        idempl.setText(ide);

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
        if ( txtdia.getText().toString().length()>0){

            params.put("editar", "1");
            params.put("id", txtid.getText().toString());
            params.put("id_empleado", idempl.getText().toString());
            params.put("dia",  txtdia.getText().toString().toUpperCase());
            WebService ws = new WebService(Constants.WS_RUTA, params, view.getContext(), (Asynchtask) AdminRutaModificar.this);
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
        if (r.equals("1")){
            Toast.makeText(getContext(), "Datos guardados correctamente", Toast.LENGTH_SHORT).show();
            txtdia.setText("");
            getFragmentManager().popBackStack();
        }
    }

}
