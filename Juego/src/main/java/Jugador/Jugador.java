/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Jugador;

import Juego.AdministradorObjetos;
import Juego.Juego;
import Multijugador.Comunicador;
import PanelJuego.PanelJuego;
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
import Multijugador.Mensajero; 
import Multijugador.PaqueteFactory;
import Multijugador.Servidor;
import com.mycompany.juego.mainJuego;
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
public class Jugador extends Entidad implements ManejadorPaquete, Comunicador{
    //Atributos
    protected String id; 
    protected Personaje personaje; 
    protected String nombrePersonaje; 
    protected InetAddress ip; 
    protected int puerto; 
    protected PanelJuego panel; 
    protected ManejadorTeclado teclado; 
    protected mainJuego juego; 

    
    //atributos para el manejo del mundo del juego
    public int pantallaX;
    public int pantallaY;
    
    //variables locales 
    InetAddress ipServer; 
    int puertoServer; 
    
    
    
    
    
    //constructores ---------------------------------------------------------------------
    public Jugador() {
    }

    public Jugador(String id, String nombrePersonaje,InetAddress ip, int puerto) throws SocketException {
        this.id = id;
        this.nombrePersonaje = nombrePersonaje; 
        this.personaje = new Personaje(nombrePersonaje);
        this.ip = ip; 
        this.puerto = puerto; 
        
        //configuramos al jugador
    }
    
    //jugadores a nivel de lista
    public Jugador(String id, String nombrePersonaje){
        this.id = id; 
        this.nombrePersonaje = nombrePersonaje; 
        this.personaje = new Personaje(nombrePersonaje);
    }
    
