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
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carlos
 */
public class Cliente {
    public static void main(String args[]){
        final int puertoServidor = 5000; 
        byte[] buffer = new byte[10]; 
        
        try{
           InetAddress direccion; 
            
            direccion = InetAddress.getByName("localhost");
             
           
           DatagramSocket socketUDP = new DatagramSocket(); 
           
           String mensaje = "a"; 
           
           Arrays.fill(buffer,(byte) 0);
           buffer = mensaje.getBytes();
           
           DatagramPacket pregunta = new DatagramPacket(buffer,buffer.length, direccion, puertoServidor);
           
           while(true){
                socketUDP.send(pregunta);
           }
           
           /*DatagramPacket peticion = new DatagramPacket(buffer,buffer.length); 

           socketUDP.receive(peticion);
           
           mensaje = new String(peticion.getData());
           System.out.println(mensaje);*/
            
           
        }catch (UnknownHostException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
