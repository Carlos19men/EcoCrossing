package com.mycompany.ecocrossing;
import EcoCrossing.net.ClienteJuego;
import EcoCrossing.net.ServidorJuego;
import EcoCrossing.net.paquetes.Paquete00Acceder;
import Entidades.Jugador;
import Entidades.JugadorMultijugador;
import Objeto.SuperObjeto;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import Recuadros.AdministradorRecuadros;

public class PanelJuego extends JPanel implements Runnable{    
    //Atributos de la pantalla
    final int recuadros = 16; //16x16 recuadros
    final int escala = 3;
    public final int tamannoRecuadros = recuadros * escala; //48x48 recuadros
    public final int maxColumnas = 16;
    public final int maxFilas = 12;
    public final int anchoPantalla = tamannoRecuadros * maxColumnas; // 48x16 = 768 pixeles
    public final int largoPantalla = tamannoRecuadros * maxFilas; // 48x12 = 576 pixeles
    
    //Atributos y ajustes del mundo
    //***** Cambiar de acuerdo al tamanno exacto del mapa del archivo **************
    public final int maxColumnasMundo = 32;
    public final int maxFilasMundo = 32;
    //
    public final int anchoMundo = tamannoRecuadros * maxColumnasMundo; // 48x50 = 2400 pixeles
    public final int alturaMundo = tamannoRecuadros * maxFilasMundo; //  48x50 = 2400 pixeles
    
    //FPS
    int FPS = 60;
    
    //Atributos juego
    public AdministradorRecuadros administradorRecuadros = new AdministradorRecuadros(this);
    public ManejoTeclas manejoTeclas = new ManejoTeclas();
    Thread juegoThread;
    public VerificarColision verificarC= new VerificarColision(this);
    public AssetSetter aSetter= new AssetSetter(this);
    public Jugador jugador = new Jugador(this, manejoTeclas);
    public SuperObjeto obj[]= new SuperObjeto[10];
    
    //Atributos multijugador
    public ArrayList<JugadorMultijugador> jugadoresConectados = new ArrayList<>();
    private ClienteJuego clienteSocket;
    private ServidorJuego servidorSocket;
    
    //Constructor
    public PanelJuego(){ //Sin nparametros
        this.setPreferredSize(new Dimension(anchoPantalla, largoPantalla));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(manejoTeclas);
        this.setFocusable(true);
    }

    //Otros metodos
    public void setupJuego(){
        aSetter.setObjeto();
    }
    
    public void iniciarjuegoThread(){
        juegoThread = new Thread(this);
        juegoThread.start();
        
        if(JOptionPane.showConfirmDialog(this, "Desea iniciar el servidor?")==0){
            servidorSocket = new ServidorJuego(this);
            servidorSocket.start();
        }
        
        clienteSocket = new ClienteJuego(this, "localhost");
        clienteSocket.start();
        Paquete00Acceder accederPaquete = new Paquete00Acceder(JOptionPane.showInputDialog(this,"Nombre de usuario: "));
        accederPaquete.escribirDatos(clienteSocket);
    }
    
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
                //Actualiza la informacion
                actualizar();
                //Muestra la informacion actualizada en la pantalla
                repaint();
                delta--;
            }         
        }        
    }
    
    public void actualizar(){ 
        for(JugadorMultijugador j : jugadoresConectados)
            j.actualizar();
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);  
        Graphics2D g2 = (Graphics2D) g;
        //Primero se dibuja el mapa, los objetos y luego el jugador
        administradorRecuadros.dibujar(g2);
        for(int i=0; i<obj.length;i++)
            if (obj[i]!= null)
                obj[i].dibujar(g2, this);
        for(JugadorMultijugador j : jugadoresConectados)
            j.dibujar(g2);       
        //Dibujar en la pantalla
        g2.dispose();     
    }  
}
