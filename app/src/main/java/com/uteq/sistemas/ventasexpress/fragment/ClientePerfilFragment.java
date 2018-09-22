package com.uteq.sistemas.ventasexpress.fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.uteq.sistemas.ventasexpress.R;
import com.uteq.sistemas.ventasexpress.utils.Constants;
import com.uteq.sistemas.ventasexpress.utils.WebServ.Asynchtask;
import com.uteq.sistemas.ventasexpress.utils.WebServ.WebService;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ClientePerfilFragment extends Fragment  implements Asynchtask {


    private View view;


    @BindView(R.id.BtnAceptar)
    Button btnAceptar;

    @BindView(R.id.BtnCancelar)
    Button btnCancelar;

    @BindView(R.id.txtNegocioPerfil)
    TextView txtnegocio;

    @BindView(R.id.txtRUCPerfil)
    TextView txtruc;

    @BindView(R.id.txtPropietarioPerfil)
    TextView txtpropietario;

    @BindView(R.id.txtCorreoPerfil)
    TextView txtcorreo;

    @BindView(R.id.txtTelefonoPerfil)
    TextView txttelefono;

    @BindView(R.id.txtDireccionPerfil)
    TextView txtdireccion;


    public ClientePerfilFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cliente_perfil, container, false);
        ButterKnife.bind(this, view);

        txtnegocio.setText(Constants.cliente.getNegocio().toString());
        txtruc.setText(Constants.cliente.getIdentificacion());
        txtpropietario.setText(Constants.cliente.getPropietario());
        txtcorreo.setText(Constants.cliente.getEmail());
        txttelefono.setText(Constants.cliente.getTelefono());
        txtdireccion.setText(Constants.cliente.getDireccion());

        btnAceptar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ObtenerCliente();
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

    public void ObtenerCliente() {
        Map<String, String> params = new HashMap<>();
        params.put("idcliente",Constants.cliente.getIdentificacion());
        WebService ws = new WebService(Constants.WS_CLIENTE, params, view.getContext(), (Asynchtask) ClientePerfilFragment.this);
        ws.execute("");
    }


    @Override
    public void processFinish(String result) throws JSONException, ParseException {
        Log.e("Resultado", result);
        try {
            JSONObject obj = new JSONObject(result);
            txtruc.setText(obj.getString("identificacion"));
            txtnegocio.setText(obj.getString("negocio"));
            txtpropietario.setText(obj.getString("propietario"));
            txtcorreo.setText(obj.getString("email"));
            txtdireccion.setText(obj.getString("direccion"));
            txttelefono.setText(obj.getString("telefono"));
        } catch (Exception e) {

        }
    }

}
