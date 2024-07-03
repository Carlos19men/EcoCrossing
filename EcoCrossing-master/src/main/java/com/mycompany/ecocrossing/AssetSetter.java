package com.mycompany.ecocrossing;

import Objeto.Basura;
import Entidades.NPC_1;
import Entidades.NPC_2;
import java.util.Random; 
/**
 *
 * @author Maria Sandoval
 */
public class AssetSetter {
    PanelJuego panelJuego;
    
    //contador del indice 
    static int indiceObjeto = 0; 
    
    public AssetSetter (PanelJuego panelJuego){
        this.panelJuego=panelJuego;
    }
    
    public void setObjeto(){
        //Ensuciar 
        String[] lista = {"papelera","papel","lata","carton","banana"};
        ensuciar(lista,20); 
    }
    
    public void setNPC(){
        panelJuego.npc[0]= new NPC_1(panelJuego);
        panelJuego.npc[0].mundoX= panelJuego.tamannoRecuadros*25;
        panelJuego.npc[0].mundoY= panelJuego.tamannoRecuadros*10;
        
        panelJuego.npc[1]= new NPC_2(panelJuego);
        panelJuego.npc[1].mundoX= panelJuego.tamannoRecuadros*50;
        panelJuego.npc[1].mundoY= panelJuego.tamannoRecuadros*35;
        
    }
    
    
    public void ensuciar(String[] lista, int cantidad){
        Random ramdon = new Random(); 
        //esta funcion coloca basura en una opcion especifica del mapa
        
        //establecesmo las limitaciones 
        
        for(int i = 0; i < cantidad; i++){
            int ramdonX = ramdon.nextInt(15,40);
            int ramdonY = ramdon.nextInt(7,26);
            int j = ramdon.nextInt(lista.length); 
            
            //cargamos el objeto aleatorio 
            panelJuego.obj[indiceObjeto] = new Basura(lista[j]);
            ((Basura) panelJuego.obj[indiceObjeto]).cargar(panelJuego.tamannoRecuadros);
            panelJuego.obj[indiceObjeto].ubicar(ramdonX, ramdonY, panelJuego.tamannoRecuadros);
            indiceObjeto++; 
        }
    }
}
