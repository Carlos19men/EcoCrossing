/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Multijugador;

import Juego.Juego;

/**
 *
 * @author carlos
 */
public class AdaptadorServidorToJuego {
    private Juego juego; 

    public AdaptadorServidorToJuego(Juego juego) {
        this.juego = juego;
    }
    
    //set / get 
    public Juego getJuego() {
        return juego;
    }

    public void setJuego(Juego juego) {
        this.juego = juego;
    }
    
    //metodos 
    public void iniciarJuego(){
        juego.inicarJuego();
    }
}
