/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Multijugador;

import java.awt.Color;

/**
 *
 * @author carlos
 */
public class PruebaJ2 {
    
    public static void main(String[] args) {
        Jugador j1 = new Jugador("Pepe",Color.RED,6000,"10.68.17.31");
        j1.Conectar();
        j1.jugar();
    }
    
}
