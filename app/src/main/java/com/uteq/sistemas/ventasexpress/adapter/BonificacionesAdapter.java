package com.uteq.sistemas.ventasexpress.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uteq.sistemas.ventasexpress.R;
import com.uteq.sistemas.ventasexpress.model.Bonificacion;
import com.uteq.sistemas.ventasexpress.utils.UIUtil;

import java.util.List;

/**
 * Created by ANDRES on 01/08/2017.
 */
public class BonificacionesAdapter extends RecyclerView.Adapter<BonificacionesAdapter.ViewHolder> {

    Context context;
    List<Bonificacion> bonificaciones;

    public BonificacionesAdapter(Context context, List<Bonificacion> bonificaciones) {
        this.context = context;
        this.bonificaciones = bonificaciones;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_bonificacion, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.id.setText(bonificaciones.get(position).getCodigo());
        holder.identificacion.setText(bonificaciones.get(position).getCodigo());
        holder.totalpedido.setText(bonificaciones.get(position).getTotalpedido());
        holder.bonificacion.setText(bonificaciones.get(position).getBonificacion());
        holder.fecha.setText(bonificaciones.get(position).getFecha());
        holder.idpedido.setText(bonificaciones.get(position).getIdpedido());
    }

    @Override
    public int getItemCount() {
        return bonificaciones.size();
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        UIUtil.animateCircularReveal(holder.itemView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView identificacion;
        //  TextView idempleado;
        TextView idpedido;
        TextView totalpedido;
        TextView bonificacion;
        TextView fecha;
        TextView id;

        public ViewHolder(View item) {
            super(item);
            cardView = (CardView) item.findViewById(R.id.cvMenu);
            id = (TextView) item.findViewById(R.id.lblID);
            identificacion = (TextView) item.findViewById(R.id.lblIdentificacion);
            idpedido = (TextView) item.findViewById(R.id.lblPedido);
            totalpedido = (TextView) item.findViewById(R.id.lblTotal);
            bonificacion = (TextView) item.findViewById(R.id.lblBonificacion);
            fecha = (TextView) item.findViewById(R.id.lblFecha);
            //   id = (TextView) item.findViewById(R.id.lblIdentificacion);
           /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    id = (TextView) v.findViewById(R.id.lblIdentificacion);
                    Bundle b = new Bundle();
                    b.putString("id", (String) id.getText());
                    AdministradorBonificaciones fragment = new AdministradorBonificaciones();
                    fragment.setArguments(b);
                    android.support.v4.app.FragmentTransaction fragmentTransaction = Constants.fragmentManager.beginTransaction().addToBackStack(null);
                    fragmentTransaction.replace(R.id.fragment, fragment).addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });*/
        }
    }
}