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
import com.uteq.sistemas.ventasexpress.fragment.VendedorRutaClientesFragment;
import com.uteq.sistemas.ventasexpress.model.Ruta;
import com.uteq.sistemas.ventasexpress.utils.Constants;
import com.uteq.sistemas.ventasexpress.utils.UIUtil;

import java.util.List;

/**
 * Created by ANDRES on 01/08/2017.
 */
public class RutaAdapter extends RecyclerView.Adapter<RutaAdapter.ViewHolder> {

    Context context;
    List<Ruta> rutas;

    public RutaAdapter(Context context, List<Ruta> rutas) {
        this.context = context;
        this.rutas = rutas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_ruta, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.titulo.setText(rutas.get(position).getDia());
        holder.id.setText(("" + rutas.get(position).getId()));
    }

    @Override
    public int getItemCount() {
        return rutas.size();
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        UIUtil.animateCircularReveal(holder.itemView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView titulo;
        TextView id;

        public ViewHolder(View item) {
            super(item);
            cardView = (CardView) item.findViewById(R.id.cvMenu);
            titulo = (TextView) item.findViewById(R.id.LblTitulo);
            id = (TextView) item.findViewById(R.id.lblID);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    id = (TextView) v.findViewById(R.id.lblID);
                    Bundle b = new Bundle();
                    b.putString("id", (String) id.getText());
                     VendedorRutaClientesFragment fragment = new VendedorRutaClientesFragment();
                     fragment.setArguments(b);
                     FragmentTransaction fragmentTransaction = Constants.fragmentManager.beginTransaction().addToBackStack(null);
                     fragmentTransaction.replace(R.id.fragment, fragment).addToBackStack(null);
                     fragmentTransaction.commit();

                }
            });
        }
    }
}