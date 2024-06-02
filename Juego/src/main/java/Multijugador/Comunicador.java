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
import java.util.Arrays;

/**
 *
 * @author carlos
 */
public class Comunicador {
    private byte[] buffer = new byte[252]; 
    private int puerto; 

    public Comunicador() {
    }

    public Comunicador(int puerto) {
        this.puerto = puerto;
    }
    
    
    
    //metodos 
    public void enviar(String ruta, int puerto, String mensaje){
        try { 
            InetAddress direccion = InetAddress.getByName(ruta); 

            DatagramSocket socket = new DatagramSocket();
            limpiarBuffer(); 
            buffer = mensaje.getBytes(); 
            
            DatagramPacket pregunta = new DatagramPacket(buffer,buffer.length,direccion,puerto); 
            
            System.out.println("Mensaje Enviado");
            socket.send(pregunta);
            

            
        } catch (SocketException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
    
    public void limpiarBuffer(){
        Arrays.fill(buffer,(byte) 0);
    }
    
    public DatagramPacket recibirPaquete(){
        
        try { 
            DatagramSocket socket = new DatagramSocket(puerto);
            limpiarBuffer(); 
            DatagramPacket peticion = new DatagramPacket(buffer,buffer.length);
            
            System.out.println("Esperando mensaje...");
            socket.receive(peticion); 
            return peticion; 
        } catch (SocketException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null; 
    }
    
    public String traducirPaquete(DatagramPacket paquete){
        return new String(paquete.getData()); 
    }
    
    public void crearServidor(){
        //Esperamos los datos del jugador 
        DatagramPacket paquete = recibirPaquete(); 
        
        String nombre = traducirPaquete(paquete); 
        
        InetAddress ruta = paquete.getAddress(); 
        
        System.out.println("Se ha conectado el jugador: " + nombre);
        
        
        System.out.println(paquete.getPort());
        enviar("localhost",6000,"Te he agregado a mi servidor"); 
    }
    
    public String recibirMensaje(){
        return traducirPaquete(recibirPaquete());
    }
}
