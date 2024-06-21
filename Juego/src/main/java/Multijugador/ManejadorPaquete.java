/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Multijugador;

import java.net.DatagramPacket;
import java.net.InetAddress;

/**
 *
 * @author carlos
 */
public interface ManejadorPaquete {
    public void enviarPaquete(Paquete packet,InetAddress ruta, int puerto); 
    public DatagramPacket recibirPaquete(); 
    public DatagramPacket empaquetar(Byte[] arreglo); 
    public Byte[] desempaquetar(Paquete paquete); 
}
