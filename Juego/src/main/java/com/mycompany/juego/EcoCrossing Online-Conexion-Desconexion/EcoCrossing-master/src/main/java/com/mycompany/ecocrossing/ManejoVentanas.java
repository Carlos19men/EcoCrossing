package com.mycompany.ecocrossing;

import EcoCrossing.net.paquetes.Paquete01Desconectar;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

public class ManejoVentanas implements WindowListener{
    private final PanelJuego panelJuego;
    private final JFrame ventana;
    
    public ManejoVentanas(PanelJuego panelJuego, JFrame ventana){
        this.panelJuego = panelJuego;
        this.ventana = ventana;
    }
        
    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        Paquete01Desconectar paquete = new Paquete01Desconectar(this.panelJuego.jugador.getNombreUsuario());
        paquete.escribirDatos(this.panelJuego.clienteSocket);
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
    
}
