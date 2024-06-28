package PanelJuego;
import PanelJuego.HUD;
import PanelJuego.AdministradorRecuadros;
import PanelJuego.VerificarColision;
import PanelJuego.ColocadorObjeto;
import PanelJuego.Sonido;
import Jugador.Jugador;
import Jugador.ManejadorTeclado;
import Multijugador.Servidor;
import Objetos.ColocadorDeObjetos;
import Objetos.SuperObjeto;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class PanelJuego extends JPanel implements Runnable{    
 //Contantes para las pantallas 
    final int recuadros = 16; //16x16 recuadros
    final int escala = 3;
    public final int tamannoRecuadros = recuadros * escala; //48x48 recuadros
    public final int maxColumnas = 16;
    public final int maxFilas = 12;
    public final int anchoPantalla = tamannoRecuadros * maxColumnas; // 48x16 = 768 pixeles
    public final int largoPantalla = tamannoRecuadros * maxFilas; // 48x12 = 576 pixeles
    public final int maxColumnasMundo = 64;
    public final int maxFilasMundo = 32;
    public final int anchoMundo = tamannoRecuadros * maxColumnasMundo; // 48x50 = 2400 pixeles
    public final int alturaMundo = tamannoRecuadros * maxFilasMundo; //  48x50 = 2400 pixeles
    
    //FPS
    int FPS = 60;
    
    //Atributos juego
    public Jugador jugador; 
    public AdministradorRecuadros administradorRecuadros;
    Sonido musica;
    Sonido efectosSonido;
    public VerificarColision verificarC;
    public ColocadorObjeto colocadorObjeto;
    public HUD hud;
    Thread juegoThread;
    
    // Entidades y objetos:
    public SuperObjeto[] obj;   
    
    //Atributos multijugador
    public JLabel ipLabel;
    
    
    // Constructor: Jugador administrador
    public PanelJuego(){
    }

    // set / get


    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }
    
    public void agregarTeclado(ManejadorTeclado teclado){
        this.addKeyListener(teclado);
    }

    public void inicializarValores() {
        //Aspecto de la pantalla
        this.setPreferredSize(new Dimension(anchoPantalla, largoPantalla));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.setLayout(null); 
        
        // Inicializar la etiqueta para mostrar la IP
        ipLabel = new JLabel();
        ipLabel.setForeground(Color.WHITE);
        ipLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // Posicionar ipLabel en la esquina superior izquierda
        ipLabel.setBounds(10, 10, 300, 20); 
        
        // Añadir ipLabel directamente al PanelJuego
        this.add(ipLabel);     
        
        // Entidades y objetos:
        obj = new SuperObjeto[10];
        
        
        //Atributos juego
        administradorRecuadros = new AdministradorRecuadros(this);
        hud = new HUD(this);
        musica = new Sonido();
        efectosSonido = new Sonido();
        verificarC = new VerificarColision(this);
        colocadorObjeto = new ColocadorObjeto(this);
    }
    
    
    public void iniciarjuegoThread() {
        //crear frame 
        JFrame ventana = new JFrame(); 
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false); 
        ventana.setTitle("Eco corssing: ");
        
        ventana.add(this); 
        ventana.pack();
        
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
        
        
        juegoThread = new Thread(this);
        juegoThread.start();       
    }
    
    //Iniciar juego 
    
    @Override
    public void run() {     
        // 1 segundo en nanosegundos (1*10^9)
        double intervaloDibujado = 1000000000/FPS; // Resultado = 0.01666 segundos
        double delta = 0;
        //Tiempo que toma en ejecutar un bloque especifico
        long ultimoTiempo = System.nanoTime();
        long tiempoActual; 
        
        
        while (juegoThread != null){          
            tiempoActual = System.nanoTime();
            delta += (tiempoActual - ultimoTiempo) / intervaloDibujado;
            ultimoTiempo = tiempoActual;    
           
            if(delta>=1){
                actualizar();
                repaint();  
                delta--;
            }         
        }        
    }
    
    public void actualizar(){
        jugador.actualizar();
    }
    
    
    //metodos para repintar la pantalla
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);  
        Graphics2D g2 = (Graphics2D) g;
        
        //Primero se dibuja el mapa, los objetos y luego el jugador
        administradorRecuadros.dibujar(g2,jugador);
        for (SuperObjeto obj1 : obj) 
            if (obj1 != null) 
                obj1.dibujar(g2, this, jugador);
        
        //Dibujar a los jugadores 

       
        
        // Dibujar el ipLabel encima de todos los demás elementos
        ipLabel.paint(g2);
        
        //UI
        hud.dibujar(g2,jugador);
        
        //Dibujar en la pantalla
        g2.dispose();
    }  
    
   
    //efectos de sonido 
     public void reproducirMusica(int i) {
        musica.colocarArchivo(i);
        musica.reproducir();
        musica.bucle();
    }
    
    public void pararMusica() {
        musica.parar();
    }
    
    public void reproducirEfectosSonido(int i) {
        efectosSonido.colocarArchivo(i);
        efectosSonido.reproducir();
        
    }
    
    
}
    //despues veo que hace eso 

   /*
    public void setIPText(String direccionIP) {
        this.direccionIP = direccionIP;
        ipLabel.setText(direccionIP); // Forzar la actualización del panel
    }

    //Otros metodos
    public void setupJuego(){
        aSetter.setObjeto();
        reproducirMusica(0); // Reproducimos el tema principal
    }
    */
