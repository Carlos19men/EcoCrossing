/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Multijugador;

import Jugador.Jugador;
import java.net.InetAddress;

/**
 *
 * @author carlos
 */
public class Mensajero {

    public Mensajero() {
    }
    
    //metodos
    public static String mensajeConectar(Jugador jugador){
        return "conectar,"+jugador.getId()+","+
                         +jugador.getMundoX()+","
                         +jugador.getMundoY()+","
                         +jugador.getPersonaje().getSprite(); 
    }
    
    public static String mensajeMover(Jugador jugador,String direccion){
        return "mover,"+jugador.getId()+","
                +jugador.getMundoX()+","
                +jugador.getMundoY()+","
                +jugador.getPersonaje().getSprite()+","
                +direccion; 
    }
    
    public static String mensaje(String mensaje){
        return "mesanje,"+mensaje;
    }
    
    public static String mensajeDesconectar(Jugador jugador){
        return "desconectar,"+jugador.getId(); 
    }
}
