/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Multijugador;

import java.net.DatagramPacket;

/**
 *
 * @author carlos
 */
public interface ManejadorPaquete {
 
    public String[] desempaquetar(DatagramPacket paquete); 
    public String empaquetar(String[] datos); 
}
