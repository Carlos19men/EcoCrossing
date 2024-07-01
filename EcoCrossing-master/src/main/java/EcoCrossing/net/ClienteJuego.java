package EcoCrossing.net;

import EcoCrossing.net.paquetes.*;
import EcoCrossing.net.paquetes.Paquete.TiposPaquete;
import Entidades.Entidad;
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

public class ClienteJuego extends Thread {
    private InetAddress direccionIP;
    private DatagramSocket socket;
    private PanelJuego panelJuego;

    // Constructor del cliente de juego
    public ClienteJuego(PanelJuego panelJuego, String direccionIP) {
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

    // Método principal del hilo que escucha por paquetes entrantes
    @Override
    public void run() {
        while (true) {
            byte[] datos = new byte[1024];
            DatagramPacket paquete = new DatagramPacket(datos, datos.length);
            try {
                socket.receive(paquete);  // Recibe un paquete
                System.out.println("Paquete recibido de: " + paquete.getAddress() + ":" + paquete.getPort());
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.parsePaquete(paquete.getData(), paquete.getAddress(), paquete.getPort());
        }
    }

    // Analiza los datos del paquete recibido y maneja según el tipo de paquete
    private void parsePaquete(byte[] datos, InetAddress direccionIP, int puerto) {
        String mensaje = new String(datos).trim();
        TiposPaquete tipo = Paquete.buscarPaquete(mensaje.substring(0, 2));
        Paquete paquete;
        switch (tipo) {
            default:
            case INVALIDO:
                break;
            case ACCEDER:
                paquete = new Paquete00Acceder(datos);
                manejarAcceso((Paquete00Acceder) paquete, direccionIP, puerto);
                break;
            case DESCONECTAR:
                paquete = new Paquete01Desconectar(datos);
                System.out.println("[" + direccionIP.getHostAddress() + ": " + puerto + "] " + ((Paquete01Desconectar) paquete).getNombreUsuario() + " se ha desconectado del juego...");
                panelJuego.eliminarJugador(((Paquete01Desconectar) paquete).getNombreUsuario());
                break;
            case MOVER:
                paquete = new Paquete02Mover(datos);
                manejarMovimiento((Paquete02Mover) paquete);
                break;
            case ELIMINAR_OBJETO:
                paquete = new Paquete03EliminarObjeto(datos);
                manejarEliminacionObjeto((Paquete03EliminarObjeto) paquete);
                break;
        }
    }

    // Métodos relacionados con el manejo de acceso y desconexión

    // Maneja el acceso de un nuevo jugador al juego
    private void manejarAcceso(Paquete00Acceder paquete, InetAddress direccionIP, int puerto) {
        System.out.println("[" + direccionIP.getHostAddress() + ": " + puerto + "] " + paquete.getNombreUsuario() + " se ha unido al juego...");
        JugadorMultijugador jugador = new JugadorMultijugador(paquete.getNombreUsuario(), panelJuego, direccionIP, puerto);
        jugador.mundoX = paquete.getX();
        jugador.mundoY = paquete.getY();
        jugador.setDireccion(paquete.getDireccion());
        panelJuego.agregarJugador(jugador);     
    }

    // Métodos relacionados con el manejo de movimientos y objetos

    // Maneja el movimiento de los jugadores
    private void manejarMovimiento(Paquete02Mover paquete) {
        String nombreUsuario = paquete.getNombreUsuario();
        JugadorMultijugador jugador = obtenerJugadorPorNombre(nombreUsuario);
        if (jugador != null) {
            jugador.setMundoX(paquete.getX());
            jugador.setMundoY(paquete.getY());
            jugador.setSpriteNum(paquete.getSpriteNum());
            jugador.setDireccion(paquete.getDireccion());
            jugador.actualizarAnimacion();
        } else {
            Entidad npc = panelJuego.buscarNPC(nombreUsuario);
            npc.mundoX = paquete.getX();
            npc.mundoY = paquete.getY();
            npc.spriteNum = paquete.getSpriteNum();
            npc.direccion = paquete.getDireccion();
            npc.Actualizar();
        }
    }

    // Maneja la eliminación de objetos
    private void manejarEliminacionObjeto(Paquete03EliminarObjeto paquete) {
        int indiceEliminado = paquete.getIndice();
        panelJuego.eliminarObjeto(indiceEliminado);
    }

    // Métodos auxiliares para la búsqueda de jugadores

    // Obtiene un jugador por su nombre de usuario
    private JugadorMultijugador obtenerJugadorPorNombre(String nombreUsuario) {
        for (JugadorMultijugador jugador : panelJuego.getJugadoresConectados())
            if (jugador.getNombreUsuario().equals(nombreUsuario))
                return jugador;
        return null;
    }

    // Métodos relacionados con el envío de datos

    // Envía datos al servidor
    public void enviarDatos(byte[] datos) {
        DatagramPacket packet = new DatagramPacket(datos, datos.length, direccionIP, 1331);
        try {
            socket.send(packet);
        } catch (IOException e) {
            Logger.getLogger(ClienteJuego.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
