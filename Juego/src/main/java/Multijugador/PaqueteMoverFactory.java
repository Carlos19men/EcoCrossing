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
public class PaqueteMoverFactory {
    final String MOVER = "mover"; 
    
    public PaqueteMoverFactory() {
   
    }

    public DatagramPacket crear(String nombre, String sprite,int x, int y,String direccion, InetAddress ruta, int puerto) {
        String mensaje = MOVER+","+nombre+","+x+","+y+","+sprite+","+direccion;
        //Creamos el paquete 
         return PaqueteFactory.crear(mensaje, ruta, puerto); 
    }
    
}
