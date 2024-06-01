package EcoCrossing.net;
import EcoCrossing.net.paquetes.Paquete;
import EcoCrossing.net.paquetes.Paquete.TiposPaquete;
import EcoCrossing.net.paquetes.Paquete00Acceder;
import Entidades.JugadorMultijugador;
import java.net.DatagramSocket;
import java.net.InetAddress;
import com.mycompany.ecocrossing.PanelJuego;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
                e.printStackTrace();
            }
            parsePaquete(paquete.getData(), paquete.getAddress(), paquete.getPort());
            /*String mensaje = new String(packet.getData());
            System.out.println("Cliente["+packet.getAddress().getHostAddress()+": "+packet.getPort()+"]: " + mensaje.trim());
            if(mensaje.trim().equalsIgnoreCase("ping")){
                enviarDatos("pong".getBytes(), packet.getAddress(), packet.getPort());
            }*/
        }
    }
    
    private void parsePaquete(byte[] datos, InetAddress direccionIP, int puerto){
        String mensaje = new String(datos).trim();
        TiposPaquete tipo = Paquete.buscarPaquete(mensaje.substring(0, 2));
        switch(tipo){
            default:
            case INVALIDO:
                break;
            case ACCEDER:       
                Paquete00Acceder paquete = new Paquete00Acceder(datos);
                //Annadir el nombre de usuario a futuro
                System.out.println("["+direccionIP.getHostAddress()+": " + puerto + "] se ha conectado.");
                JugadorMultijugador jugador = null;
                if(direccionIP.getHostAddress().equalsIgnoreCase("127.0.0.1"))
                    jugador = new JugadorMultijugador(panelJuego, panelJuego.manejoTeclas, direccionIP, puerto);
                else
                    jugador = new JugadorMultijugador(panelJuego, direccionIP, puerto);
                if(jugador != null){
                    panelJuego.jugadoresConectados.add(jugador);
                    this.jugadoresConectados.add(jugador);
                    //Pintar otro rectangulo 
                    panelJuego.repaint();
                    panelJuego.jugador = jugador;
                }
                break;
            case DESCONECTAR:       
                break;
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
