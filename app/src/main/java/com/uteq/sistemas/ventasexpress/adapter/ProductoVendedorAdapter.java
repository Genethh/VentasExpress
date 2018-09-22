package com.uteq.sistemas.ventasexpress.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.uteq.sistemas.ventasexpress.R;
import com.uteq.sistemas.ventasexpress.fragment.ClientePedidoDetalleFragment;
import com.uteq.sistemas.ventasexpress.fragment.VendedorPedidoDetalleAgregarFragment;
import com.uteq.sistemas.ventasexpress.model.Producto;
import com.uteq.sistemas.ventasexpress.utils.Constants;
import com.uteq.sistemas.ventasexpress.utils.UIUtil;
import com.uteq.sistemas.ventasexpress.utils.WebServ.Asynchtask;
import com.uteq.sistemas.ventasexpress.utils.WebServ.WebService;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductoVendedorAdapter extends RecyclerView.Adapter<ProductoVendedorAdapter.ViewHolder> implements Asynchtask {
    Context context;
    List<Producto> productos;

    View itemView;

    Fragment fragment;
    private FragmentTransaction fragmentTransaction;

    String id_pedido;
    VendedorPedidoDetalleAgregarFragment pedidoDetalleAgregarFragment;




    public ProductoVendedorAdapter(Context context, List<Producto> productos, String id_pedido, VendedorPedidoDetalleAgregarFragment vendedorPedidoDetalleAgregarFragment) {
        this.context = context;
        this.productos = productos;
        this.id_pedido = id_pedido;
        this.pedidoDetalleAgregarFragment = vendedorPedidoDetalleAgregarFragment;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_lista_producto, parent, false);
        final ProductoVendedorAdapter.ViewHolder viewHolder = new ProductoVendedorAdapter.ViewHolder(itemView);

        itemView.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //Toast.makeText(context,"Text click"+String.valueOf(viewHolder.getAdapterPosition()),Toast.LENGTH_SHORT).show();
                Toast.makeText(context.getApplicationContext(),"Producto Seleccionado:" + productos.get(Integer.valueOf(viewHolder.getAdapterPosition())).getCodigo(), Toast.LENGTH_LONG).show();


            }
        });
        viewHolder.btnagregar_producto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cantidad_pedido= viewHolder.numberCantidad.getValue();
                //Toast.makeText(context.getApplicationContext(),"agregar producto:" + cantidad_pedido, Toast.LENGTH_LONG).show();

                ConectWSAgregarProducto_Pedido(productos.get(Integer.valueOf(viewHolder.getAdapterPosition())).getCodigo(), String.valueOf(cantidad_pedido));
            }
        });


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.id.setText((productos.get(position).getCodigo()));
        holder.codigo.setText((productos.get(position).getCodigo()));
        holder.nombre.setText(productos.get(position).getNombre());
        holder.precio.setText("" + productos.get(position).getPrecioPublico());
        holder.observacion.setText("" + productos.get(position).getObservacion());
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }
    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        UIUtil.animateCircularReveal(holder.itemView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView id;
        TextView codigo;
        TextView nombre;
        TextView precio;
        TextView observacion;
        NumberPicker numberCantidad;
        Button btnagregar_producto;

        public ViewHolder(View item) {
            super(item);
            cardView = (CardView) item.findViewById(R.id.cvMenu);
            id = (TextView) item.findViewById(R.id.lblID);
            codigo = (TextView) item.findViewById(R.id.lblcodigo);
            nombre = (TextView) item.findViewById(R.id.LblNombre);
            precio = (TextView) item.findViewById(R.id.lblPrecio);
            observacion = (TextView) item.findViewById(R.id.LblObservacion);
            btnagregar_producto = (Button)item.findViewById(R.id.btnAgregarP);
            numberCantidad = (NumberPicker)item.findViewById(R.id.NumberP_Cantidad);
            numberCantidad.setMinValue(1);
            numberCantidad.setMaxValue(500);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    id = (TextView) v.findViewById(R.id.lblID);
                    Bundle b = new Bundle();
                    b.putString("id", (String) id.getText());
                }
            });
        }
    }


    ////
    private void ConectWSAgregarProducto_Pedido(String codigoProduct, String CantidadProduct) {
        Map<String, String> params = new HashMap<>();
        params.put("tipo_operacion", "Insertar");
        params.put("id_pedido", id_pedido);
        params.put("codigo_producto", codigoProduct);
        params.put("cantidad_nueva", CantidadProduct);
        params.put("estado", "1");
        //WebService ws = new WebService(Constants.WS_PRODUCTO, params, itemView.getContext(), (Asynchtask) itemView);
        WebService ws = new WebService(Constants.WS_PRODUCTO,params,itemView.getContext(),(Asynchtask) this);
        ws.execute("");
    }

    @Override
    public void processFinish(String result) throws JSONException {
        Log.e("Resultado", result);
        //JSONArray objdataarray = new JSONArray(result);
        //pedidos = Pedido.JsonObjectsBuild(objdataarray);
        //PedidoAdapter adapater = new PedidoAdapter(view.getContext(), pedidos);
        //recyclerView.setAdapter(adapater);

        Toast.makeText(context.getApplicationContext(),"Producto Agregado", Toast.LENGTH_LONG).show();
        Bundle b = new Bundle();
        b.putString("id", id_pedido);
        ClientePedidoDetalleFragment fragment = new ClientePedidoDetalleFragment();
        fragment.setArguments(b);
        FragmentTransaction fragmentTransaction = Constants.fragmentManager.beginTransaction().addToBackStack(null);
        fragmentTransaction.replace(R.id.fragment, fragment).addToBackStack(null);
        fragmentTransaction.commit();
    }
}
