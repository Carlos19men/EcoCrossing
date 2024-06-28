/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Jugador;

import Juego.AdministradorObjetos;
import Juego.Juego;
import Multijugador.Servidor;
import PanelJuego.PanelJuego;
import Personaje.Personaje;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author carlos
 */
public class Administrador extends Jugador{
    public Servidor server; 

    public Administrador() {
    }

    public Administrador(String id,String nombrePersonaje) {
        super(id,nombrePersonaje);
    }

    //la ip para el administrador siempre tiene que ser local 

    public Administrador(String id, String nomprePersonaje, int mundoX, int mundoY, InetAddress ip, int puerto) throws SocketException {
        super(id, nomprePersonaje, mundoX, mundoY, ip, puerto);
    }
    
    
    
    
    
    
    //metodos 
    public void crearServidor(InetAddress ruta, int puerto) throws UnknownHostException, SocketException{
        server = new Servidor(ruta,puerto);    
        puertoServer = puerto; 
        ipServer = ruta; 
    }
    
    public void crearPartida() throws SocketException{
        
    }
    
    public void IniciarPartida(){
        
    }
    
    public void finalizarPartida(){
        
    }
    

    
    //metodos del panel de juego
    
}
