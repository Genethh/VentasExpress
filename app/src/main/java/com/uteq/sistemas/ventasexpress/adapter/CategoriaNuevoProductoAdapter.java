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
import com.uteq.sistemas.ventasexpress.fragment.AdministradorProductoNuevoFragment;
import com.uteq.sistemas.ventasexpress.model.Categoria;
import com.uteq.sistemas.ventasexpress.utils.Constants;
import com.uteq.sistemas.ventasexpress.utils.UIUtil;

import java.util.List;


public class CategoriaNuevoProductoAdapter extends RecyclerView.Adapter<CategoriaNuevoProductoAdapter.ViewHolder> {
    Context context;
    List<Categoria> categorias;

    public CategoriaNuevoProductoAdapter(Context context, List<Categoria> categorias) {
        this.context = context;
        this.categorias = categorias;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_categoria, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.detalle.setText(categorias.get(position).getDetalle());
        holder.codigo.setText(categorias.get(position).getCodigo());
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        UIUtil.animateCircularReveal(holder.itemView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView detalle;
        TextView codigo;

        public ViewHolder(View item) {
            super(item);
            cardView = (CardView) item.findViewById(R.id.cvMenu);
            detalle = (TextView) item.findViewById(R.id.LblTitulo);
            codigo = (TextView) item.findViewById(R.id.lblID);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    codigo = (TextView) v.findViewById(R.id.lblID);
                    detalle = (TextView) v.findViewById(R.id.LblTitulo);
                    Bundle b = new Bundle();
                    b.putString("codigo", (String) codigo.getText());
                    b.putString("detalle", (String) detalle.getText());
                   //AdministradorCategoriaModificarFragment fragment = new AdministradorCategoriaModificarFragment();
                    AdministradorProductoNuevoFragment fragment = new AdministradorProductoNuevoFragment();
                    fragment.setArguments(b);
                    FragmentTransaction fragmentTransaction = Constants.fragmentManager.beginTransaction().addToBackStack(null);
                    fragmentTransaction.replace(R.id.fragment, fragment).addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
        }
    }

}
