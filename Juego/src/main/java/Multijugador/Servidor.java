/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Multijugador;

import Jugador.Administrador;
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
public class Servidor implements ManejadorPaquete,Mensaje, ListaJugadores{
    private static int puerto; 
    private DatagramSocket socket; 
    private static InetAddress IP; 
    private List<Jugador> jugadores = new ArrayList<>(); 
    private AdaptdorServidorToPanel adaptadorPanel; 
    private AdaptadorServidorToAdmin adaptadorAdmin; 
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
    
    
    //metodos basicos 
    public void agregar(Jugador jugador){
        jugadores.add(jugador); 
    }
    
    public void eliminar(Jugador jugador){
        jugadores.remove(jugador); 
    }    
    
    //metodos de paquete 

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
    
    @Override
    public String empaquetar(String[] datos) {
        return String.join(",", datos); 
    }
    

    @Override
    public void interpretar(DatagramPacket paquete) throws SocketException{
        String[] datos = desempaquetar(paquete); 
        String mensaje = empaquetar(datos); 
        
        if(datos[0].equalsIgnoreCase("acceder")){
            //agregamos un nuevo jugador
            agregar(JugadorFactory.crearJugador(datos,paquete.getAddress(),paquete.getPort())); 
            //exepcion en caso de alguna falla 
            
        }else if(datos[0].equals("mover")){
            //Un jugador se ha movido, lo buscamos en la lista 
            int indice = buscarJugador(datos[1]); 
            
            if(indice != -1){
                //El jugador existe, por ende, cambiamos sus coordenadas y notificamos 
                jugadores.get(indice).mover(Integer.parseInt(datos[2]),Integer.parseInt(datos[3]));
                notificar(mensaje,indice);   
            }
        }
    }
    
    
    //metodos de la partida 
    public void crearPartida() throws SocketException{
        //agregamos de primero el administrador a la lista 
        agregar(adaptadorAdmin.traer()); 
        crearServidor(); 
        iniciarPartida(); 
        
    }
    
    public void crearServidor()throws SocketException{
        //solicitamos el puerto y la ip
        System.out.println("Servidor creado, puerto: "+puerto+" ip: "+IP.getHostAddress());
        esperarJugadores(0);
        System.out.println("Jugadores listos");
    }
    
    public void iniciarPartida(){
        partida = true; 
        //Iniciamos la partida 
        adaptadorPanel.iniciarJuego();
        
        //Enviamos un mensaje a cada jugador para que pueden iniciar su partida 
        notificar(Mensajero.mensaje("La partida a comenzado!")); 
        
        //Escuchamos a los jugadore en todo momento
        escucharJugadores(); 
        
        
    }

    public void escucharJugadores(){
        DatagramPacket paquete; 
        while(partida){
            paquete = null; 
            try {
                //Esperamos las notificaciones del juego 
                socket.receive(paquete);
                
                //Recibimos el paquete y lo desempaquetamos 
                String[] datos = desempaquetar(paquete); 
                System.out.println("Paquete recibido! tipo: "+datos[0]);
                
                
                //interpretamos el paquete
                interpretar(paquete); 
                
                
            } catch (IOException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            //Simultaneamente se esta ejecutando el juego por el hilo de IniciarJuegoThread
            //Este ciclo continuará hasta que el admin decida finalizar el juego: partida = false
        }
    }
    
    public void finalizarPartida(){
        //se le envia una notificación a cada jugador diciendo que la partida ha finalizado
        
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
                
                //notficamos a los jugadores que se ha conectado un jugador
                notificar(Mensajero.mensaje("El jugador: "+datos[0]+" se ha conectado!")); 
            }
        }
        
        System.out.println("Jugadores listos");
    } 
    
    //metodos de notificación
    public void notificar(String mensaje){
        for(Jugador jugador: jugadores){
            enviarPaquete(PaqueteFactory.crear(mensaje, jugador.getIp(), jugador.getPuerto())); 
        }
    }
    
    public void notificar(String mensaje, int indiceOmitir){

        for(int i = 0; i < jugadores.size(); i++){
            if(i != indiceOmitir){
                enviarPaquete(PaqueteFactory.crear(mensaje, jugadores.get(i).getIp(), jugadores.get(i).getPuerto())); 
            }
        }
    }
    
    
    //metodos para los jugadores 
    @Override
    public int buscarJugador(String nombre){
        for(int i = 0; i < jugadores.size(); i++){
            if(jugadores.get(i).getId().equalsIgnoreCase(nombre)){
                return i; 
            }
        }
        return -1; 
    }

    public void enviarLista(){
        //tengo una lista de jugadores 
        
        //enviaresmos sublistas de jugadores a cada uno de los jugadores 
        for(int i = 0; i < jugadores.size(); i++){
            for(int j = 0; j < jugadores.size(); j++){
                if(j != i){
                    //creamos el mensaje con la informacíon del jugador 
                    String mensaje = Mensajero.mensajeConectar(jugadores.get(i)); 
                   
                    //enviamos el mensaje a los jugadores 
                    enviarPaquete(PaqueteFactory.crear(mensaje,jugadores.get(j).getIp(),jugadores.get(j).getPuerto())); 
                }
            }
        }
    }
    
    //metodos de los adaptadores 
    public void AdaptarPanel(PanelJuego panel){
        adaptadorPanel = new AdaptdorServidorToPanel(panel); 
    }
    
    public void AdaptarAdmin(Administrador admin){
        adaptadorAdmin = new AdaptadorServidorToAdmin(admin); 
    }


}
