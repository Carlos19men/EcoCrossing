package com.mycompany.ecocrossing;

import Entidades.Entidad;

public class VerificarColision {
    PanelJuego panelJuego;
    
   //Constructor 
    public VerificarColision(PanelJuego panelJuego){
            this.panelJuego=panelJuego;
    }
      
    //Método para verificar la colisión de una entidad con los recuadros del mapa.
    public void VerificarRecuadro (Entidad entidad){
        // Calcular posiciones de la entidad respecto al mundo y sus recuadros
        int entidadIzqMundoX= entidad.mundoX + entidad.areaSolida.x;
        int entidadDerMundoX= entidad.mundoX + entidad.areaSolida.x + entidad.areaSolida.width;
        int entidadArribaMundoY= entidad.mundoY + entidad.areaSolida.y;
        int entidadAbajoMundoY= entidad.mundoY + entidad.areaSolida.y + entidad.areaSolida.height;
        
        // Calcular los índices de las columnas y filas de los recuadros en el mapa
        int entidadIzqColumna= entidadIzqMundoX/panelJuego.tamannoRecuadros;
        int entidadDerColumna= entidadDerMundoX/ panelJuego.tamannoRecuadros;
        int entidadArribaFila= entidadArribaMundoY/panelJuego.tamannoRecuadros;
        int entidadAbajoFila= entidadAbajoMundoY/panelJuego.tamannoRecuadros;
        int recuadro1, recuadro2;
        
        // Determinar el tipo de colisión según la dirección de la entidad
        switch(entidad.direccion){
            case "arriba":
                entidadArribaFila=(entidadArribaMundoY-entidad.velocidad)/panelJuego.tamannoRecuadros;
                recuadro1=panelJuego.administradorRecuadros.numeroRecuadroMapa[entidadIzqColumna][entidadArribaFila];
                recuadro2= panelJuego.administradorRecuadros.numeroRecuadroMapa[entidadDerColumna][entidadArribaFila];
                // Verificar colisión con los recuadros correspondientes
                if (panelJuego.administradorRecuadros.recuadros[recuadro1].colision 
                        || panelJuego.administradorRecuadros.recuadros[recuadro2].colision)
                    entidad.colisionActivada=true;
                break;
            case "abajo":
                entidadAbajoFila=(entidadAbajoMundoY+entidad.velocidad)/panelJuego.tamannoRecuadros;
                recuadro1=panelJuego.administradorRecuadros.numeroRecuadroMapa[entidadIzqColumna][entidadAbajoFila];
                recuadro2= panelJuego.administradorRecuadros.numeroRecuadroMapa[entidadDerColumna][entidadAbajoFila];
                // Verificar colisión con los recuadros correspondientes
                if (panelJuego.administradorRecuadros.recuadros[recuadro1].colision 
                        || panelJuego.administradorRecuadros.recuadros[recuadro2].colision)
                    entidad.colisionActivada=true;
                break;
            case "derecha":
                entidadDerColumna=(entidadDerMundoX+entidad.velocidad)/panelJuego.tamannoRecuadros;
                recuadro1=panelJuego.administradorRecuadros.numeroRecuadroMapa[entidadDerColumna][entidadArribaFila];
                recuadro2= panelJuego.administradorRecuadros.numeroRecuadroMapa[entidadDerColumna][entidadAbajoFila];
                // Verificar colisión con los recuadros correspondientes
                if (panelJuego.administradorRecuadros.recuadros[recuadro1].colision 
                        || panelJuego.administradorRecuadros.recuadros[recuadro2].colision)
                    entidad.colisionActivada=true;
                break;
            case "izquierda":
                entidadIzqColumna=(entidadIzqMundoX-entidad.velocidad)/panelJuego.tamannoRecuadros;
                recuadro1=panelJuego.administradorRecuadros.numeroRecuadroMapa[entidadIzqColumna][entidadArribaFila];
                recuadro2= panelJuego.administradorRecuadros.numeroRecuadroMapa[entidadIzqColumna][entidadAbajoFila];
                // Verificar colisión con los recuadros correspondientes
                if (panelJuego.administradorRecuadros.recuadros[recuadro1].colision 
                        || panelJuego.administradorRecuadros.recuadros[recuadro2].colision)
                    entidad.colisionActivada=true;
                break;
        }
        
    }
    
