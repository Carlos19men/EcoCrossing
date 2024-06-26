/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.juego;

import Jugador.Administrador;
import Jugador.Entidad;
import Jugador.Jugador;
import Jugador.JugadorFactory;
import Multijugador.Servidor;
import Personaje.Personaje;
import java.net.InetAddress;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carlos
 */

public class mainJuego {

    public static void main(String[] args) {        
       try { 
            
            
            Administrador admin = new Administrador("carlos","samuel",InetAddress.getLocalHost(),6001);
            
            //creamos el servidor
            admin.crearServidor(InetAddress.getLocalHost(), 6000);
            
            admin.crearPartida(1);
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(mainJuego.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            Logger.getLogger(mainJuego.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    
    
}