    public Jugador(String id, String nomprePersonaje, int mundoX, int mundoY, InetAddress ip, int puerto) throws SocketException{
        this.id = id;
        this.nombrePersonaje = nombrePersonaje; 
        this.personaje = new Personaje(nombrePersonaje);
        this.ip = ip; 
        this.puerto = puerto; 
        this.mundoX = mundoX; 
        this.mundoY = mundoY; 
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

    public PanelJuego getPanel() {
        return panel;
    }

    public void setPanel(PanelJuego panel) {
        this.panel = panel;
    }

    public ManejadorTeclado getTeclado() {
        return teclado;
    }

    public void setTeclado(ManejadorTeclado teclado) {
        this.teclado = teclado;
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

    public mainJuego getJuego() {
        return juego;
    }

    public void setJuego(mainJuego juego) {
        this.juego = juego;
    }

    public InetAddress getIpServer() {
        return ipServer;
    }

    public void setIpServer(InetAddress ipServer) {
        this.ipServer = ipServer;
    }

    public int getPuertoServer() {
        return puertoServer;
    }

    public void setPuertoServer(int puertoServer) {
        this.puertoServer = puertoServer;
    }
    
    
    
    //metodos
    @Override
    public DatagramPacket recibirPaquete() {
        try {
            byte[] datos = new byte[1024];
            DatagramSocket socket = new DatagramSocket(puerto);
            //preparamos el paquete que vamos a recibir
            DatagramPacket paquete = new DatagramPacket(datos, datos.length);
            
             //esperamos el paquete
            socket.receive(paquete);
            System.out.println("Paquete recibido de: " + paquete.getAddress() + ":" + paquete.getPort());
            socket.close();
            return paquete;
        } catch (SocketException ex) {
            Logger.getLogger(Jugador.class.getName()).log(Level.SEVERE, null, ex);
           //falta crear un exepcion 
        } catch (IOException ex) {     
            Logger.getLogger(Jugador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

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
    
    public void subirAlServidor(String mensaje){
        enviarPaquete(PaqueteFactory.crear(mensaje, ipServer, puertoServer)); 
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
    
    public void configurar(PanelJuego panel){
        teclado = new ManejadorTeclado();  
        this.panel = panel; 
        panel.setJugador(this);
        this.panel.agregarTeclado(teclado);
                
        
        
        //Para esta función primero se tiene que crear el teclado y el panel 
        
        //valores de la pantalla 
        pantallaX = (panel.anchoPantalla - panel.tamannoRecuadros) / 2;
        pantallaY = (panel.largoPantalla - panel.tamannoRecuadros) / 2;
        
        //configuramos los valores por defecto
        mundoX = panel.tamannoRecuadros * 15;
        mundoY = panel.tamannoRecuadros * 15;
        velocidad = 4;
        direccion = "frente";      
        
        
        //Determinar la mitad de la pantalla   
        areaSolida = new Rectangle();
        areaSolida.x=8;
        areaSolida.y=16;
        areaSolidaDefaultX=areaSolida.x;
        areaSolidaDefaultY=areaSolida.y;
        areaSolida.width=32;
        areaSolida.height=32;
        
        //cargamos las imagenes del jugador 
        setImagenJugador();
        
        //valores del jugador 
        mundoX = 100;
        mundoY = 100; 
    }
    
    
    
    //metodos de manipulacción de lo sprite
    
    public void setImagenJugador() {
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

 
    
    public void mover(int x, int y,String direccion){
        mundoX = x; 
        mundoY = y; 
        this.direccion = direccion; 
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
        
        
        //pantallaX = this.mundoX - panelJuego.jugador.mundoX + panelJuego.jugador.pantallaX;
        //pantallaY = this.mundoY - panelJuego.jugador.mundoY + panelJuego.jugador.pantallaY;
    
        g2.drawImage(imagen, pantallaX, pantallaY, panel.tamannoRecuadros, panel.tamannoRecuadros, null);
        if (id != null) {
            g2.setFont(new Font("Courier New", Font.BOLD, 18));
            g2.setColor(Color.WHITE);
            int nombreX = pantallaX + (panel.tamannoRecuadros / 2) - (g2.getFontMetrics().stringWidth(id) / 2);
            int nombreY = pantallaY - 10;
            g2.drawString(id, nombreX, nombreY);
        }
        
        
    }
    

    
    
    //metodos para el multijugador 
    public void conectarse(int puertoServidor, InetAddress rutaServidor){
        
        //guardamos los valores del servidor 
        ipServer = rutaServidor; 
        puertoServer = puertoServidor; 
        
        //enviamos el paquete para solictar la conexion de este jugador
        enviarPaquete(PaqueteFactory.crear(Mensajero.mensajeConectar(this),ipServer,puertoServer));
        
        //esperamos a que acepten nuestra solicitud
        DatagramPacket paquete = recibirPaquete(); 
        
        //Mostramos el mensaje
        String[] datos = desempaquetar(paquete); 
        
        System.out.println(empaquetar(datos));        
    }
    
    public void jugar(){
        //en este punto ya estamos conectados al servidor 
        
        //vamos a recibi el paquete para inciar la partida 
        
        //configuramos el panel 
        
        
        DatagramPacket paquete = recibirPaquete(); 
        
        String[] datos = desempaquetar(paquete); 
        
        //verificamos el paquete 
        if(datos[1].equalsIgnoreCase("jugar")){
            //iniciamos 
        }
    }
    
    public void escucharServidor(){
        try {
            DatagramSocket socket = new DatagramSocket(puerto);
            DatagramPacket paquete;
            
            while(true){
                paquete = null;
                try {
                    
                    //esperamos un paquete del servidor
                    socket.receive(paquete);
                    
                    String[] datos = desempaquetar(paquete);
                    
                    System.out.println("Paquete recibido: tipo: "+datos[0]);
                    
                } catch (IOException ex) {
                    Logger.getLogger(Jugador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SocketException ex) {
            Logger.getLogger(Jugador.class.getName()).log(Level.SEVERE, null, ex);
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
                subirAlServidor(Mensajero.mensajeMover(this, direccion)); 
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

    @Override
    public String[] limpiar(String[] datos) {
        for(int i = 0; i < datos.length; i++){
            datos[i] = null; 
        }
        return datos; 
    }


}
