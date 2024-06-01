package Multijugador_2_Servidores;

import java.util.Observable;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author carlos
 */

import java.io.DataInputStream; 
import java.io.DataOutputStream; 
import java.io.IOException; 
import java.net.ServerSocket; 
import java.net.Socket; 
import java.util.Observable; 
import java.util.logging.Logger; 

public class Servidor extends Observable implements Runnable {

    private final int puerto; 

    public Servidor(int puerto) {
        this.puerto = puerto;
    }
    
    
    @Override
    public void run() {
        ServerSocket servidor = null; 
        Socket sc = null; 
        DataInputStream in; 
        DataOutputStream out; 
        
        
        try{
            
            //creamos socket del servidor 
            servidor = new ServerSocket(puerto); 
            System.out.println("Serviodr iniciado");
            
            //siempre estamos estara escuchando peticiones 
            while(true){
                
                //espero a que un cliente se conecte 
                sc = servidor.accept(); 
                
                in = new DataInputStream(sc.getInputStream()); 
                 
                //leo el mensaje que me envia 
                
                String mensaje = in.readUTF(); 
                
                //System.out.println(mensaje);
                
                this.setChanged();
                this.notifyObservers(mensaje);
                this.clearChanged();
                
            }
        } catch (IOException ex){
            Logger.getLogger(Servidor.class.getName()); 
            
        }
        
    }
    
}
