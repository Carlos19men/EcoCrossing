/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PanelDeJuego;

import Objetos.OBJ_Basura;

/**
 *
 * @author carlos
 */
public class ColocadorObjeto {
    PanelJuego panelJuego;
    
    public ColocadorObjeto (PanelJuego panelJuego){
        this.panelJuego=panelJuego;
    }
    
    public void setObjeto(){
        panelJuego.obj[0]= new OBJ_Basura();
        panelJuego.obj[0].mundoX= 10 * panelJuego.tamannoRecuadros;
        panelJuego.obj[0].mundoY= 10 * panelJuego.tamannoRecuadros;
        
        panelJuego.obj[1]= new OBJ_Basura();
        panelJuego.obj[1].mundoX= 11 * panelJuego.tamannoRecuadros;
        panelJuego.obj[1].mundoY= 19 * panelJuego.tamannoRecuadros;
    }
}
