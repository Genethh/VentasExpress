package com.uteq.sistemas.ventasexpress.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uteq.sistemas.ventasexpress.R;
import com.uteq.sistemas.ventasexpress.fragment.AdminRutaLista;
import com.uteq.sistemas.ventasexpress.model.Empleado;
import com.uteq.sistemas.ventasexpress.utils.Constants;
import com.uteq.sistemas.ventasexpress.utils.UIUtil;

import java.util.List;

/**
 * Created by ANDRES on 01/08/2017.
 */
public class RutaListEmplAdapter extends RecyclerView.Adapter<RutaListEmplAdapter.ViewHolder> {

    Context context;
    List<Empleado> empleados;

    public RutaListEmplAdapter(Context context, List<Empleado> empleados) {
        this.context = context;
        this.empleados = empleados;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_empleado, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nombre.setText(empleados.get(position).getNombres());
        holder.id.setText((empleados.get(position).getIdentificacion()));
        holder.direccion.setText(empleados.get(position).getDireccion());
        holder.identificacion.setText(empleados.get(position).getIdentificacion());

    }

    @Override
    public int getItemCount() {
        return empleados.size();
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        UIUtil.animateCircularReveal(holder.itemView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView nombre;
        TextView identificacion;
        TextView direccion;
        TextView id;

        public ViewHolder(View item) {
            super(item);
            cardView = (CardView) item.findViewById(R.id.cvMenu);
            nombre = (TextView) item.findViewById(R.id.LblTitulo);
            identificacion = (TextView) item.findViewById(R.id.lblIdentificacion);
            direccion = (TextView) item.findViewById(R.id.lbldireccion);
            id = (TextView) item.findViewById(R.id.lblID);


                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            id = (TextView) v.findViewById(R.id.lblIdentificacion);
                            Bundle b = new Bundle();
                            b.putString("ide", (String) id.getText());
                            b.putString("nombre", (String) nombre.getText());
                            AdminRutaLista fragment = new AdminRutaLista();
                            fragment.setArguments(b);
                            android.support.v4.app.FragmentTransaction fragmentTransaction = Constants.fragmentManager.beginTransaction().addToBackStack(null);
                            fragmentTransaction.replace(R.id.fragment, fragment).addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                    });
        }
    }
}