/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Multijugador;

import Jugador.Jugador;
import Jugador.JugadorFactory;
import PanelDeJuego.PanelJuego;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.List; 
import java.util.ArrayList; 
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carlos
 */
public class Servidor implements ManejadorPaquete{
    private int puerto; 
    private DatagramSocket socket; 
    private InetAddress IP; 
    private List<Jugador> jugadores = new ArrayList<>(); 
    private AdaptadorServidorPanel adaptadorPanel; 
    boolean partida; 

    public Servidor( InetAddress IP,int puerto) throws SocketException {
        this.puerto = puerto;
        this.IP = IP;
        
        //encerrar en una exepcion 
        this.socket = new DatagramSocket(puerto); 
    }

    //set / get 
    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public InetAddress getIP() {
        return IP;
    }

    public void setIP(InetAddress IP) {
        this.IP = IP;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }
    
    
    
    //metodos 
    public void agregar(Jugador jugador){
       jugadores.add(jugador); 
    }
    
    public void eliminar(Jugador jugador){
        jugadores.remove(jugador); 
    }
    
    public void notificar(){
        for(Jugador objeto: jugadores){
            //noficar
        }
    }

    @Override
    public void enviarPaquete(DatagramPacket packet) {
        try {
            socket.send(packet);
        } catch (SocketException ex) {
            Logger.getLogger(Jugador.class.getName()).log(Level.SEVERE, null, ex);  //recuerda crear exepciones 
        } catch (IOException ex) {
            Logger.getLogger(Jugador.class.getName()).log(Level.SEVERE, null, ex);  //recuerda
        }
    }

    @Override
    public DatagramPacket recibirPaquete() {
        byte[] datos = new byte[1024];
        DatagramPacket paquete = new DatagramPacket(datos, datos.length);
        try {
               socket.receive(paquete);
               System.out.println("Paquete recibido de: " + paquete.getAddress() + ":" + paquete.getPort());
               return paquete; 
        } catch (IOException e) {
           //falta crear un exepcion 
        }     
        return null; 
    }

   @Override
    public String[] desempaquetar(DatagramPacket paquete) {
        return new String(paquete.getData()).trim().split(",");
    }
    
    public void iniciarPartida(){
        partida = true; 
        //Iniciamos la partida 
        adaptadorPanel.iniciarJuego();
        
        //Enviamos un mensaje a cada jugador para que pueden iniciar su partida 
        notificar(PaqueteMensajeFactory.crearMensaje("La partida a iniciado!")); 
        
        
        
    }
    
    public void escucharJugadores(){
        DatagramPacket paquete; 
        while(partida){
            paquete = null; 
            try {
                socket.receive(paquete);
                String[] datos = desempaquetar(paquete); 
                
                System.out.println("Paquete recibido! tipo: "+datos[0]);
                
                
                
                
                
            } catch (IOException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            
        }
    }
    
    public void Interprete(String[] datos){
        if(datos[0].equalsIgnoreCase("acceder")){
            //agregamos un nuevo jugador
        }else if(datos[0].equals("mover")){
            //Un jugador se ha movido, lo buscamos en la lista 
            int indice = buscarJugador(datos[1]); 
            
            if(indice != -1){
                jugadores.get(indice).mover(Integer.parseInt(datos[2]),Integer.parseInt(datos[3]));
                //notificar()  <---------------------- quedaste en la función notificar s
            }
        }
    }
    
    public void crearServidor()throws SocketException{
        //solicitamos el puerto y la ip
        System.out.println("Servidor creado, puerto: "+puerto+" ip: "+IP.getHostAddress());
        esperarJugadores(2);
        System.out.println("Jugadores listos");
    }
    
    public void crearPartida() throws SocketException{
        crearServidor(); 
        iniciarPartida(); 
        
    }
    
    public void finalizarPartida(){
        
    }
    
    public void esperarJugadores(int numeroJugadores) throws SocketException{
        int cantidad = 0; 
        
        while(cantidad < numeroJugadores){
            DatagramPacket paquete = recibirPaquete(); 
            
            String[] datos = desempaquetar(paquete); 
            
            //si el paquete es de tipo acceder creamos el jugador y lo agregamos a la lista
            if(datos[0].equals("acceder")){
                agregar(JugadorFactory.crearJugador(datos,paquete.getAddress(),paquete.getPort()));       
                cantidad++; 
            }
        }
        
        System.out.println("Jugadores listos");
    } 
    
    public void AdaptarPanel(PanelJuego panel){
        adaptadorPanel = new AdaptadorServidorPanel(panel); 
    }
    
    public void notificar(String mensaje){
        for(Jugador jugador: jugadores){
            enviarPaquete(PaqueteFactory.crear(mensaje, jugador.getIp(), jugador.getPuerto())); 
        }
    }
    
    public int buscarJugador(String nombre){
        for(Jugador jugador: jugadores){
            if(jugador.getId().equalsIgnoreCase(nombre)){
                return jugadores.indexOf(jugador); 
            }
        }
        return -1; 
    }
}
