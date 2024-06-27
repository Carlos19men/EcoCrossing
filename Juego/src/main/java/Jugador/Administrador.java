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
    
    public Administrador(String id, String nombrePersonaje, InetAddress ip, int puerto) throws SocketException {
        super(id, nombrePersonaje, ip, puerto);
    }
    
    
    
    
    
    //metodos 
    public void crearServidor(InetAddress ruta, int puerto) throws UnknownHostException, SocketException{
        server = new Servidor(ruta,puerto);    
    }
    
    public void crearPartida() throws SocketException{
        Juego juego = new Juego(new AdministradorObjetos(),this);
        super.configurar();
        
        panel.inicializarValores();
        juego.setPanel(panel);

        //Inicializamos los objetos del juego
        
        //Iniciamos el juego   // acomodar esto si el juego tiene al panel por ende tambien tiene al juagor 
        juego.inicarJuego();
        
        //la funcion escuchar servidor tambien la puede aplicar el administrador
        
    }
    
    public void IniciarPartida(){
        
    }
    
    public void finalizarPartida(){
        
    }
    

    
    //metodos del panel de juego

    public void configurarJuego(){
        super.teclado = new ManejadorTeclado(); 
        super.panel = new PanelJuego(this,teclado,server); 
        
        super.valoresPorDefecto();
    }
}
