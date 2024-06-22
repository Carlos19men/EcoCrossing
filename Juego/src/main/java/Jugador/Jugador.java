/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Jugador;

import Multijugador.ManejadorPaquete;
import Multijugador.PaqueteFactory;
import Personaje.Personaje;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carlos
 */
public class Jugador extends Entidad implements ManejadorPaquete{
    private String id; 
    private Personaje personaje; 
    private InetAddress ip; 
    private int puerto; 
    private ManejoTeclas teclado; 
    private PanelJuego panel; 
    
    public Jugador() {
    }

    public Jugador(String id, Personaje personaje) {
        this.id = id;
        this.personaje = personaje;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Personaje getPersonaje() {
        return personaje;
    }

    public void setPersonaje(Personaje personaje) {
        this.personaje = personaje;
    }

    
    //metodos 
    @Override
    public DatagramPacket empaquetar(Byte[] arreglo, InetAddress ip, int puerto) {
        return new DatagramPacket(arreglo,arreglo.length,ip,puerto); 
    }
    
    
    @Override
    public DatagramPacket recibirPaquete() {
        DatagramSocket socket = null; 
        byte[] datos = new byte[1024];
        DatagramPacket paquete = new DatagramPacket(datos, datos.length);
        try {
               socket.receive(paquete);
               System.out.println("Paquete recibido de: " + paquete.getAddress() + ":" + paquete.getPort());
               return paquete; 
        } catch (IOException e) {
           
        }     
        return null; 
    }


    @Override
    public void enviarPaquete(DatagramPacket packet) {
        DatagramSocket socket = null; 
        try {
            socket = new DatagramSocket(puerto);
            socket.send(packet);
        } catch (SocketException ex) {
            Logger.getLogger(Jugador.class.getName()).log(Level.SEVERE, null, ex);  //recuerda crear exepciones 
        } catch (IOException ex) {
            Logger.getLogger(Jugador.class.getName()).log(Level.SEVERE, null, ex);  //recuerda
        }
    }

    @Override
    public String[] desempaquetar(DatagramPacket paquete) {
        return new String(paquete.getData()).trim().split(",");
    }


}
