package com.mycompany.ecocrossing;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ManejoTeclas implements KeyListener{
    public boolean arribaPresionado, abajoPresionado, izquierdaPresionado, derechaPresionado;

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        //Obtener el codigo de la tecla presionada
        int codigo = e.getKeyCode();       
        
        //Verificar a que direccion se refiere
        if(codigo == KeyEvent.VK_W || codigo == KeyEvent.VK_UP)
            arribaPresionado = true;
        if(codigo == KeyEvent.VK_A || codigo == KeyEvent.VK_LEFT)
            izquierdaPresionado = true;
        if(codigo == KeyEvent.VK_S || codigo == KeyEvent.VK_DOWN)
            abajoPresionado = true;
        if(codigo == KeyEvent.VK_D || codigo == KeyEvent.VK_RIGHT)
            derechaPresionado = true;
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
    }
    
}
