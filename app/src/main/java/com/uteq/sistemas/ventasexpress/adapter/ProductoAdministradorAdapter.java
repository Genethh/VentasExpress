package com.uteq.sistemas.ventasexpress.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.uteq.sistemas.ventasexpress.R;
import com.uteq.sistemas.ventasexpress.fragment.AdministradorCategoriasNuevoProductoFragment;
import com.uteq.sistemas.ventasexpress.fragment.AdministradorProductoModificar;
import com.uteq.sistemas.ventasexpress.fragment.AdministradorProductoNuevoFragment;
import com.uteq.sistemas.ventasexpress.model.Producto;
import com.uteq.sistemas.ventasexpress.utils.Constants;
import com.uteq.sistemas.ventasexpress.utils.UIUtil;

import java.util.List;

/**
 * Created by ANDRES on 01/08/2017.
 */
public class ProductoAdministradorAdapter extends RecyclerView.Adapter<ProductoAdministradorAdapter.ViewHolder> {

    Context context;
    List<Producto> listaproducto;

    public ProductoAdministradorAdapter(Context context, List<Producto> listaproducto) {
        this.context = context;
        this.listaproducto = listaproducto;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_listallproducto, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.idProducto.setText(listaproducto.get(position).getCodigo());
        //holder.nombre.setText(listaproducto.get(position).getCodigo() + " - "+ listaproducto.get(position).getNombre());
        holder.nombre.setText(listaproducto.get(position).getNombre());
        holder.codigoCategoria.setText(listaproducto.get(position).getCodigoCategoria());
        holder.stockMaximo.setText(listaproducto.get(position).getStockMaximo());
        holder.stockMinimo.setText(listaproducto.get(position).getStockMinimo());
        holder.precioPublico.setText(listaproducto.get(position).getPrecioPublico());
        holder.observacion.setText(listaproducto.get(position).getObservacion());
        holder.cantidad.setText(listaproducto.get(position).getCantidad());


    }

    @Override
    public int getItemCount() {
        return listaproducto.size();
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        UIUtil.animateCircularReveal(holder.itemView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView idProducto;
        TextView nombre;
        TextView codigoCategoria;
        TextView stock;
        TextView stockMaximo;
        TextView stockMinimo;
        TextView precioPublico;
        TextView observacion;
        TextView cantidad;

        public ViewHolder(View item) {
            super(item);
            cardView = (CardView) item.findViewById(R.id.cvMenu);
            idProducto = (TextView) item.findViewById(R.id.lblID);
            nombre = (TextView) item.findViewById(R.id.LblTitulo);

            codigoCategoria= (TextView) item.findViewById(R.id.lblcodcat);
            stock= (TextView) item.findViewById(R.id.lblStock);
            stockMaximo= (TextView) item.findViewById(R.id.lblsmax);
            stockMinimo= (TextView) item.findViewById(R.id.lblsmin);
            precioPublico= (TextView) item.findViewById(R.id.lblpreciopub);
            observacion= (TextView) item.findViewById(R.id.lblobs);
            cantidad= (TextView) item.findViewById(R.id.lblStock);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    idProducto = (TextView) v.findViewById(R.id.lblID);
                    Bundle b = new Bundle();
                    b.putString("idProducto", (String) idProducto.getText());
                    b.putString("nombre", (String) nombre.getText());
                    b.putString("codigoCategoria", (String) codigoCategoria.getText());
                    b.putString("stockMaximo", (String) stockMaximo.getText());
                    b.putString("stockMinimo", (String) stockMinimo.getText());
                    b.putString("precioPublico", (String) precioPublico.getText());
                    b.putString("observacion", (String) observacion.getText());
                    b.putString("cantidad", (String) cantidad.getText());
                    AdministradorProductoModificar fragment = new AdministradorProductoModificar();
                    // AdministradorCategoriasNuevoProductoFragment fragment = new AdministradorCategoriasNuevoProductoFragment(); //
                    fragment.setArguments(b);
                    FragmentTransaction fragmentTransaction = Constants.fragmentManager.beginTransaction().addToBackStack(null);
                    fragmentTransaction.replace(R.id.fragment, fragment).addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
        }
    }
}