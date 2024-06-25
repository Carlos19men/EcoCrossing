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
public class AdaptadorServidorToAdmin {
    private Administrador admin; 

    public AdaptadorServidorToAdmin(Administrador admin) {
        this.admin = admin;
    }
    
    public Administrador traer(){
        return admin; 
    }
}
