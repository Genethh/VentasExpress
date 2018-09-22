package com.uteq.sistemas.ventasexpress.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.uteq.sistemas.ventasexpress.R;
import com.uteq.sistemas.ventasexpress.fragment.VendedorDetallePedidoEditarFragment;
import com.uteq.sistemas.ventasexpress.model.DetallePedido;
import com.uteq.sistemas.ventasexpress.utils.Constants;
import com.uteq.sistemas.ventasexpress.utils.UIUtil;

import java.util.List;

public class DetallePedidoClienteAdapter extends RecyclerView.Adapter<DetallePedidoClienteAdapter.ViewHolder> {
    Context context;
    List<DetallePedido> productos;
    String idpedido ;

    Fragment fragment;
    private FragmentTransaction fragmentTransaction;

    public DetallePedidoClienteAdapter(Context context, List<DetallePedido> productos, String idpedido) {
        this.context = context;
        this.productos = productos;
        this.idpedido = idpedido;
    }

    @Override
    public DetallePedidoClienteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_producto_cliente, parent, false);
        final DetallePedidoClienteAdapter.ViewHolder viewHolder = new DetallePedidoClienteAdapter.ViewHolder(itemView);

        viewHolder.btnEditar.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //Toast.makeText(context,"Text click"+String.valueOf(viewHolder.getAdapterPosition()),Toast.LENGTH_SHORT).show();
                Toast.makeText(context.getApplicationContext(),"Producto Seleccionado:" + productos.get(Integer.valueOf(viewHolder.getAdapterPosition())).getCodigo(), Toast.LENGTH_LONG).show();

                //Pasar Datos
                // Creamos un nuevo Bundle
                Bundle args = new Bundle();
                // Colocamos el String
                args.putString("id", productos.get(Integer.valueOf(viewHolder.getAdapterPosition())).getCodigo());
                args.putString("nombre", productos.get(Integer.valueOf(viewHolder.getAdapterPosition())).getNombre());
                args.putString("precio", String.valueOf(productos.get(Integer.valueOf(viewHolder.getAdapterPosition())).getPrecioPublico()));
                args.putString("observacion", "La Cantidad anterior es : " + String.valueOf(productos.get(Integer.valueOf(viewHolder.getAdapterPosition())).getCantidad()));
                args.putString("idpedido",idpedido);


                fragment = new VendedorDetallePedidoEditarFragment();
                fragment.setArguments(args);
                fragmentTransaction = Constants.fragmentManager.beginTransaction().addToBackStack(null);
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DetallePedidoClienteAdapter.ViewHolder holder, int position) {
        holder.nombre.setText(productos.get(position).getNombre());
        holder.id.setText((productos.get(position).getCodigo()));
        holder.cantidad.setText("" + productos.get(position).getCantidad());
        holder.precio.setText("" + productos.get(position).getPrecioPublico());
        holder.total.setText(String.format("%.2f", productos.get(position).getCantidad() * productos.get(position).getPrecioPublico()));
        holder.btnEditar.setTag(position);
    }
    @Override
    public int getItemCount() {
        return productos.size();
    }

    @Override
    public void onViewAttachedToWindow(DetallePedidoClienteAdapter.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        UIUtil.animateCircularReveal(holder.itemView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView id;
        TextView nombre;
        TextView cantidad;
        TextView precio;
        TextView total;
        Button btnEditar;

        public ViewHolder(View item) {
            super(item);
            cardView = (CardView) item.findViewById(R.id.cvMenu);
            nombre = (TextView) item.findViewById(R.id.LblTitulo);
            cantidad = (TextView) item.findViewById(R.id.lblCantidad);
            precio = (TextView) item.findViewById(R.id.lblPrecio);
            total = (TextView) item.findViewById(R.id.lblTotal);
            id = (TextView) item.findViewById(R.id.lblID);
            btnEditar = (Button)item.findViewById(R.id.BtnModificar);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    id = (TextView) v.findViewById(R.id.lblID);
                    Bundle b = new Bundle();
                    b.putString("id", (String) id.getText());
/*                   RutaProductosFragment fragment = new ProductoVistaFragment();
                     fragment.setArguments(b);
                     FragmentTransaction fragmentTransaction = Constants.fragmentManager.beginTransaction().addToBackStack(null);
                     fragmentTransaction.replace(R.id.fragment, fragment).addToBackStack(null);
                     fragmentTransaction.commit();*/

                }
            });
        }
    }
}
