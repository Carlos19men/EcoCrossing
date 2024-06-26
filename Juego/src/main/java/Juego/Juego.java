/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Juego;

import Jugador.Jugador;
import Jugador.JugadorFactory;
import Multijugador.ManejadorPaquete;
import Objetos.SuperObjeto;
import PanelJuego.PanelJuego;
import java.net.DatagramPacket;
import java.net.SocketException;

/**
 *
 * @author carlos
 */
public class Juego implements ManejadorPaquete{
    //atributos
    private Jugador jugador; 
    private AdministradorObjetos recursos;
    private PanelJuego panel; 

    //constructor
    public Juego(AdministradorObjetos recursos, Jugador jugador) {
        this.jugador = jugador; 
        this.recursos = recursos;
    }

    //set/get
    public AdministradorObjetos getRecursos() {
        return recursos;
    }

    public void setRecursos(AdministradorObjetos recursos) {
        this.recursos = recursos;
    }

    public PanelJuego getPanel() {
        return panel;
    }

    public void setPanel(PanelJuego panel) {
        this.panel = panel;
    }
    
    
    //metodos 
    public void inicarJuego(){
        panel.iniciarjuegoThread();
    }

    public void interpretar(DatagramPacket paquete) throws SocketException{
        String[] datos = desempaquetar(paquete); 
        String mensaje = empaquetar(datos); 
        
        //verificamos si el paquete es para nosostros 
        if(!mensaje.contains(jugador.getId().toLowerCase())){
            
            if(datos[0].equalsIgnoreCase("acceder")){
                //agregamos un nuevo jugador al administrador de recursos
                recursos.addJugador(JugadorFactory.crearJugador(datos[1],datos[2]));

                //exepcion en caso de alguna falla 
                
            }else if(datos[0].equals("mover")){
                //Un jugador se ha movido, lo buscamos en la lista 
                int indice = buscarJugador(datos[1]); 

                if(indice != -1){
                    //El jugador existe, por ende, cambiamos sus coordenadas 
                    recursos.jugadores.get(indice).mover(Integer.parseInt(datos[2]),Integer.parseInt(datos[3]),datos[4]);
                }
            }else if (datos[0].equals("desconectar")){
                int indice = buscarJugador(datos[1]);
                
                if(indice != -1){
                    //lo borramos de la lista de jugadores de los recursos
                    recursos.jugadores.remove(indice);
                       System.out.println("Jugador "+datos[1]+" se ha desconectado de la partida");
                }
             
            }else if(datos[0].equalsIgnoreCase("mensaje")){
                System.out.println("mensaje");
            }
        }
        
       
    }

    public int buscarJugador(String nombre){
        for(int i = 0; i < recursos.jugadores.size(); i++){
            if(nombre.equalsIgnoreCase(recursos.jugadores.get(i).getId())){
                return i; 
            }
        }
        return -1; 
    }

    @Override
    public String[] desempaquetar(DatagramPacket paquete) {
        return new String(paquete.getData()).trim().split(",");   
    }

    @Override
    public String empaquetar(String[] datos) {
        return String.join(",", datos); 
    }
}
