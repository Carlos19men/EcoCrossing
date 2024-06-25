/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.juego;

import Jugador.Administrador;
import Jugador.Entidad;
import Jugador.Jugador;
import Multijugador.Servidor;
import java.net.InetAddress;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carlos
 */

public class Juego {

    public static void main(String[] args) {
        
        try {
            //prueba
            Administrador j1 = new Administrador("Daniel","samuel",InetAddress.getLocalHost(),6000);
            j1.crearServidor(InetAddress.getLocalHost(),5000);
            j1.crearPanelJuegoAdministrador();
            j1.server.AdaptarPanel(j1.panel);
            //j1.server.iniciarServidor();
            //j1.server.iniciarPartida();
            
            j1.panel.iniciarjuegoThread();
            
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            
            
            
            
            
            
            
            
            
            
  
    }
}
