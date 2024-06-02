package EcoCrossing.net;
import EcoCrossing.net.paquetes.Paquete;
import EcoCrossing.net.paquetes.Paquete.TiposPaquete;
import static EcoCrossing.net.paquetes.Paquete.TiposPaquete.ACCEDER;
import static EcoCrossing.net.paquetes.Paquete.TiposPaquete.DESCONECTAR;
import static EcoCrossing.net.paquetes.Paquete.TiposPaquete.INVALIDO;
import EcoCrossing.net.paquetes.Paquete00Acceder;
import EcoCrossing.net.paquetes.Paquete01Desconectar;
import Entidades.JugadorMultijugador;
import java.net.DatagramSocket;
import java.net.InetAddress;
import com.mycompany.ecocrossing.PanelJuego;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteJuego extends Thread{
    private InetAddress direccionIP;
    private DatagramSocket socket;
    private PanelJuego panelJuego;
    
     //**Diferente en el video**
    public ClienteJuego(PanelJuego panelJuego, String direccionIP){
        this.panelJuego = panelJuego;
        try {
            this.socket = new DatagramSocket();
            this.direccionIP = InetAddress.getByName(direccionIP);
        } catch (UnknownHostException | SocketException ex) {
            Logger.getLogger(ClienteJuego.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DatagramSocket getSocket() {
        return socket;
    }
    
    @Override
    public void run(){
        while(true){
            byte[] datos = new byte[1024];
            DatagramPacket paquete = new DatagramPacket(datos, datos.length);
            try {
                socket.receive(paquete);
                System.out.println("Paquete recibido de: " + paquete.getAddress() + ":" + paquete.getPort());
            } catch (IOException e) {
            }      
            this.parsePaquete(paquete.getData(), paquete.getAddress(), paquete.getPort());         
        }
    }
    
    private void parsePaquete(byte[] datos, InetAddress direccionIP, int puerto){
        String mensaje = new String(datos).trim();
        TiposPaquete tipo = Paquete.buscarPaquete(mensaje.substring(0, 2));
        Paquete paquete = null;
        switch(tipo){
            default:
            case INVALIDO:
                break;
            case ACCEDER:       
                paquete = new Paquete00Acceder(datos);
                //Annadir el nombre de usuario a futuro
                System.out.println("["+direccionIP.getHostAddress()+": " + puerto + "] " + ((Paquete00Acceder)paquete).getNombreUsuario() + " se ha unido al juego...");
                JugadorMultijugador jugador = new JugadorMultijugador(((Paquete00Acceder)paquete).getNombreUsuario(), panelJuego, direccionIP, puerto);
                panelJuego.agregarJugador(jugador);
                break;
            case DESCONECTAR:       
                paquete = new Paquete01Desconectar(datos);
                System.out.println("["+direccionIP.getHostAddress()+": " + puerto + "] " + ((Paquete01Desconectar)paquete).getNombreUsuario() + " se ha desconectado del juego..."); 
                panelJuego.eliminarJugador(((Paquete01Desconectar) paquete).getNombreUsuario());
                break;
        }
    }  
    
    public void enviarDatos(byte[] datos){
        DatagramPacket packet = new DatagramPacket(datos, datos.length, direccionIP, 1331);
        try {
            socket.send(packet);
        } catch (IOException e) {
            Logger.getLogger(ClienteJuego.class.getName()).log(Level.SEVERE, null, e);
        }
    }  
}
