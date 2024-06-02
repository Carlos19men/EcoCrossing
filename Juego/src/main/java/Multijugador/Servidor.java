/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Multijugador;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carlos
 */
public class Servidor {
    public static void main(String args[]){
        final int puerto = 5000; 
        byte[] buffer = new byte[10]; 
        
            System.out.println("Iniciado el servidor UDP");
            DatagramSocket socketUDP = null; 
            
        try {
            socketUDP = new DatagramSocket(puerto);
            DatagramPacket peticion = new DatagramPacket(buffer,buffer.length); 
            DatagramPacket peticion1 = new DatagramPacket(buffer,buffer.length); 

            while(true){
                socketUDP.receive(peticion);
                socketUDP.receive(peticion1);
                String mensaje = new String(peticion.getData()); 
                String mensaje1 = new String(peticion1.getData()); 

                System.out.println(mensaje);
                System.out.println(mensaje1);
            }
            
            /*
            
            int puertoCliente = peticion.getPort(); 
            InetAddress direccion = peticion.getAddress(); 
            
            mensaje = "Hola mundo desde el servidor"; 
            buffer = mensaje.getBytes(); 
            
            DatagramPacket respuesta = new DatagramPacket(buffer,buffer.length, direccion, puerto); 
            
            
            socketUDP.send(respuesta);*/
  
        } catch (SocketException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
