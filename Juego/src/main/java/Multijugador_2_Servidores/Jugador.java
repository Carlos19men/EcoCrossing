
package Multijugador_2_Servidores;

import java.awt.Color;
import java.awt.Graphics2D;
import static java.lang.Integer.parseInt;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;


public class Jugador extends Entity implements Observer{
    
    private String nombre,host; 
    private GamePanel gp; 
    private KeyHandler keyH; 
    private Color color; 
    private String mensaje = ""; 
    private int puertoServer;
    private Servidor server; 
    private int x2 = 0,y2 = 0; 
    
    
    

    public Jugador( String nombre, Color color, int puertoServer,String host) {
        this.nombre = nombre; 
        this.host = host; 
        this.color = color; 
        this.keyH = new KeyHandler(); 
        this.puertoServer = puertoServer; 
        valoresPorDefecto(); 
    }
    
    //set / get

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }
    
    
    
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public KeyHandler getKeyH() {
        return keyH;
    }

    public void setKeyH(KeyHandler keyH) {
        this.keyH = keyH;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Servidor getServer() {
        return server;
    }

    public void setServer(Servidor server) {
        this.server = server;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    
    
    public void valoresPorDefecto(){
        x = 100; 
        y = 100; 
        speed = 6; 
    }
    
    public void update(){
        //actualizamos la posición del objeto 
        if(keyH.upPressed){
            y -= speed;  //arriba 
        }
        if(keyH.downPressed){
            y += speed;  // abajo 
        }
        if(keyH.leftPressed){
            x-= speed;  // izquierda 
        }
        if(keyH.rightPressed){
            x += speed;  // derecha  
        }
        
        
        
        //En este momento envío la información al servidor 
        String cadena = x+"/"+y; 
        Cliente cliente = new Cliente(puertoServer,cadena,host);
        
        Thread t = new Thread(cliente); 
        t.start(); 
        
    }
    
    public void draw(Graphics2D g2){
        g2.setColor(color);
        g2.fillRect(x, y,gp.titleSize,gp.titleSize);
        
    }
    
    public void draw(Graphics2D g2,int x,int y, Color color){
        g2.setColor(color);
        g2.fillRect(x, y,gp.titleSize,gp.titleSize);
        
    }
    
    public void asignarPuerto(int puerto){
        this.server = new Servidor(puerto); 
        server.addObserver((Observer) this); //agregamos la clase from al servidor 
        Thread t = new Thread(server); 
        t.start();
    }
    
    public void coordenadas(){
        System.out.println(coordenadasDate());
    }
    
    public String coordenadasDate(){
        return x+"/"+y; 
    }
    
    
    public void Conectar(){
        Servidor s = new Servidor(puertoServer); 
        s.addObserver(this);
        System.out.println("Conectados");
        Thread t = new Thread(s); 
        t.start();
    }
    
    public void jugar(){
        JFrame window = new JFrame(); 
        window = new JFrame(); 
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false); // Redimencionar: False 
        window.setTitle("2D prototipo 1");
        
        gp = new GamePanel(this); 
        
        window.add(gp); 
        
        window.pack(); 
        
        window.setLocationRelativeTo(null); //  centro de la pantalla
        window.setVisible(true); 
       
        gp.starGameThread();
    }

    @Override
    public void update(Observable o, Object arg) {
        //recibo la información del otro servidor 
        
       mensaje = (String) arg; 
       
       String cadenas[] = mensaje.split("/"); 
       
       x2 = parseInt(cadenas[0]); 
       y2 = parseInt(cadenas[1]);
    }
    
}
