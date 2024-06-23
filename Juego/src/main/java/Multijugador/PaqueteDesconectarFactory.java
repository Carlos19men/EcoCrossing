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
public class PaqueteDesconectarFactory{
    final String DESCONECTAR = "desconectar"; 
    private String nombre; 

    public PaqueteDesconectarFactory(){
    }
    
    public DatagramPacket crear(String nombre, InetAddress ruta, int puerto) {
        String mensaje = DESCONECTAR+","+nombre; 
        return PaqueteFactory.crear(mensaje, ruta, puerto); 
    }
    
}
