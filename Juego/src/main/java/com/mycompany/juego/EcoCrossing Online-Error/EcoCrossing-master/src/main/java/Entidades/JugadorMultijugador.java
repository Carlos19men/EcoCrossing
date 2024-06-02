package Entidades;

import com.mycompany.ecocrossing.ManejoTeclas;
import com.mycompany.ecocrossing.PanelJuego;
import java.awt.Graphics2D;
import java.net.InetAddress;

public class JugadorMultijugador extends Jugador{
    public InetAddress direccionIP;
    public int puerto;
   
    //*****Ajustar en caso de añadir mas campos a jugador*********//
    public JugadorMultijugador(String nombreUsuario, PanelJuego panelJuego, ManejoTeclas manejoTeclas, InetAddress direccionIP, int puerto){       
        super(nombreUsuario, panelJuego, manejoTeclas);
        this.direccionIP = direccionIP;
        this.puerto = puerto;
    }
    
    //No recibe el manejo de teclas
     public JugadorMultijugador(String nombreUsuario, PanelJuego panelJuego, InetAddress direccionIP, int puerto){       
        super(nombreUsuario, panelJuego, null);
        this.direccionIP = direccionIP;
        this.puerto = puerto;
    }  
    
    @Override
     public void actualizar(){
         super.actualizar();
     }
     
      @Override
    public void dibujar(Graphics2D g2) {
        super.dibujar(g2);
    }
}
