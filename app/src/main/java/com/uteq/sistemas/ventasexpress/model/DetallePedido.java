package com.uteq.sistemas.ventasexpress.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetallePedido {

    private String codigo;
    private String codigoCategoria;
    private String nombre;
    private int stockMaximo;
    private int stockMinimo;
    private double precioPublico;
    private String observacion;
    private int cantidad;


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigoCategoria() {
        return codigoCategoria;
    }

    public void setCodigoCategoria(String codigoCategoria) {
        this.codigoCategoria = codigoCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getStockMaximo() {
        return stockMaximo;
    }

    public void setStockMaximo(int stockMaximo) {
        this.stockMaximo = stockMaximo;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public double getPrecioPublico() {
        return precioPublico;
    }

    public void setPrecioPublico(double precioPublico) {
        this.precioPublico = precioPublico;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }


    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public DetallePedido(JSONObject a) throws JSONException {
        codigo = a.getString("codigo");
        nombre = a.getString("nombre");
        codigoCategoria = a.getString("codigo_categoria");
        stockMaximo = a.getInt("stock_maximo");
        stockMinimo = a.getInt("stock_minimo");
        precioPublico = a.getDouble("precio_publico");
        observacion = a.getString("observacion");
        cantidad = a.getInt("cantidad");
    }

    public static ArrayList<DetallePedido> JsonObjectsBuild(JSONArray datos)
            throws JSONException {
        ArrayList<DetallePedido> rutas = new ArrayList<>();
        for (int i = 0; i < datos.length(); i++) {
            rutas.add(new DetallePedido(datos.getJSONObject(i)));
        }
        return rutas;
    }
}