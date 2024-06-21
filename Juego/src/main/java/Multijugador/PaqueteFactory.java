/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Multijugador;

/**
 *
 * @author carlos
 */
public class PaqueteFactory {

    public PaqueteFactory() {
    }
    
    
    //metodo
    public Paquete crearPaquete(String tipo){
        return new Paquete(tipo); 
    }
}
