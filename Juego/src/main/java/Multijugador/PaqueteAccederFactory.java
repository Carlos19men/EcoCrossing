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
public class PaqueteAccederFactory{
    final String ACCEDER = "acceder";  

    public PaqueteAccederFactory() {

    }
    
    public DatagramPacket crear(String nombre, String sprite, int x, int y, InetAddress ruta, int puerto) {
        String mensaje = ACCEDER+","+nombre+","+x+","+y+","+sprite; 
        return PaqueteFactory.crear(mensaje, ruta, puerto); 
    }
    
}
