/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.juego;

import Jugador.Administrador;
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
            Administrador admin = new Administrador("carlos","samuel",InetAddress.getLocalHost(),5000);
            /*admin.crearServidor();
            admin.server.AdaptarAdmin(admin);
            admin.inicializarCoordenadas();
            admin.valoresPorDefecto();
            
            admin.server.crearPartida();*/
            
            
            
            
            
            
            
            
            
            
            
           
            
        } catch (SocketException | UnknownHostException ex) {
            System.out.println(ex.getMessage());   //<-----------------------------
        }
    }
}
