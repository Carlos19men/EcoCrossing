package Entidades;

import com.mycompany.ecocrossing.ManejoTeclas;
import com.mycompany.ecocrossing.PanelJuego;
import java.net.InetAddress;

public class JugadorMultijugador extends Jugador{
    public InetAddress direccionIP;
    public int puerto;
   
    //*****Ajustar en caso de añadir mas campos a jugador*********//
    public JugadorMultijugador(PanelJuego panelJuego, ManejoTeclas manejoTeclas, InetAddress direccionIP, int puerto){       
        super(panelJuego, manejoTeclas);
        this.direccionIP = direccionIP;
        this.puerto = puerto;
    }
    
    //No recibe el manejo de teclas
     public JugadorMultijugador(PanelJuego panelJuego, InetAddress direccionIP, int puerto){       
        super(panelJuego, null);
        this.direccionIP = direccionIP;
        this.puerto = puerto;
    }   
}
