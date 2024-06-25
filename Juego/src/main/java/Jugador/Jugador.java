/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Jugador;

import Multijugador.ListaJugadores;
import PanelDeJuego.PanelJuego;
import Multijugador.ManejadorPaquete;
import Multijugador.Mensaje;
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
import Multijugador.Mensajero; 
import Multijugador.PaqueteFactory;
import Multijugador.Servidor;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carlos
 */
public class Jugador extends Entidad implements ManejadorPaquete, Mensaje, ListaJugadores{
    //atributos del personaje 
    private String id; 
    private Personaje personaje; 
    private String personajeNombre; //<----------------------------------------- editar para borrar
   
    //atributos para el online 
    private InetAddress ip; 
    private int puerto; 
    private DatagramSocket socket; 
    
    //datos del panel de juego 
    public PanelJuego panel; 
    
    //atributos para el manejo del mundo del juego

    public int mundoX = 100, mundoY = 100; 
    public final int pantallaX = (panel.anchoPantalla - panel.tamannoRecuadros) / 2;
    public final int pantallaY = (panel.largoPantalla - panel.tamannoRecuadros) / 2;
    
    //datos del personaje del jugador
    private ManejadorTeclado teclado; 
    private String direccionAnterior;
    
    //lista de jugadores conectados 
    List<Jugador> subLista = new ArrayList<>(); 

    //Datos del servidor
    InetAddress hostServer; 
    int puertoServer; 
    
    
    
    //constructores ---------------------------------------------------------------------
    public Jugador() {
    }

    public Jugador(String id, String personaje,InetAddress ip, int puerto) throws SocketException {
        this.id = id;
        personajeNombre = personaje;    
        this.socket = new DatagramSocket(puerto);        
        this.ip = ip; 
    }
    
    public Jugador(String id, String personaje){
        this.id = id; 
        this.personajeNombre = personaje;
    }
    //--------------------------------------------------------------------------------------
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

    public String getDireccionAnterior() {
        return direccionAnterior;
    }

    public void setDireccionAnterior(String direccionAnterior) {
        this.direccionAnterior = direccionAnterior;
    }

    public String getPersonajeNombre() {
        return personajeNombre;
    }

    public void setPersonajeNombre(String personajeNombre) {
        this.personajeNombre = personajeNombre;
    }

    public int getMundoX() {
        return mundoX;
    }

    public void setMundoX(int mundoX) {
        this.mundoX = mundoX;
    }

    public int getMundoY() {
        return mundoY;
    }

    public void setMundoY(int mundoY) {
        this.mundoY = mundoY;
    }

    public List<Jugador> getSubLista() {
        return subLista;
    }

    public void setSubLista(List<Jugador> subLista) {
        this.subLista = subLista;
    }
    
    
    
    //metodos basicos de la clase 
    public void agregar(Jugador jugador){
        subLista.add(jugador); 
    }

    @Override
    public int buscarJugador(String nombre){
        for(int i = 0; i < subLista.size(); i++){
            if(subLista.get(i).getId().equalsIgnoreCase(nombre)){
                return i; 
            }
        }
        return -1; 
    }
    

    
    //metodos del manejo de paquetes 
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
    
    @Override
    public String empaquetar(String[] datos) {
        return String.join(",", datos); 
    }
    
    public void subirAlServidor(String mensaje){
        enviarPaquete(PaqueteFactory.crear(mensaje, hostServer, puertoServer));
    }
    
    //metodos del manejo de teclas 
    public void crearManejadorDeTeclado(){
        teclado = new ManejadorTeclado(); 
    }
    
    //metodos del panel de juego
    public void crearPanelJuegoAdministrador(Servidor server){
        panel = new PanelJuego(this,teclado,server); 
    }
    
    public void crearPanelJuegoJugador(){
        panel = new PanelJuego(this,teclado); 
    }
    
    
    //metodos para inicializar valores por defecto      
    public void inicializarCoordenadas(){
        //Determinar la mitad de la pantalla 
        
        areaSolida = new Rectangle();
        areaSolida.x=8;
        areaSolida.y=16;
        areaSolidaDefaultX=areaSolida.x;
        areaSolidaDefaultY=areaSolida.y;
        areaSolida.width=32;
        areaSolida.height=32;
        valoresPorDefecto();
        getImagenJugador();
        
        // Inicializar la dirección anterior con la misma dirección inicial
        direccionAnterior = direccion;
    }
    
    public void valoresPorDefecto() {
        mundoX = panel.tamannoRecuadros * 15;
        mundoY = panel.tamannoRecuadros * 15;
        velocidad = 4;
        direccion = "frente";      
    }

    
    
    //metodos de manipulacción de lo sprite
    
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

 
    
    public void mover(int x, int y){
        mundoX = x; 
        mundoY = y; 
    }
    
    public void dibujar(Graphics2D g2) {
        BufferedImage imagen = frente;
        switch (direccion) {
            case "arriba":
                if (spriteNum == 1)
                    imagen = arriba1;
                else if (spriteNum == 2)
                    imagen = arriba2;
                break;
            case "abajo":
                if (spriteNum == 1)
                    imagen = abajo1;
                else if (spriteNum == 2)
                    imagen = abajo2;
                break;
            case "derecha":
                if (spriteNum == 1)
                    imagen = der1;
                else if (spriteNum == 2)
                    imagen = der2;
                break;
            case "izquierda":
                if (spriteNum == 1)
                    imagen = izq1;
                else if (spriteNum == 2)
                    imagen = izq2;
                break;
            case "frente":
                imagen = frente;
                break;
            case "espalda":
                imagen = espalda;
                break;
            case "lado1":
                imagen = lado1;
                break;
            case "lado2":
                imagen = lado2;
                break;
        }
        
        /*
        int  = this.mundoX - panelJuego.jugador.mundoX + panelJuego.jugador.pantallaX;
        pantallaY = this.mundoY - panelJuego.jugador.mundoY + panelJuego.jugador.pantallaY;
    
        g2.drawImage(imagen, pantallaX, pantallaY, panelJuego.tamannoRecuadros, panelJuego.tamannoRecuadros, null);
        if (nombreUsuario != null) {
            g2.setFont(new Font("Courier New", Font.BOLD, 18));
            g2.setColor(Color.WHITE);
            int nombreX = pantallaX + (panelJuego.tamannoRecuadros / 2) - (g2.getFontMetrics().stringWidth(nombreUsuario) / 2);
            int nombreY = pantallaY - 10;
            g2.drawString(nombreUsuario, nombreX, nombreY);
        }*/
        
        
    }
    

    
    
