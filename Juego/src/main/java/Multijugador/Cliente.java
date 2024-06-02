/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Multijugador;



/**
 *
 * @author carlos
 */
public class Cliente {
    public static void main(String args[]){
        Comunicador beatriz = new Comunicador(6000);
        beatriz.enviar("localhost", 5000, "Beatriz");
        System.out.println(beatriz.recibirMensaje()); 
    }
}
