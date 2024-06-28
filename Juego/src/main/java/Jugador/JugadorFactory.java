/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Jugador;

import Personaje.Personaje;
import java.net.InetAddress;
import java.net.SocketException;

/**
 *
 * @author carlos
 */
public class JugadorFactory {

    public JugadorFactory() {
    }
    
    //metodos 
    public Jugador crearJugador(){
        return new Jugador(); 
    }
    
    public static Jugador crearJugador(String id,String nombrePersonaje,int x, int y, InetAddress ip, int puerto) throws SocketException{
        return new Jugador(id,nombrePersonaje,x,y,ip,puerto); 
    }
    
    public static Jugador crearJugador(String id,String nombrePersonaje){
        return new Jugador(id,nombrePersonaje); 
    }
}
