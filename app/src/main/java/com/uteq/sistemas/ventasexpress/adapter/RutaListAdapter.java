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
import com.uteq.sistemas.ventasexpress.fragment.AdminRutaModificar;
import com.uteq.sistemas.ventasexpress.model.Ruta;
import com.uteq.sistemas.ventasexpress.utils.Constants;
import com.uteq.sistemas.ventasexpress.utils.UIUtil;

import java.util.List;

/**
 * Created by ANDRES on 01/08/2017.
 */
public class RutaListAdapter extends RecyclerView.Adapter<RutaListAdapter.ViewHolder> {

    Context context;
    List<Ruta> rutas;

    public RutaListAdapter(Context context, List<Ruta> rutas) {
        this.context = context;
        this.rutas = rutas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_rutadmin, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.codigo.setText(rutas.get(position).getId());
        holder.id.setText(rutas.get(position).getId());
        holder.dia.setText(rutas.get(position).getDia());
        holder.ide.setText(rutas.get(position).getIdempleado());
        //holder.fecha.setText(rutas.get(position).getFecha());
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
        TextView codigo;
        TextView id;
        TextView ide;
        TextView dia;
        //TextView fecha;


        public ViewHolder(View item) {
            super(item);
            cardView = (CardView) item.findViewById(R.id.cvMenu);
            codigo = (TextView) item.findViewById(R.id.lblcodigoruta);
            dia = (TextView) item.findViewById(R.id.lbldiaruta);
            id = (TextView) item.findViewById(R.id.lblIDrutanueva);
            ide = (TextView) item.findViewById(R.id.lblIDErutanueva);
            //fecha = (TextView) item.findViewById(R.id.lblfecharuta);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    id = (TextView) v.findViewById(R.id.lblIDrutanueva);
                    Bundle b = new Bundle();
                    b.putString("id", (String) id.getText());
                    b.putString("ide", (String) ide.getText());
                    b.putString("dia", (String) dia.getText());

                    //b.putString("id", (String) id.getText());

                     AdminRutaModificar fragment = new AdminRutaModificar();
                     fragment.setArguments(b);
                     FragmentTransaction fragmentTransaction = Constants.fragmentManager.beginTransaction().addToBackStack(null);
                     fragmentTransaction.replace(R.id.fragment, fragment).addToBackStack(null);
                     fragmentTransaction.commit();

                }
            });
        }
    }
}