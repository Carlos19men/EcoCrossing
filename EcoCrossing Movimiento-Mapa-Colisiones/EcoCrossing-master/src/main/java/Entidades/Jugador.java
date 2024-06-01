package Entidades;
import com.mycompany.ecocrossing.ManejoTeclas;
import com.mycompany.ecocrossing.PanelJuego;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Jugador extends Entidad{
    PanelJuego panelJuego;
    ManejoTeclas manejoTeclas;
    
    public final int pantallaX, pantallaY;

    //************Cargar Sprites******************//

    public Jugador(PanelJuego panelJuego, ManejoTeclas manejoTeclas) {
        this.panelJuego = panelJuego;
        this.manejoTeclas = manejoTeclas;
        
        //Centro de la pantalla con un ajuste para que no este solo la esquina superior izquierda del sprite del jugador
        pantallaX = panelJuego.anchoPantalla/2 - (panelJuego.tamannoRecuadros/2);
        pantallaY = panelJuego.largoPantalla/2 - (panelJuego.tamannoRecuadros/2);
        areaSolida= new Rectangle();
        areaSolida.x=8;
        areaSolida.y=16;
        areaSolida.width=32;
        areaSolida.height=32;
        setValoresporDefecto();
        getImagenJugador();
    }

    public void setValoresporDefecto(){
        //**Posicionar al jugador por defecto**
        mundoX = panelJuego.tamannoRecuadros * 15;
        mundoY = panelJuego.tamannoRecuadros * 15;
        velocidad = 4;
        direccion= "frente";
    }
    
    public void getImagenJugador(){
        try{
            espalda=ImageIO.read(getClass().getResourceAsStream("/Jugador/samuel_espalda.png"));
            arriba1=ImageIO.read(getClass().getResourceAsStream("/Jugador/samuel_espalda_caminando1.png"));
            arriba2=ImageIO.read(getClass().getResourceAsStream("/Jugador/samuel_espalda_caminando2.png"));
            frente=ImageIO.read(getClass().getResourceAsStream("/Jugador/samuel_frente.png"));
            abajo1=ImageIO.read(getClass().getResourceAsStream("/Jugador/samuel_caminando1.png"));
            abajo2=ImageIO.read(getClass().getResourceAsStream("/Jugador/samuel_caminando2.png"));
            lado1=ImageIO.read(getClass().getResourceAsStream("/Jugador/samuel_Derecha.png"));
            der1=ImageIO.read(getClass().getResourceAsStream("/Jugador/samuel_Derecho_caminando1.png"));
            der2=ImageIO.read(getClass().getResourceAsStream("/Jugador/samuel_Derecho_caminando2.png"));
            lado2=ImageIO.read(getClass().getResourceAsStream("/Jugador/samuel_Izquierda.png"));
            izq1=ImageIO.read(getClass().getResourceAsStream("/Jugador/samuel_izquierda_caminando1.png"));
            izq2=ImageIO.read(getClass().getResourceAsStream("/Jugador/samuel_izquierda_caminando2.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public void setPanelJuego(PanelJuego panelJuego) {
        this.panelJuego = panelJuego;
    }
    
    public void actualizar(){
        //Moverse la cantidad de pixeles que indique la velocidad del jugador
        if (manejoTeclas.arribaPresionado || manejoTeclas.abajoPresionado 
                || manejoTeclas.derechaPresionado || manejoTeclas.izquierdaPresionado){
            if(manejoTeclas.arribaPresionado){
                direccion="arriba";
                
            }else if(manejoTeclas.izquierdaPresionado){
                direccion="izquierda";
                
            }else if(manejoTeclas.abajoPresionado){
                direccion="abajo";
               
            }else if(manejoTeclas.derechaPresionado){
                direccion="derecha";
                
            }
            colisionActivada=false;
            panelJuego.verificarC.VerificarRecuadro(this);
            
            if(!colisionActivada){
                switch(direccion){
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
            }
            spriteCont++;
            if (spriteCont>12){
                if(spriteNum==2)
                    spriteNum=1;
                else if (spriteNum==1)
                    spriteNum=2;
                spriteCont=0;
            }
        }
        
        if ("arriba".equals(direccion) && !manejoTeclas.arribaPresionado)
            direccion="espalda";
        else if ("abajo".equals(direccion) && !manejoTeclas.abajoPresionado)
            direccion="frente";
        else if("derecha".equals(direccion) && !manejoTeclas.derechaPresionado)
            direccion="lado1";
        else if ("izquierda".equals(direccion) && !manejoTeclas.izquierdaPresionado)
            direccion="lado2";
    }
    
    public void dibujar(Graphics2D g2){
       //g2.setColor(Color.white);
        //g2.fillRect(x, y, panelJuego.tamannoRecuadros, panelJuego.tamannoRecuadros);
        BufferedImage imagen=frente;
        switch(direccion){
            case "arriba":
                if(spriteNum==1)
                    imagen= arriba1;
                else if(spriteNum==2)
                    imagen=arriba2;
                break;
            case "abajo":
                if(spriteNum==1)
                    imagen= abajo1;
                else if(spriteNum==2)
                    imagen=abajo2;
                break;
            case "derecha":
                if(spriteNum==1)
                    imagen= der1;
                else if(spriteNum==2)
                    imagen=der2;
                break;
            case "izquierda":
                if(spriteNum==1)
                    imagen= izq1;
                else if(spriteNum==2)
                    imagen=izq2;
                break;
            case "frente":
                imagen=frente;
                break;
            case "espalda":
                imagen=espalda;
                break;
            case "lado1":
                imagen=lado1;
                break;
            case "lado2":
                imagen=lado2;
                break;
        }
        g2.drawImage(imagen, pantallaX, pantallaY, panelJuego.tamannoRecuadros, panelJuego.tamannoRecuadros, null);
    }
}
