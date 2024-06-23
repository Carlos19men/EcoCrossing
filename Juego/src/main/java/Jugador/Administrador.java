/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Jugador;

import Multijugador.Servidor;
import Personaje.Personaje;
import java.net.SocketException;

/**
 *
 * @author carlos
 */
public class Administrador extends Jugador{
    private Servidor server; 

    public Administrador() {
    }

    public Administrador(String id, Personaje personaje,int puerto) throws SocketException {
        super(id, personaje,puerto);
    }
    
    
    
    //metodos 
    public void crearServidor(){
        
    }
    
    public void crearPartida(){
        
    }
    
    public void IniciarPartida(){
        
    }
    
    public void finalizarPartida(){
        
    }
}