    //metodos para el multijugador 
    public void conectarse(int puertoServidor, InetAddress rutaServidor){
        
        //guardamos los valores del servidor 
        hostServer = rutaServidor; 
        puertoServer = puertoServidor; 
        
        //enviamos el paquete para solictar la conexion de este jugador
        enviarPaquete(PaqueteFactory.crear(Mensajero.mensajeConectar(this),rutaServidor,puertoServidor));
        
        //esperamos a que acepten nuestra solicitud
        DatagramPacket paquete = recibirPaquete(); 
        
        //Mostramos el mensaje
        String[] datos = desempaquetar(paquete); 
        
        System.out.println("Paquete recibido: tipo :"+datos[0]+" mensaje: "+datos[1]);
        
        //recibimos la lista de jugadores conectados
        
        
        //ahora esperamos a que el administrador incie la partida
        paquete = recibirPaquete(); 
        
        //iniciamos el juego
        panel.iniciarjuegoThread();
        
        //escuchamos al servidor
        
        
    }
    
    public void escucharServidor(){
        DatagramPacket paquete; 
 
        while(true){
            paquete = null; 
            try {
                
                //esperamos un paquete del servidor
                socket.receive(paquete);
                
                String[] datos = desempaquetar(paquete); 
                
                System.out.println("Paquete recibido: tipo: "+datos[0]);
                
                interpretar(paquete); 
                         
            } catch (IOException ex) {
                Logger.getLogger(Jugador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    

    @Override
    public void interpretar(DatagramPacket paquete) throws SocketException{
        String[] datos = desempaquetar(paquete); 
        String mensaje = empaquetar(datos); 
        
        if(datos[0].equals("conectar")){
            
            //agregamos al jugador a la sublista
            agregar(JugadorFactory.crearJugador(datos[1],datos[4]));
            
        }else if(datos[0].equals("mover")){
            //Un jugador se ha movido, lo buscamos en la lista 
            int indice = buscarJugador(datos[1]); 
            
            if(indice != -1){
                //El jugador existe, por ende, cambiamos sus coordenadas y notificamos 
                subLista.get(indice).mover(Integer.parseInt(datos[2]),Integer.parseInt(datos[3]));
            }
        }
    }
    
    //metodos de actualizacion dentro del juego
    public void actualizar() {
        if (teclado != null) {
            boolean moviendose = false;
            
            if (teclado.arribaPresionado || teclado.abajoPresionado || teclado.derechaPresionado || teclado.izquierdaPresionado) {
                moviendose = true;
                if (teclado.arribaPresionado) {
                    direccion = "arriba";
                } else if (teclado.izquierdaPresionado) {
                    direccion = "izquierda";
                } else if (teclado.abajoPresionado) {
                    direccion = "abajo";
                } else if (teclado.derechaPresionado) {
                    direccion = "derecha";
                }
                colisionActivada = false;
                
                panel.verificarC.VerificarRecuadro(this);
                
                int indiceObjeto = panel.verificarC.VerificarObjeto(this, true);
                
               // recogerObjeto(indiceObjeto);
                
                if (!colisionActivada) {
                    switch (direccion) {
                        case "arriba":
                            mundoY -= velocidad;
                            break;
                        case "abajo":
                            mundoY += velocidad;
                            break;
                        case "derecha":
                            mundoX += velocidad;
                            break;
                        case "izquierda":
                            mundoX -= velocidad;
                            break;
                    }
                    subirAlServidor(Mensajero.mensajeMover(this, direccion)); 
                    
                }
                actualizarAnimacion();
            }
            
            if (!moviendose) {
                if ("arriba".equals(direccion))
                    direccion = "espalda";
                else if ("abajo".equals(direccion))
                    direccion = "frente";
                else if ("derecha".equals(direccion))
                    direccion = "lado1";
                else if ("izquierda".equals(direccion))
                    direccion = "lado2";
                
                // Verificar si la dirección ha cambiado antes de enviar el paquete
                if (!direccion.equals(direccionAnterior)) {
                    subirAlServidor(Mensajero.mensajeMover(this, direccion)); 
                    direccionAnterior = direccion;
                }
            }
        }
    }
    
    public void actualizarAnimacion() {
        spriteCont++;
        if (spriteCont > 12) {
            if (spriteNum == 2)
                spriteNum = 1;
            else if (spriteNum == 1)
                spriteNum = 2;
            spriteCont = 0;
        }
    }


    
   /* public void recogerObjeto(int indice) {
        if (indice != 999) {
            switch (panel.obj[indice].nombre) {
                case "Basura":
                    if (teclado.recogerObjetoPresionado) {
                        panel.obj[indice] = null;
                        panel.reproducirEfectosSonido(1);
                        cntBasura++;
                        panel.ui.mostrarMensaje("Conseguiste basura!");
                    }
                    break;
            }    // Agregar más objetos según sea necesario
        }
    }*/
    

}
