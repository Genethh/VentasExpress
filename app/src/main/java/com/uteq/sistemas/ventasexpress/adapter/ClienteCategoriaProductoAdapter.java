package com.uteq.sistemas.ventasexpress.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.uteq.sistemas.ventasexpress.R;
import com.uteq.sistemas.ventasexpress.fragment.AdministradorCategoriaModificarFragment;
import com.uteq.sistemas.ventasexpress.fragment.ClientePedidoDetalleFragment;
import com.uteq.sistemas.ventasexpress.fragment.VendedorPedidoDetalleAgregarFragment;
import com.uteq.sistemas.ventasexpress.model.Categoria;
import com.uteq.sistemas.ventasexpress.utils.Constants;
import com.uteq.sistemas.ventasexpress.utils.UIUtil;
import com.uteq.sistemas.ventasexpress.utils.WebServ.Asynchtask;
import com.uteq.sistemas.ventasexpress.utils.WebServ.WebService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClienteCategoriaProductoAdapter extends RecyclerView.Adapter<ClienteCategoriaProductoAdapter.ViewHolder>  /*implements Asynchtask */{
    List<Categoria> categorias;
    Context context;
    View itemView;

    String id_pedido_nuevo;

    public ClienteCategoriaProductoAdapter(Context context, List<Categoria> categorias,String id_pedido_nuevo) {
        this.context = context;
        this.categorias = categorias;
        this.id_pedido_nuevo = id_pedido_nuevo;
    }

    @Override
    public ClienteCategoriaProductoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_cliente_categoria_pedido, parent, false);
        ClienteCategoriaProductoAdapter.ViewHolder viewHolder = new ClienteCategoriaProductoAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ClienteCategoriaProductoAdapter.ViewHolder holder, int position) {
        holder.detalle.setText(categorias.get(position).getDetalle());
        holder.id.setText(categorias.get(position).getCodigo());
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

    @Override
    public void onViewAttachedToWindow(ClienteCategoriaProductoAdapter.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        UIUtil.animateCircularReveal(holder.itemView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView detalle;
        TextView id;

        public ViewHolder(View item) {
            super(item);
            cardView = (CardView) item.findViewById(R.id.cvMenu);
            detalle = (TextView) item.findViewById(R.id.LblTitulo);
            id = (TextView) item.findViewById(R.id.lblID);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Pasar Datos
                    // Creamos un nuevo Bundle
                    Bundle args = new Bundle();
                    // Colocamos el String
                    args.putString("id_pedido", id_pedido_nuevo);


                    VendedorPedidoDetalleAgregarFragment fragment = new VendedorPedidoDetalleAgregarFragment();
                    fragment.setArguments(args);
                    FragmentTransaction fragmentTransaction = Constants.fragmentManager.beginTransaction().addToBackStack(null);
                    fragmentTransaction.replace(R.id.fragment, fragment);
                    fragmentTransaction.commit();
                }
            });
        }
    }


}
