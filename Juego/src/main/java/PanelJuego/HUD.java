package PanelJuego;

import Jugador.Jugador;
import Objetos.OBJ_Basura;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class HUD {
    PanelJuego panelJuego;
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
    
    public HUD(PanelJuego panelJuego){
        this.panelJuego = panelJuego;
        arial_40= new Font("Arial", Font.PLAIN, 40);
        arial_80B= new Font("Arial", Font.BOLD, 80);
        OBJ_Basura basura= new OBJ_Basura();
        imagenBasura= basura.imagen;
    }
    
    public void mostrarMensaje(String texto) {
        mensaje= texto;
        mensajeActivo= true;
    }
    
    public void dibujar(Graphics2D g2, Jugador jugador) {
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
            
        }else{
            g2.setFont(arial_40);
            g2.setColor(Color.white);
            g2.drawImage(imagenBasura, panelJuego.tamannoRecuadros/2, panelJuego.tamannoRecuadros/2, panelJuego.tamannoRecuadros, panelJuego.tamannoRecuadros, null);
            //g2.drawString("x "+ jugador.cntBasura, 74, 65);
            
            // TIEMPO
            tiempoJuego += (double)1/60;
            g2.drawString("Tiempo:"+formatoDecimal.format(tiempoJuego), panelJuego.tamannoRecuadros*10.5f, 65);
            
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
        
        
    }
}
