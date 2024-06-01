package EcoCrossing.net;
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
    
    @Override
    public void run(){
        while(true){
            byte[] datos = new byte[1024];
            DatagramPacket packet = new DatagramPacket(datos, datos.length);
            try {
                socket.receive(packet);
            //**Diferente en el video**
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Servidor: " + new String(packet.getData()));                 
        }
    }
    
    public void enviarDatos(byte[] datos){
        DatagramPacket packet = new DatagramPacket(datos, datos.length, direccionIP, 1331);
        try {
            socket.send(packet);
        //**Diferente en el video**
        } catch (IOException e) {
            Logger.getLogger(ClienteJuego.class.getName()).log(Level.SEVERE, null, e);
        }
    }  
}
