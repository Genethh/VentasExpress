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
public class AdministradorEmpleadoModificarFragment extends Fragment implements Asynchtask {

    private View view;

    private LinearLayoutManager linearLayoutManager;
    private String id,nombre,apellidos,telefono,direccion,email;

    Fragment fragment;
    private FragmentTransaction fragmentTransaction;

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



    public AdministradorEmpleadoModificarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_administrador_empleado_modificar, container, false);
        ButterKnife.bind(this, view);
        Bundle b = getArguments();
        id = b.getString("id");
        nombre = b.getString("nombre");
        apellidos = b.getString("apellidos");
        telefono = b.getString("telefono");
        direccion = b.getString("direccion");
        email = b.getString("email");

        txtIdentificacion=(TextView) view.findViewById(R.id.txtIdentificacion);
        txtNombres=(TextView) view.findViewById(R.id.txtNombres);
        txtApellidos=(TextView) view.findViewById(R.id.txtApellidos);
        txtCorreo=(TextView) view.findViewById(R.id.txtCorreo);
        txtDireccion=(TextView) view.findViewById(R.id.txtDireccion);
        txtTelefono=(TextView) view.findViewById(R.id.txtTelefono);

        txtIdentificacion.setText(id);
        txtNombres.setText(nombre);
        txtApellidos.setText(apellidos);
        txtCorreo.setText(email);
        txtDireccion.setText(direccion);
        txtTelefono.setText(telefono);

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
        if (txtIdentificacion.getText().toString().length()>0 &  txtNombres.getText().toString().length()>0 &
                txtApellidos.getText().toString().length()>0 &  txtCorreo.getText().toString().length()>0 &
                txtTelefono.getText().toString().length()>0 &  txtDireccion.getText().toString().length()>0)

        {
            params.put("editar", "1");
            params.put("identificacion", txtIdentificacion.getText().toString());
            params.put("id_usuario",  "1");
            params.put("nombres", txtNombres.getText().toString().toUpperCase());
            params.put("apellidos", txtApellidos.getText().toString().toUpperCase());
            params.put("email", txtCorreo.getText().toString().toLowerCase());
            params.put("telefono", txtTelefono.getText().toString());
            params.put("direccion", txtDireccion.getText().toString().toUpperCase());

            params.put("genero", "");
            params.put("estado_civil", "");
            params.put("estado", "");
            WebService ws = new WebService(Constants.WS_EMPLEADO, params, view.getContext(), (Asynchtask) AdministradorEmpleadoModificarFragment.this);
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
