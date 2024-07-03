package com.mycompany.ecocrossing;

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

public class PanelJuego extends JPanel implements Runnable {

    // Atributos de la pantalla
    final int recuadros = 16; // Tamaño de los recuadros del juego
    final int escala = 3; // Escala de los recuadros
    public final int tamannoRecuadros = recuadros * escala; // Tamaño total de los recuadros
    public final int maxColumnas = 16; // Número máximo de columnas en la pantalla
    public final int maxFilas = 12; // Número máximo de filas en la pantalla
    public final int anchoPantalla = tamannoRecuadros * maxColumnas; // Ancho total de la pantalla
    public final int largoPantalla = tamannoRecuadros * maxFilas; // Alto total de la pantalla

    // Atributos y ajustes del mundo del juego
    // ***** Ajustar según el tamaño exacto del mapa del juego *****
    public final int maxColumnasMundo = 86; // Número máximo de columnas del mundo del juego
    public final int maxFilasMundo = 80; // Número máximo de filas del mundo del juego
    public final int anchoMundo = tamannoRecuadros * maxColumnasMundo; // Ancho total del mundo del juego
    public final int alturaMundo = tamannoRecuadros * maxFilasMundo; // Alto total del mundo del juego

    // FPS
    int FPS = 60;

    // Atributos del juego
    public AdministradorRecuadros administradorRecuadros = new AdministradorRecuadros(this);
    public ManejoTeclas manejoTeclas = new ManejoTeclas(this);
    Sonido musica = new Sonido(); // Reproductor de música
    Sonido efectosSonido = new Sonido(); // Reproductor de efectos de sonido
    public VerificarColision verificarC = new VerificarColision(this); // Verificador de colisiones
    public AssetSetter aSetter = new AssetSetter(this); // Configurador de assets
    public UI ui = new UI(this); // Interfaz de usuario del juego
    Thread juegoThread; // Hilo principal del juego
    
    
    // Entidades y objetos del juego
    public Jugador jugador = new Jugador("", this, manejoTeclas); // Jugador principal
    public SuperObjeto obj[] = new SuperObjeto[50]; // Arreglo de objetos del juego
    public Entidad npc[]= new Entidad[5]; //Arreglo npc's del juego

    // Estado del juego
    public int estadoJuego;
    public final int estadoTitulo = 0;
    public final int estadoJugando = 1;
    public final int estadoPausa = 2;
    public final int estadoDialogo= 3;
    // public final int estadoDialogo=3;

    // Atributos multijugador
    public ArrayList<JugadorMultijugador> jugadoresConectados = new ArrayList<>();
    public ClienteJuego clienteSocket;
    public ServidorJuego servidorSocket;
    public JLabel ipLabel;
    
    // Constructor de PanelJuego.
    public PanelJuego() {
        this.setPreferredSize(new Dimension(anchoPantalla, largoPantalla));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(manejoTeclas); // Agregar el manejador de teclas al panel
        this.setFocusable(true);
        this.setLayout(null);

        // Inicializar la etiqueta para mostrar la IP
        ipLabel = new JLabel();
        ipLabel.setForeground(Color.WHITE);
        ipLabel.setFont(new Font("Arial", Font.BOLD, 14));
        ipLabel.setBounds(10, 10, 300, 20); // Posicionar ipLabel en la esquina superior izquierda
        this.add(ipLabel); // Añadir ipLabel al PanelJuego
    }

    // Obtiene de manera segura la lista de jugadores conectados. (Evitar un ConcurrentModificationException)
    public synchronized ArrayList<JugadorMultijugador> getJugadoresConectados() {
        return this.jugadoresConectados;
    }

    
     // Establece el texto de la etiqueta de IP.
    public void setIPText(String direccionIP) {
        ipLabel.setText(direccionIP); // Forzar la actualización del panel
    }

    // Configura y prepara el juego para comenzar.
    public void setupJuego() {
        aSetter.setObjeto();
        aSetter.setNPC();
        // reproducirMusica(0); // Reproducir el tema principal
        estadoJuego = estadoTitulo;
    }

    
    // Agrega un jugador a la lista de jugadores conectados.
    public void agregarJugador(JugadorMultijugador jugador) {
        getJugadoresConectados().add(jugador);
        repaint(); // Actualizar el panel
    }

    // Elimina un jugador de la lista de jugadores conectados.
    public void eliminarJugador(String nombreUsuario) {
        for (JugadorMultijugador j : this.getJugadoresConectados()) {
            if (j.getNombreUsuario().equals(nombreUsuario)) {
                this.getJugadoresConectados().remove(j);
                break;
            }
        }
    }

    // Maneja la eliminación de un objeto del juego.
    public void eliminarObjeto(int indice) {
        obj[indice] = null;
        repaint();
    }
    
    // Antes de ejecutar iniciarjuegoThread()...
    public void preInicioJuego() {
        juegoThread = new Thread(this);
        juegoThread.start();
    }
    
