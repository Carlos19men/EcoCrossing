package com.mycompany.ecocrossing;

import Objeto.OBJ_Basura;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {
    PanelJuego panelJuego;
    Graphics2D g2;
    Font arial_40, arial_80B;
    BufferedImage imagenBasura;
    public boolean mensajeActivo= false;
    public String mensaje= "";
    public int contadorMensaje=0;
    
    // En un hipotetico caso donde un objeto marque el final de la aventura
    // supongamos un cofre...
    /*
    Las instrucciones serian:
    case "ObjetoNombre":
        gp.ui.juegoFinalizdo= true;
        gp.pararMusica();
        gp.reproducirEfectoSonido(i);
        break;
    
    En el metodo de dibujar comprobariamos el valor de juegoFinalizado...
    */
    
    public boolean juegoFinalizado= false;
    double tiempoJuego;
    DecimalFormat formatoDecimal= new DecimalFormat("#0.00");
    public int comandoNumerico= 0;
    public int estadoPantallaTitulo= 0; // El númeri indica la pantalla actual
    
    public UI(PanelJuego panelJuego){
        this.panelJuego=panelJuego;
        arial_40= new Font("Arial", Font.PLAIN, 40);
        arial_80B= new Font("Arial", Font.BOLD, 80);
        OBJ_Basura basura= new OBJ_Basura(panelJuego);
        imagenBasura= basura.imagen;
    }
    
    public void mostrarMensaje(String texto) {
        mensaje= texto;
        mensajeActivo= true;
    }
    
    public void dibujar(Graphics2D g2) {
        this.g2=g2;
        
        if(juegoFinalizado==true) {
            g2.setFont(arial_40);
            g2.setColor(Color.white);
            
            String texto;
            int longitudTexto;
            int x, y;
            
            texto= "Lo has encontrado!";
            longitudTexto=(int)g2.getFontMetrics().getStringBounds(texto, g2).getWidth();
            x= panelJuego.anchoPantalla/2 - longitudTexto/2;
            y= panelJuego.largoPantalla/2 - (panelJuego.tamannoRecuadros*3);
            g2.drawString(texto, x, y);
            
            // Para mostrar el tiempo que te tomo completar el juego:
            texto= "Tiempo de Aventura:"+formatoDecimal.format(tiempoJuego)+"!";
            longitudTexto=(int)g2.getFontMetrics().getStringBounds(texto, g2).getWidth();
            x= panelJuego.anchoPantalla/2 - longitudTexto/2;
            y= panelJuego.largoPantalla/2 + (panelJuego.tamannoRecuadros*4);
            g2.drawString(texto, x, y);
            
            g2.setFont(arial_80B);
            g2.setColor(Color.yellow);
            texto= "Felicidades";
            longitudTexto=(int)g2.getFontMetrics().getStringBounds(texto, g2).getWidth();
            x= panelJuego.anchoPantalla/2 - longitudTexto/2;
            y= panelJuego.largoPantalla/2 - (panelJuego.tamannoRecuadros*2);
            g2.drawString(texto, x, y);
            panelJuego.juegoThread=null;
        }
        // Verificar más adelante si esto esta del todo bien...
        else if(panelJuego.estadoJuego==panelJuego.estadoJugando){
            g2.setFont(arial_40);
            g2.setColor(Color.white);
            g2.drawImage(imagenBasura, panelJuego.tamannoRecuadros/2, panelJuego.tamannoRecuadros/2, panelJuego.tamannoRecuadros, panelJuego.tamannoRecuadros, null);
            g2.drawString("x "+ panelJuego.jugador.cntBasura, 74, 65);
            
            // TIEMPO
            /*tiempoJuego += (double)1/60;
            g2.drawString("Tiempo:"+formatoDecimal.format(tiempoJuego), panelJuego.tamannoRecuadros*10.5f, 65);*/
            
            // MENSAJES
            if(mensajeActivo==true) {
                g2.setFont(g2.getFont().deriveFont(30f));
                g2.drawString(mensaje, panelJuego.tamannoRecuadros/2, panelJuego.tamannoRecuadros*5);
                contadorMensaje++;
                if(contadorMensaje > 120) {
                    contadorMensaje=0;
                    mensajeActivo=false;
                }
            }
        }  
        
        g2.setFont(arial_40);
        g2.setColor(Color.white);
        
        // Pantalla titulo:
        if(panelJuego.estadoJuego==panelJuego.estadoTitulo) {
            dibujarPantallaTitulo();
        }
        
        // Pausa:
        if(panelJuego.estadoJuego==panelJuego.estadoJugando) {
            // Para más adelante...
        }
        else if(panelJuego.estadoJuego==panelJuego.estadoPausa) {
            dibujarPantallaPausa();
        }
        
        
    }
    
    public void dibujarPantallaTitulo() {
        if(estadoPantallaTitulo == 0) {
            g2.setColor(new Color(0,0,0)); 
            g2.fillRect(0, 0, panelJuego.anchoPantalla, panelJuego.largoPantalla);

            // Nombre del título:
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 60f));
            String texto="EcoCrossing Adventure";
            int x= obtenerXParaTextoCentrado(texto);
            int y= panelJuego.tamannoRecuadros*2;
            g2.setColor(Color.white);
            g2.drawString(texto, x, y);
        
            // Sombra:
            g2.setColor(Color.gray);
            g2.drawString(texto, x+5, y+5);
            // Color del Menú:
            g2.setColor(Color.white);
            g2.drawString(texto, x, y);
        
            // Personaje Principal Imagen:
            x= panelJuego.anchoPantalla/2 - (panelJuego.tamannoRecuadros);
            y+= panelJuego.tamannoRecuadros*2;
            g2.drawImage(panelJuego.jugador.abajo1, x, y, panelJuego.tamannoRecuadros*2, panelJuego.tamannoRecuadros*2, null);
    
            // Menú_
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30f));
        
            texto= "NUEVO JUEGO";
            x= obtenerXParaTextoCentrado(texto);
            y+= panelJuego.tamannoRecuadros*3.5;
            g2.drawString(texto, x, y);
            if(comandoNumerico==0) {
                g2.drawString(">", x-panelJuego.tamannoRecuadros, y);
            }
        
            texto= "CARGAR JUEGO";
            x= obtenerXParaTextoCentrado(texto);
            y+= panelJuego.tamannoRecuadros;
            g2.drawString(texto, x, y);
            if(comandoNumerico==1) {
                g2.drawString(">", x-panelJuego.tamannoRecuadros, y);
            }
        
            texto= "SALIR";
            x= obtenerXParaTextoCentrado(texto);
            y+= panelJuego.tamannoRecuadros;
            g2.drawString(texto, x, y);
            if(comandoNumerico==2) {
                g2.drawString(">", x-panelJuego.tamannoRecuadros, y);
            }
        }
        else if(estadoPantallaTitulo == 1) {
            // Selección de Personalización de Personaje:
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(42f));
            
            String texto= "Selecciona tu personaje!";
            int x=obtenerXParaTextoCentrado(texto);
            int y= panelJuego.tamannoRecuadros*3;
            g2.drawString(texto, x, y);
            
            texto="Personaje 1";
            x= obtenerXParaTextoCentrado(texto);
            y+=panelJuego.tamannoRecuadros*3;
            g2.drawString(texto, x, y);
            if(comandoNumerico == 0) {
                g2.drawString(">", x-panelJuego.tamannoRecuadros, y);
            }
            
            texto="Personaje 2";
            x= obtenerXParaTextoCentrado(texto);
            y+=panelJuego.tamannoRecuadros;
            g2.drawString(texto, x, y);
            if(comandoNumerico == 1) {
                g2.drawString(">", x-panelJuego.tamannoRecuadros, y);
            }
            
            texto="Personaje 3";
            x= obtenerXParaTextoCentrado(texto);
            y+=panelJuego.tamannoRecuadros;
            g2.drawString(texto, x, y);
            if(comandoNumerico == 2) {
                g2.drawString(">", x-panelJuego.tamannoRecuadros, y);
            }
            
            texto="Volver";
            x= obtenerXParaTextoCentrado(texto);
            y+=panelJuego.tamannoRecuadros*2;
            g2.drawString(texto, x, y);
            if(comandoNumerico == 3) {
                g2.drawString(">", x-panelJuego.tamannoRecuadros, y);
            }
        }
    }
    
    public void dibujarPantallaPausa() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80f));
        String texto="PAUSA";
        int x= obtenerXParaTextoCentrado(texto);
        int y= panelJuego.largoPantalla/2;
        
        g2.drawString(texto, x, y);
    }
    
    public int obtenerXParaTextoCentrado(String texto) {
        int longitud=(int)g2.getFontMetrics().getStringBounds(texto, g2).getWidth();
        int x= panelJuego.anchoPantalla/2 -longitud/2;
        return x;
    }
}
