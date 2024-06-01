/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objeto;

import com.mycompany.ecocrossing.PanelJuego;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Maria Sandoval
 */
public class SuperObjeto {
    public BufferedImage imagen;
    public String nombre;
    public boolean colision=false;
    public int mundoX, mundoY;
    
    public void dibujar (Graphics2D g2, PanelJuego panelJuego){
       int pantallaX = mundoX - panelJuego.jugador.mundoX + panelJuego.jugador.pantallaX;
       int pantallaY = mundoY - panelJuego.jugador.mundoY + panelJuego.jugador.pantallaY;
        
       if (mundoX + panelJuego.tamannoRecuadros > panelJuego.jugador.mundoX - panelJuego.jugador.pantallaX
          && mundoX - panelJuego.tamannoRecuadros < panelJuego.jugador.mundoX + panelJuego.jugador.pantallaX 
          && mundoY + panelJuego.tamannoRecuadros > panelJuego.jugador.mundoY - panelJuego.jugador.pantallaY
          && mundoY - panelJuego.tamannoRecuadros < panelJuego.jugador.mundoY + panelJuego.jugador.pantallaY) {
            g2.drawImage(imagen, pantallaX, pantallaY, panelJuego.tamannoRecuadros, panelJuego.tamannoRecuadros, null);
       }
    }
}
