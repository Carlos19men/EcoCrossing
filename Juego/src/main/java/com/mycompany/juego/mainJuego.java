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
import miniGUI.PanelAdministrado;

/**
 *
 * @author carlos
 */

public class mainJuego {

    public static void main(String[] args) {        
        PanelAdministrado panel = new PanelAdministrado(); 
        
        panel.saludar();
        
    }
    
    
    
    
}
