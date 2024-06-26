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

public class mainJuego2 {

    public static void main(String[] args) {
        
        
        try { 
            InetAddress ruta = InetAddress.getLocalHost(); 
            Jugador j1 = JugadorFactory.crearJugador("ignacio","samuel",ruta,5500);
            
            j1.conectarse(6000,ruta);
        } catch (UnknownHostException ex) {
            Logger.getLogger(mainJuego2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            Logger.getLogger(mainJuego2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}