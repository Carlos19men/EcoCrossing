package Entidades;

import EcoCrossing.net.paquetes.Paquete02Mover;
import EcoCrossing.net.paquetes.Paquete03EliminarObjeto;
import com.mycompany.ecocrossing.ManejoTeclas;
import com.mycompany.ecocrossing.PanelJuego;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Jugador extends Entidad {
    ManejoTeclas manejoTeclas;
    public final int pantallaX, pantallaY;
    public int cntBasura=0;
    private final String nombreUsuario;
    private String direccionAnterior;

   public Jugador(String nombreUsuario, PanelJuego panelJuego, ManejoTeclas manejoTeclas) {
       super(panelJuego);
        this.manejoTeclas = manejoTeclas;
        this.nombreUsuario = nombreUsuario;
        this.pantallaX = panelJuego.anchoPantalla / 2 - (panelJuego.tamannoRecuadros / 2);
        this.pantallaY = panelJuego.largoPantalla / 2 - (panelJuego.tamannoRecuadros / 2);
        areaSolida = new Rectangle();
        areaSolida.x=8;
        areaSolida.y=16;
        areaSolidaDefaultX=areaSolida.x;
        areaSolidaDefaultY=areaSolida.y;
        areaSolida.width=32;
        areaSolida.height=32;
        setValoresporDefecto();
        ObtenerImagen();
        // Inicializar la dirección anterior con la misma dirección inicial
        direccionAnterior = direccion;
    }

    public void setValoresporDefecto() {
        mundoX = panelJuego.tamannoRecuadros * 50;
        mundoY = panelJuego.tamannoRecuadros * 30;
        velocidad = 4;
        direccion = "frente";      
    }
    
    public void ObtenerImagen() {
        espalda= configurar("/Jugador/samuel_espalda");
        arriba1= configurar("/Jugador/samuel_espalda_caminando1");
        arriba2= configurar("/Jugador/samuel_espalda_caminando2");
        frente= configurar("/Jugador/samuel_frente");
        abajo1= configurar("/Jugador/samuel_caminando1");
        abajo2= configurar("/Jugador/samuel_caminando2");
        lado1= configurar("/Jugador/samuel_Derecha");
        der1= configurar("/Jugador/samuel_Derecho_caminando1");  
        der2= configurar("/Jugador/samuel_Derecho_caminando2");
        lado2= configurar("/Jugador/samuel_Izquierda");
        izq1= configurar("/Jugador/samuel_Izquierda_caminando1");
        izq2= configurar("/Jugador/samuel_Izquierda_caminando2"); 
    }
    
    public void actualizar() {
        if (manejoTeclas != null) {
            boolean moviendose = false;
            
            if (manejoTeclas.arribaPresionado || manejoTeclas.abajoPresionado || manejoTeclas.derechaPresionado || manejoTeclas.izquierdaPresionado) {
                moviendose = true;
                if (manejoTeclas.arribaPresionado) {
                    direccion = "arriba";
                } else if (manejoTeclas.izquierdaPresionado) {
                    direccion = "izquierda";
                } else if (manejoTeclas.abajoPresionado) {
                    direccion = "abajo";
                } else if (manejoTeclas.derechaPresionado) {
                    direccion = "derecha";
                }
            colisionActivada=false;
            panelJuego.verificarC.VerificarRecuadro(this);
            int indiceObjeto=panelJuego.verificarC.VerificarObjeto(this, true);
            recogerObjeto(indiceObjeto);
            int indiceNPC= panelJuego.verificarC.VerificarEntidad(this, panelJuego.npc);
            interactuarNPC(indiceNPC);
                if (!colisionActivada) {
                    switch (direccion) {
                        case "arriba":
                            mundoY -= velocidad;
                            break;
                        case "abajo":
                            mundoY += velocidad;
                            break;
                        case "derecha":
                            mundoX += velocidad;
                            break;
                        case "izquierda":
                            mundoX -= velocidad;
                            break;
                    }
                    Paquete02Mover paquete = new Paquete02Mover(this.getNombreUsuario(), mundoX, mundoY, spriteNum, direccion);
                    paquete.escribirDatos(this.panelJuego.clienteSocket);
                }
                actualizarAnimacion();
            }
            
            if (!moviendose) {
                if ("arriba".equals(direccion))
                    direccion = "espalda";
                else if ("abajo".equals(direccion))
                    direccion = "frente";
                else if ("derecha".equals(direccion))
                    direccion = "lado1";
                else if ("izquierda".equals(direccion))
                    direccion = "lado2";       
            }
            
            // Verificar si la dirección ha cambiado antes de enviar el paquete
            if (!direccion.equals(direccionAnterior)) {
                Paquete02Mover paquete = new Paquete02Mover(this.getNombreUsuario(), mundoX, mundoY, spriteNum, direccion);
                paquete.escribirDatos(this.panelJuego.clienteSocket);
                direccionAnterior = direccion;
            }
        }
    }
    
    public void recogerObjeto(int indice) {
    if (indice != 999 && panelJuego.obj[indice] != null) {
        
        switch (panelJuego.obj[indice].nombre) {
            case "Basura":
                if (manejoTeclas.recogerObjetoPresionado) {
                    panelJuego.obj[indice] = null;
                    panelJuego.reproducirEfectosSonido(1);
                    cntBasura++;
                    panelJuego.ui.mostrarMensaje("Conseguiste basura!");
                    // Crear y enviar el paquete para eliminar el objeto en todos los clientes
                    Paquete03EliminarObjeto paqueteEliminar = new Paquete03EliminarObjeto(indice);
                    paqueteEliminar.escribirDatos(this.panelJuego.clienteSocket);
                }
                break;
            // Agregar más objetos según sea necesario
        }
    }
}   
    public void interactuarNPC(int indice){
        if(indice != 999){
            System.out.println("Verificando colision con el npc");
        }
    }
    
    public void actualizarAnimacion() {
        spriteCont++;
        if (spriteCont > 12) {
            if (spriteNum == 2)
                spriteNum = 1;
            else if (spriteNum == 1)
                spriteNum = 2;
            spriteCont = 0;
        }
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }
    
    public void dibujar(Graphics2D g2) {
        BufferedImage imagen = frente;
        switch (direccion) {
            case "arriba":
                if (spriteNum == 1)
                    imagen = arriba1;
                else if (spriteNum == 2)
                    imagen = arriba2;
                break;
            case "abajo":
                if (spriteNum == 1)
                    imagen = abajo1;
                else if (spriteNum == 2)
                    imagen = abajo2;
                break;
            case "derecha":
                if (spriteNum == 1)
                    imagen = der1;
                else if (spriteNum == 2)
                    imagen = der2;
                break;
            case "izquierda":
                if (spriteNum == 1)
                    imagen = izq1;
                else if (spriteNum == 2)
                    imagen = izq2;
                break;
            case "frente":
                imagen = frente;
                break;
            case "espalda":
                imagen = espalda;
                break;
            case "lado1":
                imagen = lado1;
                break;
            case "lado2":
                imagen = lado2;
                break;
        }
        
        int pantallaX= this.mundoX - panelJuego.jugador.mundoX + panelJuego.jugador.pantallaX;
        int pantallaY= this.mundoY - panelJuego.jugador.mundoY + panelJuego.jugador.pantallaY;
    
        g2.drawImage(imagen, pantallaX, pantallaY, null);
        if (nombreUsuario != null) {
            g2.setFont(new Font("Courier New", Font.BOLD, 18));
            g2.setColor(Color.WHITE);
            int nombreX = pantallaX + (panelJuego.tamannoRecuadros / 2) - (g2.getFontMetrics().stringWidth(nombreUsuario) / 2);
            int nombreY = pantallaY - 10;
            g2.drawString(nombreUsuario, nombreX, nombreY);
        }
    }
}
