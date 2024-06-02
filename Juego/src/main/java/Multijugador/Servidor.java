/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Multijugador;



/**
 *
 * @author carlos
 */
public class Servidor {
    public static void main(String args[]){
        Comunicador server = new Comunicador(5000); 
        server.crearServidor();
    }
    
}
