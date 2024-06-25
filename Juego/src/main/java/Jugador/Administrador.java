/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Jugador;

import Multijugador.Servidor;
import PanelDeJuego.PanelJuego;
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

    public Administrador(String id, String personaje) {
        super(id,personaje);
    }

    public Administrador(String id, String personaje, InetAddress ip, int puerto) throws SocketException {
        super(id, personaje, ip, puerto);
    }
    
    
    
    
    
    //metodos 
    public void crearServidor(InetAddress ruta, int puerto) throws UnknownHostException, SocketException{
        server = new Servidor(InetAddress.getLocalHost(),5000);    
    }
    
    public void crearPartida() throws SocketException{
        server.crearPartida();
    }
    
    public void IniciarPartida(){
        
    }
    
    public void finalizarPartida(){
        
    }
    
    //metodos del panel de juego
    public void crearPanelJuegoAdministrador(){
        panel = new PanelJuego(this,super.getTeclado(),server); 
        mundoX = 100;
        mundoY = 100; 
        pantallaX = (panel.anchoPantalla - panel.tamannoRecuadros) / 2;
        pantallaY = (panel.largoPantalla - panel.tamannoRecuadros) / 2;
        
    }
}
