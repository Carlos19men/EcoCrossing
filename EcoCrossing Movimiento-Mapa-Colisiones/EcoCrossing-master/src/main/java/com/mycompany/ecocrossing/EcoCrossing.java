package com.mycompany.ecocrossing;
import javax.swing.JFrame;

public class EcoCrossing {

    public static void main(String[] args) {
        JFrame ventana = new JFrame();
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        ventana.setTitle("ECO Crossing: Aventuras Verdes");
        
        PanelJuego panelJuego = new PanelJuego();
        ventana.add(panelJuego);
        ventana.pack();
        
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
        panelJuego.setupJuego();
        panelJuego.iniciarjuegoThread();
    }
}
