/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Multijugador;

import java.net.DatagramPacket;
import java.net.SocketException;

/**
 *
 * @author carlos
 */
public interface Mensaje {
    public void interpretar(DatagramPacket paquete) throws SocketException; 
}
