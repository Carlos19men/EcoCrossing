/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Jugador;

import Personaje.Personaje;

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
    
    public Jugador crearJugador(String id, Personaje personaje){
        return new Jugador(id,personaje); 
    }
}
