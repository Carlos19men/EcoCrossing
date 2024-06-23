/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PanelDeJuego;
import Jugador.Entidad; 

/**
 *
 * @author Maria Sandoval
 */
public class VerificarColision {
    PanelJuego panelJuego;
    
    public VerificarColision(PanelJuego panelJuego){
            this.panelJuego=panelJuego;
    }
    
    public void VerificarRecuadro (Entidad entidad){
        int entidadIzqMundoX= entidad.mundoX + entidad.areaSolida.x;
        int entidadDerMundoX= entidad.mundoX + entidad.areaSolida.x + entidad.areaSolida.width;
        int entidadArribaMundoY= entidad.mundoY + entidad.areaSolida.y;
        int entidadAbajoMundoY= entidad.mundoY + entidad.areaSolida.y + entidad.areaSolida.height;
        
        int entidadIzqColumna= entidadIzqMundoX/panelJuego.tamannoRecuadros;
        int entidadDerColumna= entidadDerMundoX/ panelJuego.tamannoRecuadros;
        int entidadArribaFila= entidadArribaMundoY/panelJuego.tamannoRecuadros;
        int entidadAbajoFila= entidadAbajoMundoY/panelJuego.tamannoRecuadros;
        int recuadro1, recuadro2;
        switch(entidad.direccion){
            case "arriba":
                entidadArribaFila=(entidadArribaMundoY-entidad.velocidad)/panelJuego.tamannoRecuadros;
                recuadro1=panelJuego.administradorRecuadros.numeroRecuadroMapa[entidadIzqColumna][entidadArribaFila];
                recuadro2= panelJuego.administradorRecuadros.numeroRecuadroMapa[entidadDerColumna][entidadArribaFila];
                if (panelJuego.administradorRecuadros.recuadros[recuadro1].colision 
                        || panelJuego.administradorRecuadros.recuadros[recuadro2].colision)
                    entidad.colisionActivada=true;
                break;
            case "abajo":
                entidadAbajoFila=(entidadAbajoMundoY+entidad.velocidad)/panelJuego.tamannoRecuadros;
                recuadro1=panelJuego.administradorRecuadros.numeroRecuadroMapa[entidadIzqColumna][entidadAbajoFila];
                recuadro2= panelJuego.administradorRecuadros.numeroRecuadroMapa[entidadDerColumna][entidadAbajoFila];
                if (panelJuego.administradorRecuadros.recuadros[recuadro1].colision 
                        || panelJuego.administradorRecuadros.recuadros[recuadro2].colision)
                    entidad.colisionActivada=true;
                break;
            case "derecha":
                entidadDerColumna=(entidadDerMundoX+entidad.velocidad)/panelJuego.tamannoRecuadros;
                recuadro1=panelJuego.administradorRecuadros.numeroRecuadroMapa[entidadDerColumna][entidadArribaFila];
                recuadro2= panelJuego.administradorRecuadros.numeroRecuadroMapa[entidadDerColumna][entidadAbajoFila];
                if (panelJuego.administradorRecuadros.recuadros[recuadro1].colision 
                        || panelJuego.administradorRecuadros.recuadros[recuadro2].colision)
                    entidad.colisionActivada=true;
                break;
            case "izquierda":
                entidadIzqColumna=(entidadIzqMundoX-entidad.velocidad)/panelJuego.tamannoRecuadros;
                recuadro1=panelJuego.administradorRecuadros.numeroRecuadroMapa[entidadIzqColumna][entidadArribaFila];
                recuadro2= panelJuego.administradorRecuadros.numeroRecuadroMapa[entidadIzqColumna][entidadAbajoFila];
                if (panelJuego.administradorRecuadros.recuadros[recuadro1].colision 
                        || panelJuego.administradorRecuadros.recuadros[recuadro2].colision)
                    entidad.colisionActivada=true;
                break;
        }
        
    }
    
    
    public int VerificarObjeto (Entidad entidad, boolean jugador){
        int indice=999;
        
        for(int i=0; i < panelJuego.obj; i++){
            
            if(panelJuego.obj[i] != null){
                //Obtener la posicion del area solida de la entidad
                entidad.areaSolida.x=entidad.mundoX + entidad.areaSolida.x;
                entidad.areaSolida.y=entidad.mundoY + entidad.areaSolida.y;
                
                //Obtener la posicion del area solida del objeto
                panelJuego.obj[i].areaSolida.x= panelJuego.obj[i].mundoX + panelJuego.obj[i].areaSolida.x;
                panelJuego.obj[i].areaSolida.y= panelJuego.obj[i].mundoY + panelJuego.obj[i].areaSolida.y;
                
                switch(entidad.direccion){
                    case "arriba":
                        entidad.areaSolida.y -= entidad.velocidad;
                        if(entidad.areaSolida.intersects(panelJuego.obj[i].areaSolida)){
                            if(panelJuego.obj[i].colision)
                                entidad.colisionActivada=true;
                            if(jugador)
                                indice=i;
                        }
                        break;
                    case "abajo":
                        entidad.areaSolida.y += entidad.velocidad;
                        if(entidad.areaSolida.intersects(panelJuego.obj[i].areaSolida)){
                            if(panelJuego.obj[i].colision)
                                entidad.colisionActivada=true;
                            if(jugador)
                                indice=i;
                        }
                        break;
                    case "izquierda":
                        entidad.areaSolida.x -= entidad.velocidad;
                        if(entidad.areaSolida.intersects(panelJuego.obj[i].areaSolida)){
                            if(panelJuego.obj[i].colision)
                                entidad.colisionActivada=true;
                            if(jugador)
                                indice=i;
                        }
                        break;
                    case "derecha":
                        entidad.areaSolida.x += entidad.velocidad;
                        if(entidad.areaSolida.intersects(panelJuego.obj[i].areaSolida)){
                            if(panelJuego.obj[i].colision)
                                entidad.colisionActivada=true;
                            if(jugador)
                                indice=i;
                        }
                        break;
                }
                entidad.areaSolida.x=entidad.areaSolidaDefaultX;
                entidad.areaSolida.y=entidad.areaSolidaDefaultY;
                panelJuego.obj[i].areaSolida.x=panelJuego.obj[i].areaSolidaDefaultX;
                panelJuego.obj[i].areaSolida.y=panelJuego.obj[i].areaSolidaDefaultY;
            }
        }
        return indice;
    }
}
