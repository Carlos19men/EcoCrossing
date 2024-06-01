
package Multijugador;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyHandler implements KeyListener  {

    public boolean upPressed, downPressed, leftPressed, rightPressed; 
    
    @Override
    public void keyTyped(KeyEvent e) {  
    }

    //evento para tecla precionada 
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); //Obtenemos el codigo de la tecla precionada 
        
        if(code == KeyEvent.VK_W){
            upPressed = true; 
        }
        
        if(code == KeyEvent.VK_S){
            downPressed = true; 
        }
        
        if(code == KeyEvent.VK_A){
            leftPressed = true; 
        }
        
        if(code == KeyEvent.VK_D){
            rightPressed = true; 
        }
    }

    
    //evento para tecla despues de precionarla
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode(); //Obtenemos el codigo de la tecla precionada 
        
        if(code == KeyEvent.VK_W){
            upPressed = false; 
        }
        
        if(code == KeyEvent.VK_S){
            downPressed = false; 
        }
        
        if(code == KeyEvent.VK_A){
            leftPressed = false; 
        }
        
        if(code == KeyEvent.VK_D){
            rightPressed = false; 
        }
    }
    
}
