package com.mycompany.ecocrossing;

import Objeto.OBJ_Basura;
import Entidades.NPC_1;
import Entidades.NPC_2;

/**
 *
 * @author Maria Sandoval
 */
public class AssetSetter {
    PanelJuego panelJuego;
    
    
    public AssetSetter (PanelJuego panelJuego){
        this.panelJuego=panelJuego;
    }
    
    public void setObjeto(){
        panelJuego.obj[0]= new OBJ_Basura(panelJuego);
        panelJuego.obj[0].mundoX= 10 * panelJuego.tamannoRecuadros;
        panelJuego.obj[0].mundoY= 10 * panelJuego.tamannoRecuadros;
        
        panelJuego.obj[1]= new OBJ_Basura(panelJuego);
        panelJuego.obj[1].mundoX= 11 * panelJuego.tamannoRecuadros;
        panelJuego.obj[1].mundoY= 19 * panelJuego.tamannoRecuadros;
    }
    
    public void setNPC(){
        panelJuego.npc[0]= new NPC_1(panelJuego);
        panelJuego.npc[0].mundoX= panelJuego.tamannoRecuadros*25;
        panelJuego.npc[0].mundoY= panelJuego.tamannoRecuadros*10;
        
        panelJuego.npc[1]= new NPC_2(panelJuego);
        panelJuego.npc[1].mundoX= panelJuego.tamannoRecuadros*50;
        panelJuego.npc[1].mundoY= panelJuego.tamannoRecuadros*35;
        
    }
}
