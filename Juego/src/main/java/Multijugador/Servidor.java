/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Multijugador;

import Jugador.Jugador;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.List; 
import java.util.LinkedList; 


/**
 *
 * @author carlos
 */
public class Servidor implements ManejadorPaquete{
    private int puerto; 
    private InetAddress IP; 
    private List<Jugador> jugadores = new LinkedList<>(); 

    public Servidor(int puerto, InetAddress IP) {
        this.puerto = puerto;
        this.IP = IP;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public InetAddress getIP() {
        return IP;
    }

    public void setIP(InetAddress IP) {
        this.IP = IP;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }
    
    //metodos 
    
    public void agregar(Jugador jugador){
        
    }
    
    public void eliminar(Jugador jugador){
        
    }
    
    public void notificar(){
        
    }

    @Override
    public void enviarPaquete(PaqueteFactory packet, InetAddress ruta, int puerto) {
        
    }

    @Override
    public Byte[] desempaquetar(PaqueteFactory paquete) {
                return null; 
    }

    @Override
    public DatagramPacket recibirPaquete() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public DatagramPacket empaquetar(Byte[] arreglo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void enviarPaquete(DatagramPacket packet) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public DatagramPacket empaquetar(Byte[] arreglo, InetAddress ip, int puerto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String[] desempaquetar(DatagramPacket paquete) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
