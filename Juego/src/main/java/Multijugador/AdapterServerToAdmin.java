/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Multijugador;

import Jugador.Administrador;

/**
 *
 * @author carlos
 */
public class AdapterServerToAdmin {
    private Administrador admin; 

    public AdapterServerToAdmin(Administrador admin) {
        this.admin = admin;
    }
    
    //metodos 
    public void iniciarJuego(){
        admin.IniciarPartida();
    }
    
    public void actualizarObjetos(){
        
    }
    
}
