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
public class PaqueteFactory {

    public PaqueteFactory() {
    }

    public static DatagramPacket crear(String mensaje, InetAddress ruta, int port){
        return new DatagramPacket(mensaje.getBytes(), mensaje.getBytes().length, ruta, port); 
    }
    
    
}
