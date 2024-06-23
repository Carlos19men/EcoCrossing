/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Multijugador;

import java.net.DatagramPacket;
import java.net.InetAddress;

/**
 *
 * @author carlos
 */
public class PaqueteMensajeFactory {
    public PaqueteMensajeFactory() {
    }
    
    public static DatagramPacket crearPaquete(String mensaje, InetAddress ruta, int port){
        mensaje ="mensaje,"+mensaje; 
        return PaqueteFactory.crear(mensaje, ruta, port); 
    }
    
    public static String crearMensaje(String mensaje){
        return "mensaje,"+mensaje; 
    }
    
}
