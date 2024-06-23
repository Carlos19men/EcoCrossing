/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Multijugador;

import PanelDeJuego.PanelJuego;

/**
 *
 * @author carlos
 */
public class AdaptadorServidorPanel {
    private PanelJuego panel; 

    public AdaptadorServidorPanel(PanelJuego panel) {
        this.panel = panel;
    }
    
    
    public void iniciarJuego(){
        panel.iniciarjuegoThread();
    }
    
}
