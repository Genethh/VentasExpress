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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.uteq.sistemas.ventasexpress.R;
import com.uteq.sistemas.ventasexpress.utils.Constants;
import com.uteq.sistemas.ventasexpress.utils.WebServ.Asynchtask;
import com.uteq.sistemas.ventasexpress.utils.WebServ.WebService;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
public class VendedorDetallePedidoEditarFragment extends Fragment implements Asynchtask  {

    private LinearLayoutManager linearLayoutManager;
    private View view;

    Fragment fragment;
    private FragmentTransaction fragmentTransaction;

    private String id;
    private String idpedido;
    private String cantidad_nueva;

    @BindView(R.id.lblID)
    TextView ID;

    @BindView(R.id.LblNombre)
    TextView nombre;

    @BindView(R.id.lblcodigo)
    TextView codigo;

    @BindView(R.id.lblPrecio)
    TextView precio;

    @BindView(R.id.LblObservacion)
    TextView observacion;

    @BindView(R.id.btnModificarP)
    Button btnmodificar;

    @BindView(R.id.btnEliminarP)
    Button btneliminar;

    @BindView(R.id.txtCantidadModificar)
    EditText Txtcantidad_modificar;

    public VendedorDetallePedidoEditarFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pedido_editar_producto, container, false);
        ButterKnife.bind(this, view);
        Bundle b = getArguments();
        id = b.getString("id");
        ID.setText(id);
        nombre.setText(b.getString("nombre"));
        codigo.setText(b.getString("id"));
        precio.setText(b.getString("precio"));
        observacion.setText(b.getString("observacion"));
        idpedido = b.getString("idpedido");

        btneliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext().getApplicationContext(),"Eliminar", Toast.LENGTH_LONG).show();
                ConectWSDetallePedido("Eliminar");
            }
        });

        btnmodificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext().getApplicationContext(),"Modificar", Toast.LENGTH_LONG).show();
                cantidad_nueva = Txtcantidad_modificar.getText().toString();
                ConectWSDetallePedido("Modificar");
            }
        });




        linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);



        return view;
    }


    private void ConectWSDetallePedido(String tipo) {
        Map<String, String> params = new HashMap<>();
        if (tipo == "Eliminar") {
            params.put("tipo_operacion", "Eliminar");
        } else if (tipo == "Modificar") {
            params.put("tipo_operacion", "Modificar");
            params.put("cantidad_nueva", cantidad_nueva);
        }
        params.put("id_pedido", idpedido);
        params.put("codigo_producto", id);
        WebService ws = new WebService(Constants.WS_PRODUCTO, params, view.getContext(), (Asynchtask) VendedorDetallePedidoEditarFragment.this);
        ws.execute("");
    }

    @Override
    public void processFinish(String result) throws JSONException {
        Log.e("Resultado", result);
        //JSONArray objdataarray = new JSONArray(result);
        //pedidos = Pedido.JsonObjectsBuild(objdataarray);
        //PedidoAdapter adapater = new PedidoAdapter(view.getContext(), pedidos);
        //recyclerView.setAdapter(adapater);

        Bundle b = new Bundle();
        b.putString("id", idpedido);
        ClientePedidoDetalleFragment fragment = new ClientePedidoDetalleFragment();
        fragment.setArguments(b);
        FragmentTransaction fragmentTransaction = Constants.fragmentManager.beginTransaction().addToBackStack(null);
        fragmentTransaction.replace(R.id.fragment, fragment).addToBackStack(null);
        fragmentTransaction.commit();
    }
}
