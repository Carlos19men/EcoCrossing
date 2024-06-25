/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PanelDeJuego;

import Jugador.Jugador;
import Multijugador.Servidor;
import java.util.List;

/**
 *
 * @author carlos
 */
public class AdaptadorPanelToServer {
    private Servidor server; 

    public AdaptadorPanelToServer(Servidor server) {
        this.server = server;
    }
    
    //metodos 
    
    public List<Jugador> obtenerJugadores(){
        return server.getJugadores(); 
    }
}
