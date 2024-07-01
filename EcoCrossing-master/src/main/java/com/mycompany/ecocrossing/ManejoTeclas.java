package com.mycompany.ecocrossing;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ManejoTeclas implements KeyListener{
    public boolean arribaPresionado, abajoPresionado, izquierdaPresionado, derechaPresionado, recogerObjetoPresionado;
    PanelJuego panelJuego;

    // Depuración:
    boolean tiempo=false;
    
    public ManejoTeclas(PanelJuego panelJuego) {
        this.panelJuego= panelJuego;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        //Obtener el codigo de la tecla presionada
        int codigo = e.getKeyCode();       
        
        // Selecionar opción en el menú:
        if(panelJuego.estadoJuego==panelJuego.estadoTitulo) {
            if(panelJuego.ui.estadoPantallaTitulo == 0) {
                if(codigo == KeyEvent.VK_W || codigo == KeyEvent.VK_UP) {
                    panelJuego.ui.comandoNumerico--;
                    if(panelJuego.ui.comandoNumerico<0) {
                        panelJuego.ui.comandoNumerico=2;
                    }
                }
                else if(codigo == KeyEvent.VK_S || codigo == KeyEvent.VK_DOWN) {
                    panelJuego.ui.comandoNumerico++;
                    if(panelJuego.ui.comandoNumerico>2) {
                        panelJuego.ui.comandoNumerico=0; 
                    }
                }
                else if(codigo == KeyEvent.VK_ENTER) {
                    if(panelJuego.ui.comandoNumerico == 0) {
                        panelJuego.ui.estadoPantallaTitulo=1;
                    }
                    else if(panelJuego.ui.comandoNumerico == 1) {
                        // Añadir Después:
                    }
                    else if(panelJuego.ui.comandoNumerico==2) {
                        System.exit(0);
                    }
                }
            }
            else if(panelJuego.ui.estadoPantallaTitulo == 1) {
                if(codigo == KeyEvent.VK_W || codigo == KeyEvent.VK_UP) {
                    panelJuego.ui.comandoNumerico--;
                    if(panelJuego.ui.comandoNumerico<0) {
                        panelJuego.ui.comandoNumerico=3;
                    }
                }
                else if(codigo == KeyEvent.VK_S || codigo == KeyEvent.VK_DOWN) {
                    panelJuego.ui.comandoNumerico++;
                    if(panelJuego.ui.comandoNumerico>3) {
                        panelJuego.ui.comandoNumerico=0; 
                    }
                }
                else if(codigo == KeyEvent.VK_ENTER) {
                    if(panelJuego.ui.comandoNumerico == 0) {
                        // Implementar lógica para cargar una skin en específico, y luego arrancar el juego con ella...
                        panelJuego.estadoJuego=panelJuego.estadoJugando;
                        panelJuego.iniciarjuegoThread();
                        panelJuego.reproducirMusica(0);
                    }
                    else if(panelJuego.ui.comandoNumerico == 1) {
                        // Implementar lógica para cargar una skin en específico, y luego arrancar el juego con ella...
                        panelJuego.estadoJuego=panelJuego.estadoJugando;
                        panelJuego.iniciarjuegoThread();
                        panelJuego.reproducirMusica(0);
                    }
                    else if(panelJuego.ui.comandoNumerico==2) {
                        // Implementar lógica para cargar una skin en específico, y luego arrancar el juego con ella...
                        panelJuego.estadoJuego=panelJuego.estadoJugando;
                        panelJuego.iniciarjuegoThread();
                        panelJuego.reproducirMusica(0);
                    }
                    else if(panelJuego.ui.comandoNumerico==3) {
                        // En este caso volvemos al menú principal...
                        panelJuego.ui.estadoPantallaTitulo=0;
                    }
                }
            }
        }
        
        //Verificar a que direccion se refiere
        if(codigo == KeyEvent.VK_W || codigo == KeyEvent.VK_UP)
            arribaPresionado = true;
        if(codigo == KeyEvent.VK_A || codigo == KeyEvent.VK_LEFT)
            izquierdaPresionado = true;
        if(codigo == KeyEvent.VK_S || codigo == KeyEvent.VK_DOWN)
            abajoPresionado = true;
        if(codigo == KeyEvent.VK_D || codigo == KeyEvent.VK_RIGHT)
            derechaPresionado = true;
        if(codigo == KeyEvent.VK_P || codigo == KeyEvent.VK_ESCAPE)
            if(panelJuego.estadoJuego==panelJuego.estadoJugando) {
                panelJuego.estadoJuego= panelJuego.estadoPausa;
                panelJuego.musica.parar();
            }
            else if(panelJuego.estadoJuego==panelJuego.estadoPausa) {
                panelJuego.estadoJuego= panelJuego.estadoJugando;
                panelJuego.musica.reproducir();
            }
        //Verificar teclas especiales
        if(codigo == KeyEvent.VK_R)
            recogerObjetoPresionado = true;
        
        //Verificar depuración:
        if(codigo == KeyEvent.VK_T) {
            if(!tiempo) {
                tiempo=true;
            }
            else {
                tiempo=false;
            }
        }
            
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //Obtener el codigo de la tecla que se solto
        int codigo = e.getKeyCode();  
        
        //Verificar a que direccion se refiere
        if(codigo == KeyEvent.VK_W || codigo == KeyEvent.VK_UP)
            arribaPresionado = false;
        if(codigo == KeyEvent.VK_A || codigo == KeyEvent.VK_LEFT)
            izquierdaPresionado = false;
        if(codigo == KeyEvent.VK_S || codigo == KeyEvent.VK_DOWN)
            abajoPresionado = false;
        if(codigo == KeyEvent.VK_D || codigo == KeyEvent.VK_RIGHT)
            derechaPresionado = false;
        
        //Verificar teclas especiales
        if(codigo == KeyEvent.VK_R)
            recogerObjetoPresionado = false;
    }
    
}
