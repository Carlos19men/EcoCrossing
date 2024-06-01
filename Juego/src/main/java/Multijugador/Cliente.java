package Multijugador;

/**
 *
 * @author carlos
 */

import java.io.DataOutputStream; 
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Cliente implements Runnable{
    private int puerto; 
    private String mensaje; 
    private String host; 

    public Cliente(int puerto, String mensaje, String host) {
        this.puerto = puerto;
        this.mensaje = mensaje;
        this.host = host; 
    }
    
    
    @Override
    public void run() {
        //host del servidor 
        
        DataOutputStream out; 
        
        try{
            //Cremo el socket para conectarme al cliente 
            Socket sc = new Socket(host,puerto); 
            
            out = new DataOutputStream(sc.getOutputStream()); 
            
            //Envio un mensaje al cliente 
            out.writeUTF(mensaje);
            
            sc.close();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
