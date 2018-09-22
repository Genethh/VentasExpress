package com.uteq.sistemas.ventasexpress.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class VendedorRutaAgregarClienteFragment extends Fragment implements Asynchtask {


    private View view;
    String idcliente, idruta;

    public VendedorRutaAgregarClienteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_vendedor_ruta_agregar_cliente, container, false);
        ButterKnife.bind(this, view);
        Bundle b = getArguments();
        idcliente = b.getString("id");
        idruta = b.getString("idpedido");
        login();
        return view;
    }

    public void login() {
        Map<String, String> params = new HashMap<>();
        params.put("AgregarDetalle", "1");
        params.put("idcliente", idcliente);
        params.put("idruta", idruta);
        WebService ws = new WebService(Constants.WS_RUTA, params, view.getContext(), (Asynchtask) VendedorRutaAgregarClienteFragment.this);
        ws.execute("");
    }
    @Override
    public void processFinish(String result) throws JSONException, ParseException {
        Log.e("Resultado", result);
        JSONObject obj = new JSONObject(result);
        String r = obj.getString("resultado");
        if (r=="1"){
            Toast.makeText(getContext(), "Datos guardados correctamente", Toast.LENGTH_SHORT).show();
            VendedorRutaFragment fragment = new VendedorRutaFragment();
            FragmentTransaction fragmentTransaction = Constants.fragmentManager.beginTransaction().addToBackStack(null);
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commit();
        }
    }

}
