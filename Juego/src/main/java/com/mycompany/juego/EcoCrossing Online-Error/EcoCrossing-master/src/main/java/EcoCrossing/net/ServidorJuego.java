package EcoCrossing.net;
import EcoCrossing.net.paquetes.Paquete;
import EcoCrossing.net.paquetes.Paquete.TiposPaquete;
import EcoCrossing.net.paquetes.Paquete00Acceder;
import Entidades.JugadorMultijugador;
import java.net.DatagramSocket; 
import java.net.InetAddress;
import com.mycompany.ecocrossing.PanelJuego;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;

import java.util.logging.Logger;

public class ServidorJuego extends Thread{
    private DatagramSocket socket;
    private PanelJuego panelJuego;
    private ArrayList<JugadorMultijugador> jugadoresConectados = new ArrayList<>();
    
    //**Se puede implementar un try/catch**
    public ServidorJuego(PanelJuego panelJuego){
        this.panelJuego = panelJuego;
        try {
            this.socket = new DatagramSocket(1331);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void run(){
        while(true){
            byte[] datos = new byte[1024];
            DatagramPacket paquete = new DatagramPacket(datos, datos.length);
            try {
                socket.receive(paquete);
            //**Diferente en el video**
            } catch (IOException e) {
            }
            this.parsePaquete(paquete.getData(), paquete.getAddress(), paquete.getPort());
        }
    }
    
    private void parsePaquete(byte[] datos, InetAddress direccionIP, int puerto){
        String mensaje = new String(datos).trim();
        System.out.println("Mensaje recibido: " + mensaje);      
        TiposPaquete tipo = Paquete.buscarPaquete(mensaje.substring(0, 2));
        Paquete paquete = null;
        switch(tipo){
            default:
            case INVALIDO:
                break;
            case ACCEDER:       
                paquete = new Paquete00Acceder(datos);
                //Annadir el nombre de usuario a futuro
                System.out.println("["+direccionIP.getHostAddress()+": " + puerto + "] " + ((Paquete00Acceder)paquete).getNombreUsuario() + " se ha conectado.");
                JugadorMultijugador jugador = new JugadorMultijugador(((Paquete00Acceder)paquete).getNombreUsuario(), panelJuego, direccionIP, puerto);
                this.agregarConexion(jugador, ((Paquete00Acceder)paquete));
                break;
            case DESCONECTAR:       
                break;
        }
    }  
    
    public void agregarConexion(JugadorMultijugador jugador, Paquete00Acceder paquete) {
        boolean conectado = false;
        for(JugadorMultijugador j: this.jugadoresConectados){
            if(jugador.getNombreUsuario().equalsIgnoreCase(j.getNombreUsuario())){
                if(j.direccionIP == null)
                    j.direccionIP = jugador.direccionIP;
                if(j.puerto == -1)
                    j.puerto = jugador.puerto;
                conectado = true;
            } else
                enviarDatos(paquete.getDatos(), j.direccionIP, j.puerto);
        }
        if(!conectado){
            jugadoresConectados.add(jugador);
            paquete.escribirDatos(this);
            //panelJuego.agregarJugador(jugador);
        }
    }
    
    public void enviarDatos(byte[] datos, InetAddress direccionIP, int puerto){
        DatagramPacket packet = new DatagramPacket(datos, datos.length, direccionIP, puerto);
        try {
            this.socket.send(packet);
        //**Diferente en el video**
        } catch (IOException e) {
            Logger.getLogger(ClienteJuego.class.getName()).log(Level.SEVERE, null, e);
        }
    }  

    public void enviarDatos_TodoslosClientes(byte[] datos) {
        for(JugadorMultijugador j: jugadoresConectados)
            enviarDatos(datos, j.direccionIP, j.puerto);
    }

    
}