    //Método para verificar la colisión de una entidad con otros objetos en el juego.
    public int VerificarObjeto (Entidad entidad, boolean jugador){
        int indice=999;
        
        // Recorrer todos los objetos en el juego
        for(int i=0; i<panelJuego.obj.length;i++){
            if(panelJuego.obj[i] != null){
                // Obtener la posición del área sólida de la entidad y del objeto
                entidad.areaSolida.x=entidad.mundoX + entidad.areaSolida.x;
                entidad.areaSolida.y=entidad.mundoY + entidad.areaSolida.y;
                panelJuego.obj[i].areaSolida.x= panelJuego.obj[i].mundoX + panelJuego.obj[i].areaSolida.x;
                panelJuego.obj[i].areaSolida.y= panelJuego.obj[i].mundoY + panelJuego.obj[i].areaSolida.y;
                
                // Verificar colisión según la dirección de la entidad
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
                // Restaurar las posiciones originales del área sólida de la entidad y del objeto
                entidad.areaSolida.x=entidad.areaSolidaDefaultX;
                entidad.areaSolida.y=entidad.areaSolidaDefaultY;
                panelJuego.obj[i].areaSolida.x=panelJuego.obj[i].areaSolidaDefaultX;
                panelJuego.obj[i].areaSolida.y=panelJuego.obj[i].areaSolidaDefaultY;
            }
        }
        return indice;
    }
    
    public int VerificarEntidad(Entidad entidad, Entidad[] entidades){
    int indice=999;
        // Recorrer todos los objetos en el juego
        for(int i=0; i<entidades.length;i++){
            if(entidades[i] != null){
                entidad.areaSolida.x=entidad.mundoX + entidad.areaSolida.x;
                entidad.areaSolida.y=entidad.mundoY + entidad.areaSolida.y;
                entidades[i].areaSolida.x= entidades[i].mundoX + entidades[i].areaSolida.x;
                entidades[i].areaSolida.y= entidades[i].mundoY + entidades[i].areaSolida.y;
                
                switch(entidad.direccion){
                    case "arriba":
                        entidad.areaSolida.y -= entidad.velocidad;
                        if(entidad.areaSolida.intersects(entidades[i].areaSolida)){
                            entidad.colisionActivada=true;
                            indice=i;
                        }
                        break;
                    case "abajo":
                        entidad.areaSolida.y += entidad.velocidad;
                        if(entidad.areaSolida.intersects(entidades[i].areaSolida)){
                            entidad.colisionActivada=true;
                            indice=i;
                        }
                        break;
                    case "izquierda":
                        entidad.areaSolida.x -= entidad.velocidad;
                        if(entidad.areaSolida.intersects(entidades[i].areaSolida)){
                            entidad.colisionActivada=true;
                            indice=i;
                        }
                        break;
                    case "derecha":
                        entidad.areaSolida.x += entidad.velocidad;
                        if(entidad.areaSolida.intersects(entidades[i].areaSolida)){
                            entidad.colisionActivada=true;
                            indice=i;
                        }
                        break;
                }
                entidad.areaSolida.x=entidad.areaSolidaDefaultX;
                entidad.areaSolida.y=entidad.areaSolidaDefaultY;
                entidades[i].areaSolida.x=entidades[i].areaSolidaDefaultX;
                entidades[i].areaSolida.y=entidades[i].areaSolidaDefaultY;
            }
        }
        return indice;
    }
    
    public void VerificarJugador(Entidad entidad){
        entidad.areaSolida.x=entidad.mundoX + entidad.areaSolida.x;
        entidad.areaSolida.y=entidad.mundoY + entidad.areaSolida.y;
        panelJuego.jugador.areaSolida.x= panelJuego.jugador.mundoX + panelJuego.jugador.areaSolida.x;
        panelJuego.jugador.areaSolida.y= panelJuego.jugador.mundoY + panelJuego.jugador.areaSolida.y;
                
        switch(entidad.direccion){
                    case "arriba":
                        entidad.areaSolida.y -= entidad.velocidad;
                        if(entidad.areaSolida.intersects(panelJuego.jugador.areaSolida))
                            entidad.colisionActivada=true;
                        break;
                    case "abajo":
                        entidad.areaSolida.y += entidad.velocidad;
                        if(entidad.areaSolida.intersects(panelJuego.jugador.areaSolida))
                            entidad.colisionActivada=true;
                        break;
                    case "izquierda":
                        entidad.areaSolida.x -= entidad.velocidad;
                        if(entidad.areaSolida.intersects(panelJuego.jugador.areaSolida))
                            entidad.colisionActivada=true;
                        break;
                    case "derecha":
                        entidad.areaSolida.x += entidad.velocidad;
                        if(entidad.areaSolida.intersects(panelJuego.jugador.areaSolida))
                            entidad.colisionActivada=true;
                        break;
                }
                entidad.areaSolida.x=entidad.areaSolidaDefaultX;
                entidad.areaSolida.y=entidad.areaSolidaDefaultY;
                panelJuego.jugador.areaSolida.x=panelJuego.jugador.areaSolidaDefaultX;
                panelJuego.jugador.areaSolida.y=panelJuego.jugador.areaSolidaDefaultY;
    }
}