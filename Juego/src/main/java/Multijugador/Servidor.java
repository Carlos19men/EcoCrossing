/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Multijugador;

import Jugador.Administrador;
import Jugador.Jugador;
import Jugador.JugadorFactory;
import Juego.Juego;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.List; 
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carlos
 */
public class Servidor implements Comunicador, ManejadorPaquete{
    private int puerto; 
    private InetAddress IP; 
    private List<Jugador> jugadores = new LinkedList<>(); 
    boolean partida = false; 
    
    //adaptadores 
    public AdaptadorServidorToJuego adaptadorJuego;
    public AdapterServerToAdmin adapterAdmin; 
    
    public Servidor( InetAddress IP,int puerto) throws SocketException {
        this.puerto = puerto;
        this.IP = IP;
        
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
    
    public void eliminar(Jugador jugador){
        jugadores.remove(jugador); 
    }    
    
    //metodos de paquete 
    @Override
    public void enviarPaquete(DatagramPacket packet) {
        try {
            DatagramSocket socket = new DatagramSocket(puerto); 
            socket.send(packet);
            socket.close();
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
               DatagramSocket socket = new DatagramSocket(puerto); 
               socket.receive(paquete);
               System.out.println("Paquete recibido de: " + paquete.getAddress() + " : " + paquete.getPort());
               socket.close();
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
    
    @Override
    public String empaquetar(String[] datos) {
        return String.join(",", datos); 
    }
    
    @Override
    public String leerPaquete(DatagramPacket paquete) {
        return new String(paquete.getData()); 
    }
        
    public void iniciarServidor(Administrador admin, int cantidad)throws SocketException{
        //solicitamos el puerto y la ip
        jugadores.add(admin); 

        System.out.println("Servidor creado, puerto: "+puerto+" ip: "+IP.getHostAddress());
        
        esperarJugadores(cantidad); 
        
    }
    
    public void iniciarPartida() throws SocketException{
        //Iniciamos la partida 
        partida = true; 
        
        
        //creamos el juego 
        
        
        //Enviamos un mensaje a cada jugador para que pueden iniciar su partida 
        notificar(Mensajero.mensaje("iniciar,hola")); 
        
        //Escuchamos a los jugadore en todo momento
        escucharJugadores(); 
        
        
    }

    public void escucharJugadores() throws SocketException{
        byte[] buffer = new byte[255]; 
        DatagramPacket paquete = new DatagramPacket(buffer,buffer.length); 
       
        try {
            DatagramSocket socket = new DatagramSocket(puerto); 
            while(partida){
                
                //Esperamos las notificaciones de los jugadores
                socket.receive(paquete);

                //Recibimos el paquete y lo desempaquetamos (solo para acegurarnos de que llego)
                String[] datos = desempaquetar(paquete); 
                System.out.println("Paquete recibido! tipo: "+datos[0]);

                //notificamos a los jugadores el paquete 
                notificar(empaquetar(datos)); 
            }
            
            //siempre cerrar el socket
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            
            //Simultaneamente se esta ejecutando el juego por el hilo de IniciarJuegoThread
            //Este ciclo continuará hasta que el admin decida finalizar el juego: partida = false
        
    }
    
    public void finalizarPartida(){
        //se le envia una notificación a cada jugador diciendo que la partida ha finalizado
        
    }
    
    public void esperarJugadores(int numeroJugadores) throws SocketException{
        int cantidad = 0; 
        
        while(cantidad < numeroJugadores){
            System.out.println("Esperando Jugadores...");
            DatagramPacket paquete = recibirPaquete(); 
            
           
            String[] datos = desempaquetar(paquete); 
            
            //si el paquete es de tipo acceder creamos el jugador y lo agregamos a la lista
            if(datos[0].equals("conectar")){
                
                //creamos un jugador a nivel de conexcion
                int x = Integer.parseInt(datos[2]); 
                int y = Integer.parseInt(datos[3]); 
                jugadores.add(JugadorFactory.crearJugador(datos[1],datos[2],x,y,paquete.getAddress(),paquete.getPort()));
                cantidad++; 
                
                //notficamos a los jugadores que se ha conectado un jugador
                notificar(Mensajero.mensaje("El jugador: "+datos[1]+" se ha conectado!")); 
            }
        }
        
        System.out.println("Jugadores listos");
        mostrarLista(); 
        
    } 
    
    public void mostrarLista(){
        int i = 1; 
        for(Jugador jugador: jugadores){
            System.out.println("Jugador "+i+": "+jugador.getId());
        }
    }
    
    //metodos de notificación
    public void notificar(String mensaje){
        for(Jugador jugador: jugadores){
            if(jugador instanceof Jugador){
                enviarPaquete(PaqueteFactory.crear(mensaje, jugador.getIp(), jugador.getPuerto()));  
            }
        }
    }
    
    public void notificar(String mensaje, InetAddress rutaOmitir){

        for(int i = 0; i < jugadores.size(); i++){
            if(jugadores.get(i).getId().equals(rutaOmitir)){ 
                enviarPaquete(PaqueteFactory.crear(mensaje, jugadores.get(i).getIp(), jugadores.get(i).getPuerto()));
            } else {
            }
        }
    }
    
    
    //metodos para los jugadores     
    public void enviarJugadores(){
        //tenemos la lista de jugadores y se la tenemos que enviar a los demas jugadores
        String lista = "lista,"; 
        
        for(Jugador jugador: jugadores){
            lista += jugador.getId(); 
        }
        
        //enviamos la lista completa a los jugadores 
        notificar(lista); 
    }
    
    //metodos de los adaptadores 
    public void AdapatarJuego(Juego juego){
        adaptadorJuego = new AdaptadorServidorToJuego(juego); 

    }
    
    public void AdaptarAdmin(Administrador admin){
        adapterAdmin = new AdapterServerToAdmin(admin); 
    }

    @Override
    public String[] limpiar(String[] datos) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


}
