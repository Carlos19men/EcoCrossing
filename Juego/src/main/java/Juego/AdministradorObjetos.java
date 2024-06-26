/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Juego;

import Jugador.Jugador;
import Objetos.SuperObjeto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carlos
 */
public class AdministradorObjetos {
    public List<Jugador> jugadores = new ArrayList<>();
    public List<SuperObjeto> objetosMapa = new ArrayList<>(); 

    //constructores 

    public AdministradorObjetos() {
    }
    

    //set / get 
    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public List<SuperObjeto> getObjetosMapa() {
        return objetosMapa;
    }

    public void setObjetosMapa(List<SuperObjeto> objetosMapa) {
        this.objetosMapa = objetosMapa;
    }
    
    public void addJugador(Jugador jugador){
        jugadores.add(jugador); 
    }
    
}
