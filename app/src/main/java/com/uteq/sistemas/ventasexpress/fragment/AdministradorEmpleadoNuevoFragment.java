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
public class AdministradorEmpleadoNuevoFragment extends Fragment implements Asynchtask {


    private View view;

    @BindView(R.id.BtnAceptar)
    Button btnAceptar;
    @BindView(R.id.BtnCancelar)
    Button btnCancelar;

    @BindView(R.id.txtIdentificacion)
    TextView txtIdentificacion;
    @BindView(R.id.txtNombres)
    TextView txtNombres;
    @BindView(R.id.txtApellidos)
    TextView txtApellidos;
    @BindView(R.id.txtCorreo)
    TextView txtCorreo;
    @BindView(R.id.txtTelefono)
    TextView txtTelefono;
    @BindView(R.id.txtDireccion)
    TextView txtDireccion;



    public AdministradorEmpleadoNuevoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_administrador_empleado_nuevo, container, false);
        ButterKnife.bind(this, view);
        btnAceptar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
                loginvendedor();
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

    public void loginvendedor() {
        // ingresar usuario y contrasea para el vendedor
        Map<String, String> parama = new HashMap<>();
        parama.put("agregarUsuario", "1");
        parama.put("usuario", txtIdentificacion.getText().toString());
        parama.put("clave", txtIdentificacion.getText().toString());
        parama.put("tipo", "vendedor");
        WebService wser = new WebService(Constants.WS_EMPLEADO, parama, view.getContext(), (Asynchtask) AdministradorEmpleadoNuevoFragment.this);
        wser.execute("");
    }

    public void login() {
        Map<String, String> params = new HashMap<>();
        if (txtIdentificacion.getText().toString().length()>0 &  txtNombres.getText().toString().length()>0 &
                txtApellidos.getText().toString().length()>0 &  txtCorreo.getText().toString().length()>0 &
                txtTelefono.getText().toString().length()>0 &  txtDireccion.getText().toString().length()>0)

        {
            params.put("agregar", "1");
            params.put("identificacion", txtIdentificacion.getText().toString());
            params.put("id_usuario",  "1");
            params.put("nombres", txtNombres.getText().toString().toUpperCase());
            params.put("apellidos", txtApellidos.getText().toString().toUpperCase());
            params.put("email", txtCorreo.getText().toString());
            params.put("telefono", txtTelefono.getText().toString());
            params.put("direccion", txtDireccion.getText().toString().toUpperCase());

            params.put("genero", "");
            params.put("estado_civil", "");
            params.put("estado", "");
            WebService ws = new WebService(Constants.WS_EMPLEADO, params, view.getContext(), (Asynchtask) AdministradorEmpleadoNuevoFragment.this);
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
