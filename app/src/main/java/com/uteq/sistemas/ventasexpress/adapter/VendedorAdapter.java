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
import com.uteq.sistemas.ventasexpress.fragment.AdministradorEmpleadoModificarFragment;
import com.uteq.sistemas.ventasexpress.model.Empleado;
import com.uteq.sistemas.ventasexpress.utils.Constants;
import com.uteq.sistemas.ventasexpress.utils.UIUtil;

import java.util.List;

/**
 * Created by ANDRES on 01/08/2017.
 */
public class VendedorAdapter extends RecyclerView.Adapter<VendedorAdapter.ViewHolder> {

    Context context;
    List<Empleado> empleados;

    public VendedorAdapter(Context context, List<Empleado> empleados) {
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
        holder.apellidos.setText(empleados.get(position).getApellidos());
        holder.email.setText(empleados.get(position).getEmail());
        holder.telefono.setText(empleados.get(position).getTelefono());
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
        TextView apellidos;
        TextView email;
        TextView telefono;

        public ViewHolder(View item) {
            super(item);
            cardView = (CardView) item.findViewById(R.id.cvMenu);
            nombre = (TextView) item.findViewById(R.id.LblTitulo);
            identificacion = (TextView) item.findViewById(R.id.lblIdentificacion);
            direccion = (TextView) item.findViewById(R.id.lbldireccion);
            id = (TextView) item.findViewById(R.id.lblID);
            apellidos = (TextView) item.findViewById(R.id.lblApellido);
            email = (TextView) item.findViewById(R.id.lblemail);
            telefono = (TextView) item.findViewById(R.id.lbltlf);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    id = (TextView) v.findViewById(R.id.lblID);
                    Bundle b = new Bundle();
                    b.putString("id", (String) id.getText());
                    b.putString("nombre", (String) nombre.getText());
                    b.putString("direccion", (String) direccion.getText());
                    b.putString("apellidos", (String) apellidos.getText());
                    b.putString("email", (String) email.getText());
                    b.putString("telefono", (String) telefono.getText());

                            AdministradorEmpleadoModificarFragment fragment = new AdministradorEmpleadoModificarFragment();
                            fragment.setArguments(b);
                            android.support.v4.app.FragmentTransaction fragmentTransaction = Constants.fragmentManager.beginTransaction().addToBackStack(null);
                            fragmentTransaction.replace(R.id.fragment, fragment).addToBackStack(null);
                            fragmentTransaction.commit();


                }
            });
        }
    }
}