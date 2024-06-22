package Jugador;
import EcoCrossing.net.ClienteJuego;
import EcoCrossing.net.ServidorJuego;
import EcoCrossing.net.paquetes.Paquete00Acceder;
import Entidades.*;
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
import java.awt.Font;
import javax.swing.JLabel;

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
    public final int maxColumnasMundo = 64;
    public final int maxFilasMundo = 32;
    //
    public final int anchoMundo = tamannoRecuadros * maxColumnasMundo; // 48x50 = 2400 pixeles
    public final int alturaMundo = tamannoRecuadros * maxFilasMundo; //  48x50 = 2400 pixeles
    
    //FPS
    int FPS = 60;
    
    //Atributos juego
    public AdministradorRecuadros administradorRecuadros = new AdministradorRecuadros(this);
    public ManejoTeclas manejoTeclas = new ManejoTeclas();
    Sonido musica= new Sonido();
    Sonido efectosSonido= new Sonido();
    public VerificarColision verificarC= new VerificarColision(this);
    public AssetSetter aSetter= new AssetSetter(this);
    public UI ui= new UI(this);
    Thread juegoThread;
    
    // Entidades y objetos:
    public Jugador jugador = new Jugador("",this, manejoTeclas);
    public SuperObjeto obj[]= new SuperObjeto[10];
    
    
    //Atributos multijugador
    public ArrayList<JugadorMultijugador> jugadoresConectados = new ArrayList<>();
    public ClienteJuego clienteSocket;
    public ServidorJuego servidorSocket;
    public JLabel ipLabel;
    private String direccionIP; 
    
    
    // Constructor
    public PanelJuego() { // Sin parametros
        this.setPreferredSize(new Dimension(anchoPantalla, largoPantalla));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(manejoTeclas);
        this.setFocusable(true);
        this.setLayout(null); // Usar posicionamiento absoluto

        // Inicializar la etiqueta para mostrar la IP
        ipLabel = new JLabel();
        ipLabel.setForeground(Color.WHITE);
        ipLabel.setFont(new Font("Arial", Font.BOLD, 14));
        ipLabel.setBounds(10, 10, 300, 20); // Posicionar ipLabel en la esquina superior izquierda
        this.add(ipLabel); // Añadir ipLabel directamente al PanelJuego
    }

    public void setIPText(String direccionIP) {
        this.direccionIP = direccionIP;
        ipLabel.setText(direccionIP); // Forzar la actualización del panel
    }

    //Otros metodos
    public void setupJuego(){
        aSetter.setObjeto();
        reproducirMusica(0); // Reproducimos el tema principal
    }
    
    //Evitar un ConcurrentModificationException (Una entidad sometida a lectura y escritura al mismo tiempo)
    public synchronized ArrayList<JugadorMultijugador> getJugadoresConectados(){
        return this.jugadoresConectados;
    }
    
    public void agregarJugador(JugadorMultijugador jugador){
        getJugadoresConectados().add(jugador);
        repaint();
    }
    
    public void eliminarJugador(String nombreUsuario){
        for(JugadorMultijugador j: this.getJugadoresConectados())
            if(j.getNombreUsuario().equals(nombreUsuario)){
                this.getJugadoresConectados().remove(j);
                break;
            }             
    }
    
    public void iniciarjuegoThread() {
        juegoThread = new Thread(this);
        juegoThread.start();

        String servidorIP = "";
        if (JOptionPane.showConfirmDialog(this, "Desea iniciar el servidor?") == 0) {
            servidorSocket = new ServidorJuego(this);
            servidorSocket.start();
            servidorIP = servidorSocket.obtenerIPLocal();
            JOptionPane.showMessageDialog(this, "Servidor iniciado. IP del servidor: " + servidorIP);
            setIPText("IP del servidor: " + servidorIP);  // Actualizar el texto con la IP del servidor
        } else {
            servidorIP = JOptionPane.showInputDialog(this, "Ingrese la IP del servidor:", "localhost");
            setIPText("IP del servidor: " + servidorIP);  // Actualizar el texto con la IP del servidor
        }

        jugador = new JugadorMultijugador(JOptionPane.showInputDialog(this, "Nombre de usuario: "), this, manejoTeclas, null, -1);
        agregarJugador((JugadorMultijugador) jugador);
        Paquete00Acceder accederPaquete = new Paquete00Acceder(jugador.getNombreUsuario(), jugador.mundoX, jugador.mundoY, jugador.direccion);

        if (servidorSocket != null) {
            servidorSocket.agregarConexion((JugadorMultijugador) jugador, accederPaquete);
        }

        clienteSocket = new ClienteJuego(this, servidorIP);
        clienteSocket.start();
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
        for(JugadorMultijugador j : getJugadoresConectados())
            j.actualizar();
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);  
        Graphics2D g2 = (Graphics2D) g;
        
        //Primero se dibuja el mapa, los objetos y luego el jugador
        administradorRecuadros.dibujar(g2);
        for (SuperObjeto obj1 : obj) 
            if (obj1 != null) 
                obj1.dibujar(g2, this);
        for(JugadorMultijugador j : getJugadoresConectados())
            j.dibujar(g2);  
        
        // Dibujar el ipLabel encima de todos los demás elementos
        ipLabel.paint(g2);
        
        //UI
        ui.dibujar(g2);
        
        //Dibujar en la pantalla
        g2.dispose();
    }  
    
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
    
    private int buscarIndiceJugadorMultijugador(String nombreUsuario){
        int indice = 0;
        for(JugadorMultijugador j : getJugadoresConectados()){
            if(j.getNombreUsuario().equals(nombreUsuario))
                break;
            indice++;
        }
        return indice;
    } 
    
    public void moverJugador(String nombreUsuario, int x, int y){
        int indice = buscarIndiceJugadorMultijugador(nombreUsuario);
        this.getJugadoresConectados().get(indice).mundoX = x;
        this.getJugadoresConectados().get(indice).mundoY = y;
    }
    
    
}
