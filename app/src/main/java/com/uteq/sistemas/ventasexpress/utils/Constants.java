package com.uteq.sistemas.ventasexpress.utils;

import android.support.v4.app.FragmentManager;

import com.uteq.sistemas.ventasexpress.model.Cliente;
import com.uteq.sistemas.ventasexpress.model.Empleado;

/**
 * Created by Andres on 10/08/2017.
 */

public class Constants {

    public static Empleado empleado;
    public static Cliente cliente;

   // public static final String IP_LOCAL = "http://192.168.1.2/v5APIVentasExpress";


    public static final String IP_LOCAL = "http://192.168.1.136/v5APIVentasExpress";
    public static final String DOMINIO = "http://ve.hol.es";
    public static final String WS_RUTA = DOMINIO + "/ruta.php";
    public static final String WS_CLIENTE = DOMINIO + "/cliente.php";
    public static final String WS_USUARIO = DOMINIO + "/usuario.php";
    public static final String WS_PEDIDO = DOMINIO + "/pedido.php";
    public static final String WS_EMPLEADO = DOMINIO + "/empleado.php";
    public static final String WS_PRODUCTO = DOMINIO + "/producto.php";
    public static final String WS_BONIFICACION = DOMINIO + "/bonificacion.php";
    public static final String WS_CATEGORIA = DOMINIO + "/categoria.php";
    public static String ide;

    //Mensajes
    public static final String MSG_CONFIRMAR_GUARDAR_IMAGEN = "¿Desea guardar la imagen?";
    public static final String MSG_GUARDAR_IMAGEN = "Mantenga presionado para guardar como imagen";
    public static final String MSG_COMPROBAR_CONEXION_INTERNET = "Comprueba tu conexión a internet";
    public static final String MSG_CARGANDO_IMAGEN = "Cargando imagen...";
    public static FragmentManager fragmentManager;
    public static String idpedido;
    public static String idruta;
}