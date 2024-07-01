package EcoCrossing.net;

import EcoCrossing.net.paquetes.*;
import EcoCrossing.net.paquetes.Paquete.TiposPaquete;
import Entidades.Entidad;
import Entidades.JugadorMultijugador;
import com.mycompany.ecocrossing.PanelJuego;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorJuego extends Thread {
    private DatagramSocket socket;
    private PanelJuego panelJuego;
    private ArrayList<JugadorMultijugador> jugadoresConectados = new ArrayList<>();

    // Constructor del servidor de juego
    public ServidorJuego(PanelJuego panelJuego) {
        this.panelJuego = panelJuego;
        try {
            this.socket = new DatagramSocket(1331);  // Configura el socket en el puerto por defecto
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    // Obtiene la IP local del servidor
    public String obtenerIPLocal() {
        try {
            InetAddress direccionIP = InetAddress.getLocalHost();
            return direccionIP.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "Desconocida";
        }
    }

    // Método principal del hilo que recibe paquetes entrantes
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
        System.out.println("Mensaje recibido: " + mensaje);
        TiposPaquete tipo = Paquete.buscarPaquete(mensaje.substring(0, 2));
        switch (tipo) {
            default:
            case INVALIDO:
                break;
            case ACCEDER:
                Paquete paquete = new Paquete00Acceder(datos);
                System.out.println("[" + direccionIP.getHostAddress() + ": " + puerto + "] " + ((Paquete00Acceder) paquete).getNombreUsuario() + " se ha conectado.");
                JugadorMultijugador jugador = new JugadorMultijugador(((Paquete00Acceder) paquete).getNombreUsuario(), panelJuego, direccionIP, puerto);
                this.agregarConexion(jugador, ((Paquete00Acceder) paquete));
                break;
            case DESCONECTAR:
                paquete = new Paquete01Desconectar(datos);
                System.out.println("[" + direccionIP.getHostAddress() + ": " + puerto + "] " + ((Paquete01Desconectar) paquete).getNombreUsuario() + " se ha desconectado.");
                this.quitarConexion(((Paquete01Desconectar) paquete));
                break;
            case MOVER:
                paquete = new Paquete02Mover(datos);
                System.out.println(((Paquete02Mover) paquete).getNombreUsuario() + " se ha movido a: " + ((Paquete02Mover) paquete).getX() + ", " + ((Paquete02Mover) paquete).getY());
                this.manejarMovimiento((Paquete02Mover) paquete);
                break;
            case ELIMINAR_OBJETO:
                paquete = new Paquete03EliminarObjeto(datos);
                System.out.println("Se ha eliminado el objeto de indice: " + ((Paquete03EliminarObjeto) paquete).getIndice());
                manejarEliminacionObjeto((Paquete03EliminarObjeto) paquete);
                break;
        }
    }

    // Métodos relacionados con la gestión de conexiones

    // Agrega una nueva conexión de jugador al servidor
    public void agregarConexion(JugadorMultijugador jugador, Paquete00Acceder paquete) {
        boolean conectado = false;
        for (JugadorMultijugador j : this.jugadoresConectados) {
            if (jugador.getNombreUsuario().equalsIgnoreCase(j.getNombreUsuario())) {
                if (j.direccionIP == null)
                    j.direccionIP = jugador.direccionIP;
                if (j.puerto == -1)
                    j.puerto = jugador.puerto;
                j.setDireccion(paquete.getDireccion());
                conectado = true;
            } else {
                enviarDatos(paquete.getDatos(), j.direccionIP, j.puerto);
                Paquete00Acceder paqueteExistente = new Paquete00Acceder(j.getNombreUsuario(), j.mundoX, j.mundoY, j.direccion);
                enviarDatos(paqueteExistente.getDatos(), jugador.direccionIP, jugador.puerto);
            }
        }
        if (!conectado) {
            jugador.setDireccion(paquete.getDireccion());
            this.jugadoresConectados.add(jugador);
            // Recorrer el arreglo de objetos del servidor, en busca de enviarle a los demás jugadores qué objetos dibujar.
            // No enviar al administrador cuando se conecta, pues, su puerto es -1 al iniciar.
            for (int i = 0; i < panelJuego.obj.length; i++)  
                if (panelJuego.obj[i] == null && !(jugador.getNombreUsuario().equalsIgnoreCase(panelJuego.jugador.getNombreUsuario()))) {
                    Paquete03EliminarObjeto paqueteObjeto = new Paquete03EliminarObjeto(i);
                    enviarDatos(paqueteObjeto.getDatos(), jugador.direccionIP, jugador.puerto);
                }
        }
    }

    // Quita la conexión de un jugador del servidor
    public void quitarConexion(Paquete01Desconectar paquete) {
        this.jugadoresConectados.remove(buscarIndiceJugadorMultijugador(paquete.getNombreUsuario()));
        paquete.escribirDatos(this);
    }

    // Métodos relacionados con el manejo de movimientos y objetos

    // Maneja el movimiento de los jugadores
    private void manejarMovimiento(Paquete02Mover paquete) {
        if (buscarJugadorMultijugador(paquete.getNombreUsuario()) != null) {
            int indice = buscarIndiceJugadorMultijugador(paquete.getNombreUsuario());
            this.jugadoresConectados.get(indice).mundoX = paquete.getX();
            this.jugadoresConectados.get(indice).mundoY = paquete.getY();
            paquete.escribirDatos(this);
        //De no encontrarse el jugador, sabemos que el paquete se envio por un npc
        } else {
            Entidad npc = panelJuego.buscarNPC(paquete.getNombreUsuario());
            npc.mundoX = paquete.getX();
            npc.mundoY = paquete.getY();
            paquete.escribirDatos(this);
        }
    }

    // Maneja la eliminación de un objeto en el juego
    private void manejarEliminacionObjeto(Paquete03EliminarObjeto paquete) {
        int indiceEliminado = paquete.getIndice();

        // Notificar a todos los clientes conectados sobre la eliminación del objeto
        enviarDatos_TodoslosClientes(paquete.getDatos());

        // Actualizar el estado del juego en el servidor
        panelJuego.eliminarObjeto(indiceEliminado);
    }

    // Métodos auxiliares para la búsqueda de jugadores

    // Busca un jugador multijugador por su nombre de usuario
    public JugadorMultijugador buscarJugadorMultijugador(String nombreUsuario) {
        for (JugadorMultijugador j : this.jugadoresConectados)
            if (j.getNombreUsuario().equals(nombreUsuario))
                return j;
        return null;
    }

    // Busca el índice de un jugador multijugador por su nombre de usuario
    public int buscarIndiceJugadorMultijugador(String nombreUsuario) {
        int indice = 0;
        for (JugadorMultijugador j : this.jugadoresConectados) {
            if (j.getNombreUsuario().equals(nombreUsuario))
                break;
            indice++;
        }
        return indice;
    }

    // Métodos relacionados con el envío de datos

    // Envía datos a una dirección IP y puerto específicos
    public void enviarDatos(byte[] datos, InetAddress direccionIP, int puerto) {
        DatagramPacket packet = new DatagramPacket(datos, datos.length, direccionIP, puerto);
        try {
            this.socket.send(packet);
        } catch (IOException e) {
            Logger.getLogger(ClienteJuego.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    // Envía datos a todos los clientes conectados
    public void enviarDatos_TodoslosClientes(byte[] datos) {
        for (JugadorMultijugador j : jugadoresConectados)
            enviarDatos(datos, j.direccionIP, j.puerto);
    }
}
