
package Multijugador_2_Servidores;

import javax.swing.JPanel; 
import java.awt.Dimension; 
import java.awt.Color; 
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable{
    
    private Jugador jugador; 
    int x2, y2; 
    // set/get 

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }
    
    
    //sreen settings (Estructura del panel) 
    final int originalTitleSize = 16; // 16x16 titulo 
    final int scale = 3; // escala 
    
    public final int titleSize = originalTitleSize * scale; 
    
    final int maxScreenCol = 16; // cuadrados que forman la pantalla 
    final int maxScreenRow = 12; 
    final int screenWidth = titleSize * maxScreenCol; // 768 pixeles 
    final int screenHeight = titleSize * maxScreenRow; // 576 pixeles 
    


    Thread gameThread;   // Es un hilo, algo que se puede inicar y detener 
    
    //FPS
    int FPS = 60; 

       
    
    //constructor 
    public GamePanel(Jugador jugador){
        this.jugador = jugador; 
        this.setPreferredSize(new Dimension(screenWidth,screenHeight)); 
        this.setBackground(Color.DARK_GRAY);
        this.setDoubleBuffered(true); // dibuja primero las imagenes en el buffer para luego mostrarlas en pantalla
        this.addKeyListener(jugador.getKeyH());  // Escuchador de teclado (en un objeto que esta atento a todos los eventos del teclado) 
        this.setFocusable(true);
    }
    
    public void starGameThread(){
        gameThread = new Thread(this); // this para referenciarlo así mismo 
        gameThread.start(); // este metodo llama al metodo run 
    }

    @Override
    public void run() {
        // este metodo es para iniciar la vida del hilo 
        //metodo sleep para establecer juegos 
        double drawInterval = 1000000000/FPS; // 0.16666 segundos 
        double delta = 0; 
        long lastTime = System.nanoTime(); 
        long currentTime; 
        long timer = 0; 
        int drawCount = 0; 
        
        System.out.println("entre");
        
        //bucle de juego 
        while(gameThread != null){
            //tenemos que establecer cuantas pantallas tienen que mostrarse por segundo 60fps
            currentTime = System.nanoTime(); 
            delta += (currentTime - lastTime) / drawInterval; 
            timer += (currentTime - lastTime); 
            lastTime = currentTime; 
            
            // pasos a seguir 
            // 1 UPDATE: actualizar la información de la salida de la pantalla 
     
            
            
            // 2 DRAW: dibujar la pantalla con la información actualizada  
             
      
            if(delta >= 1){
                update(); 
                repaint();
                delta--; 
                drawCount++; 
            }
            
            if(timer >= 1000000000){
                System.out.println("FPS: "+ drawCount);
                drawCount = 0; 
                timer = 0; 
            }
            
            
            //Enviamos las coordenas al servidor 
            
               
        }
    }
    
    public void update(){
        jugador.update();
    }
  
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g; // estas dos clases son similares pero graphis2D tiene mas funciones para dibujar 
  
        jugador.draw(g2);

        //g2.dispose();
        
        //dibujamos el segudo
        jugador.draw(g2,jugador.getX2(),jugador.getY2(),Color.GREEN);
        g2.dispose();
    } 
    
}
