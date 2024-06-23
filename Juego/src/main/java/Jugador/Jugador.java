/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Jugador;

import PanelDeJuego.PanelJuego;
import Multijugador.ManejadorPaquete;
import Personaje.Personaje;
import java.awt.Rectangle;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author carlos
 */
public class Jugador extends Entidad implements ManejadorPaquete{
    //atributos del personaje 
    private String id; 
    private Personaje personaje; 
   
    //atributos para el online 
    private InetAddress ip; 
    private int puerto; 
    private DatagramSocket socket; 
    
    //atributos para el manejo del juego
    private ManejadorTeclado teclado = null; 
    private PanelJuego panel = null; 
    public int pantallaX = 0, pantallaY = 0;
    private String direccionAnterior;

    
    //constructores 
    public Jugador() {
    }

    public Jugador(String id, String personaje,InetAddress ip, int puerto) throws SocketException {
        this.id = id;
        //this.personaje = personaje;    <---------------------  agregar una función para cargar laas imagenes de un sprite con un string
        this.socket = new DatagramSocket(puerto);        
    }
    
    //set / get 
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

    public InetAddress getIp() {
        return ip;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public DatagramSocket getSocket() {
        return socket;
    }

    public void setSocket(DatagramSocket socket) {
        this.socket = socket;
    }

    public ManejadorTeclado getTeclado() {
        return teclado;
    }

    public void setTeclado(ManejadorTeclado teclado) {
        this.teclado = teclado;
    }

    public PanelJuego getPanel() {
        return panel;
    }

    public void setPanel(PanelJuego panel) {
        this.panel = panel;
    }

    public int getPantallaX() {
        return pantallaX;
    }

    public void setPantallaX(int pantallaX) {
        this.pantallaX = pantallaX;
    }

    public int getPantallaY() {
        return pantallaY;
    }

    public void setPantallaY(int pantallaY) {
        this.pantallaY = pantallaY;
    }

    public String getDireccionAnterior() {
        return direccionAnterior;
    }

    public void setDireccionAnterior(String direccionAnterior) {
        this.direccionAnterior = direccionAnterior;
    }
    

    
    //metodos 
    @Override
    public DatagramPacket recibirPaquete() {
        byte[] datos = new byte[1024];
        
        //preparamos el paquete que vamos a recibir 
        DatagramPacket paquete = new DatagramPacket(datos, datos.length);
        try {
                //esperamos el paquete 
               socket.receive(paquete);
               System.out.println("Paquete recibido de: " + paquete.getAddress() + ":" + paquete.getPort());
               return paquete; 
        } catch (IOException e) {
           //falta crear un exepcion 
        }     
        return null; 
    }


    @Override
    public void enviarPaquete(DatagramPacket packet) {
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
    
        public void crearManejadorDeTeclado(){
        teclado = new ManejadorTeclado(); 
    }
    
    public void crearPanelJuego(){
        panel = new PanelJuego(this,teclado); 
    }
    
    //Iniciar valores por defecto     
    public void setValoresporDefecto() {
        mundoX = panel.tamannoRecuadros * 15;
        mundoY = panel.tamannoRecuadros * 15;
        velocidad = 4;
        direccion = "frente";      
    }
    
    public void getImagenJugador() {
        try {
            espalda = ImageIO.read(getClass().getResourceAsStream("/Jugador/samuel_espalda.png"));
            arriba1 = ImageIO.read(getClass().getResourceAsStream("/Jugador/samuel_espalda_caminando1.png"));
            arriba2 = ImageIO.read(getClass().getResourceAsStream("/Jugador/samuel_espalda_caminando2.png"));
            frente = ImageIO.read(getClass().getResourceAsStream("/Jugador/samuel_frente.png"));
            abajo1 = ImageIO.read(getClass().getResourceAsStream("/Jugador/samuel_caminando1.png"));
            abajo2 = ImageIO.read(getClass().getResourceAsStream("/Jugador/samuel_caminando2.png"));
            lado1 = ImageIO.read(getClass().getResourceAsStream("/Jugador/samuel_Derecha.png"));
            der1 = ImageIO.read(getClass().getResourceAsStream("/Jugador/samuel_Derecho_caminando1.png"));
            der2 = ImageIO.read(getClass().getResourceAsStream("/Jugador/samuel_Derecho_caminando2.png"));
            lado2 = ImageIO.read(getClass().getResourceAsStream("/Jugador/samuel_Izquierda.png"));
            izq1 = ImageIO.read(getClass().getResourceAsStream("/Jugador/samuel_izquierda_caminando1.png"));
            izq2 = ImageIO.read(getClass().getResourceAsStream("/Jugador/samuel_izquierda_caminando2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public void inicializarCoordenadas(){
        pantallaX = panel.anchoPantalla / 2 - (panel.tamannoRecuadros / 2);
        pantallaY = panel.largoPantalla / 2 - (panel.tamannoRecuadros / 2);
        areaSolida = new Rectangle();
        areaSolida.x=8;
        areaSolida.y=16;
        areaSolidaDefaultX=areaSolida.x;
        areaSolidaDefaultY=areaSolida.y;
        areaSolida.width=32;
        areaSolida.height=32;
        setValoresporDefecto();
        getImagenJugador();
        
        // Inicializar la dirección anterior con la misma dirección inicial
        direccionAnterior = direccion;
    }

    public void mover(int x, int y){
        mundoX = x; 
        mundoY = y; 
    }
}