    // Inicia el hilo principal del juego y maneja la conexión del servidor y el cliente.
    public void iniciarjuegoThread() {
        String servidorIP = "";
        if (JOptionPane.showConfirmDialog(this, "Desea iniciar el servidor?") == 0) {
            // Iniciar servidor de juego
            servidorSocket = new ServidorJuego(this);
            servidorSocket.start();
            servidorIP = servidorSocket.obtenerIPLocal();
            JOptionPane.showMessageDialog(this, "Servidor iniciado. IP del servidor: " + servidorIP);
            setIPText("IP del servidor: " + servidorIP); // Actualizar el texto con la IP del servidor
        } else {
            // Conectar a un servidor existente
            servidorIP = JOptionPane.showInputDialog(this, "Ingrese la IP del servidor:", "localhost");
            setIPText("IP del servidor: " + servidorIP); // Actualizar el texto con la IP del servidor
        }

        // Crear jugador multijugador y agregarlo
        jugador = new JugadorMultijugador(JOptionPane.showInputDialog(this, "Nombre de usuario: "), this, manejoTeclas, null, -1);
        agregarJugador((JugadorMultijugador) jugador);
        Paquete00Acceder accederPaquete = new Paquete00Acceder(jugador.getNombreUsuario(), jugador.mundoX, jugador.mundoY, jugador.direccion);

        // Agregar conexión al servidor si está disponible
        if (servidorSocket != null) {
            servidorSocket.agregarConexion((JugadorMultijugador) jugador, accederPaquete);
        }

        // Iniciar cliente para conectar al servidor
        clienteSocket = new ClienteJuego(this, servidorIP);
        clienteSocket.start();
        accederPaquete.escribirDatos(clienteSocket);
    }

 
    // Maneja la actualización del estado del juego y el panel
    @Override
    public void run() {
        // Intervalo de tiempo para el dibujado
        double intervaloDibujado = 1000000000 / FPS;
        double delta = 0;
        long ultimoTiempo = System.nanoTime();
        long tiempoActual;

        while (juegoThread != null) {
            tiempoActual = System.nanoTime();
            delta += (tiempoActual - ultimoTiempo) / intervaloDibujado;
            ultimoTiempo = tiempoActual;

            if (delta >= 1) {
                // Actualizar la información del juego
                actualizar();
                // Redibujar la pantalla
                repaint();
                delta--;
            }
        }
    }
    
    // Actualizar el estado de los jugadores y NPCs según el estado del juego
    public void actualizar() {
        if (estadoJuego == estadoJugando) {
            // Actualizar jugadores conectados
            for (JugadorMultijugador jugador : getJugadoresConectados()) 
                jugador.actualizar();

            // Actualizar NPCs
            for (Entidad npc1 : npc) 
                if (npc1 != null) 
                    npc1.Actualizar(); // Asumiendo que 'Actualizar' está correctamente implementado en 'Entidad'
        } else if (estadoJuego == estadoPausa) {
            // No hacer nada si el juego está en pausa
        }

        repaint(); // Actualizar el panel después de procesar las actualizaciones
    }


    // Dibuja los elementos del juego en el panel
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        // Titulo del Juego:
        if(estadoJuego==estadoTitulo) {
            ui.dibujar(g2);
        }
        // Otros: (Ojo estar pendiente con lo que va a ir aquí)
        else {
            // Dibujar el mapa, objetos y npc's del juego
            administradorRecuadros.dibujar(g2);
        
            for (SuperObjeto obj1 : obj) 
                if (obj1 != null) 
                    obj1.dibujar(g2, this);
            
            for (Entidad npc1 : npc) 
                if (npc1 != null) 
                    npc1.dibujar(g2);

            // Dibujar jugadores conectados
            for (JugadorMultijugador j : getJugadoresConectados()) 
                j.dibujar(g2);

            // Mostrar la etiqueta de IP en la pantalla
            ipLabel.paint(g2);

            // Dibujar la interfaz de usuario
            ui.dibujar(g2);
        }
        
        

        g2.dispose();
    }

    // Métodos relacionados a la música
   
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

    // Busca el índice de un jugador multijugador por su nombre de usuario
    private int buscarIndiceJugadorMultijugador(String nombreUsuario) {
        int indice = 0;
        for (JugadorMultijugador j : getJugadoresConectados()) {
            if (j.getNombreUsuario().equals(nombreUsuario))
                break;
            indice++;
        }
        return indice;
    }
    
    // Método para buscar un NPC por su nombre
    public Entidad buscarNPC(String nombre) {
        for (Entidad npc1 : npc) 
            if (npc1 != null && npc1.getClass().getName().equals(nombre)) 
                return npc1;
        return null; 
    }

    // Mueve un jugador multijugador a las coordenadas especificadas
    public void moverJugador(String nombreUsuario, int x, int y) {
        int indice = buscarIndiceJugadorMultijugador(nombreUsuario);
        this.getJugadoresConectados().get(indice).mundoX = x;
        this.getJugadoresConectados().get(indice).mundoY = y;
    }
}
