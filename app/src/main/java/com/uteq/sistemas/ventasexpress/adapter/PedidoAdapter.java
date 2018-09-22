package com.uteq.sistemas.ventasexpress.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uteq.sistemas.ventasexpress.R;
import com.uteq.sistemas.ventasexpress.fragment.ClientePedidoDetalleFragment;
import com.uteq.sistemas.ventasexpress.fragment.VendedorPedidoDetalleFragment;
import com.uteq.sistemas.ventasexpress.model.Pedido;
import com.uteq.sistemas.ventasexpress.utils.Constants;
import com.uteq.sistemas.ventasexpress.utils.UIUtil;

import java.util.List;

/**
 * Created by ANDRES on 01/08/2017.
 */
public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.ViewHolder> {

    Context context;
    List<Pedido> pedidos;

    public PedidoAdapter(Context context, List<Pedido> clientes) {
        this.context = context;
        this.pedidos = clientes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_pedido, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nombre.setText(pedidos.get(position).getNegocio());
        holder.estado.setText("Estado: " + pedidos.get(position).getEstado());
        holder.id.setText(("" + pedidos.get(position).getId()));
    }

    @Override
    public int getItemCount() {
        return pedidos.size();
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        UIUtil.animateCircularReveal(holder.itemView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView nombre;
        TextView estado;
        TextView id;

        public ViewHolder(View item) {
            super(item);
            cardView = (CardView) item.findViewById(R.id.cvMenu);
            nombre = (TextView) item.findViewById(R.id.LblTitulo);
            estado = (TextView) item.findViewById(R.id.lblEstado);
            id = (TextView) item.findViewById(R.id.lblID);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    id = (TextView) v.findViewById(R.id.lblID);
                    Bundle b = new Bundle();
                    b.putString("id", (String) id.getText());
                    VendedorPedidoDetalleFragment fragment = new VendedorPedidoDetalleFragment();
                    fragment.setArguments(b);
                    FragmentTransaction fragmentTransaction = Constants.fragmentManager.beginTransaction().addToBackStack(null);
                    fragmentTransaction.replace(R.id.fragment, fragment).addToBackStack(null);
                    fragmentTransaction.commit();


                }
            });
        }
    }
}