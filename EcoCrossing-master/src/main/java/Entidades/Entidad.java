package Entidades;

import com.mycompany.ecocrossing.HerramientaUtil;
import com.mycompany.ecocrossing.PanelJuego;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Entidad {
    public PanelJuego panelJuego;
    public int mundoX, mundoY, velocidad;
    public BufferedImage frente, espalda, arriba1, arriba2, abajo1, abajo2,lado1, der1, der2, lado2, izq1, izq2;
    public String direccion, mostrar;
    public int spriteCont=0;
    public int spriteNum=1;  
    public Rectangle areaSolida= new Rectangle(0, 0, 48, 48);
    public int areaSolidaDefaultX, areaSolidaDefaultY;
    public boolean colisionActivada=false;
    public boolean enMovimiento;
    public int contAccion;
    
    public Entidad(PanelJuego panelJuego){
        this.panelJuego=panelJuego;
    }
    
    public void setSpriteCont(int spriteCont) {
        this.spriteCont = spriteCont;
    }

    public void setSpriteNum(int spriteNum) {
        this.spriteNum = spriteNum;
    }

    public void setEnMovimiento(boolean enMovimiento) {
        this.enMovimiento = enMovimiento;
    }

    public void setMundoX(int mundoX) {
        this.mundoX = mundoX;
    }

    public void setMundoY(int mundoY) {
        this.mundoY = mundoY;
    }

    public int getSpriteCont() {
        return spriteCont;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public int getSpriteNum() {
        return spriteNum;
    }
    
    public void setAccion(){
    }
    
    public void Actualizar(){
        setAccion();
        colisionActivada=false;
        
        //Verificar colisiones
        panelJuego.verificarC.VerificarRecuadro(this);
        panelJuego.verificarC.VerificarObjeto(this, false);
        panelJuego.verificarC.VerificarJugador(this);
        
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
            }
        
        spriteCont++;
        if (spriteCont > 12) {
            if (spriteNum == 2)
                spriteNum = 1;
            else if (spriteNum == 1)
                spriteNum = 2;
            spriteCont = 0;
        }
    }
    
    public void dibujar(Graphics2D g2){
        BufferedImage imagen= null;
        int pantallaX = mundoX - panelJuego.jugador.mundoX + panelJuego.jugador.pantallaX;
        int pantallaY = mundoY - panelJuego.jugador.mundoY + panelJuego.jugador.pantallaY;
        if (mundoX + panelJuego.tamannoRecuadros > panelJuego.jugador.mundoX - panelJuego.jugador.pantallaX
            && mundoX - panelJuego.tamannoRecuadros < panelJuego.jugador.mundoX + panelJuego.jugador.pantallaX 
            && mundoY + panelJuego.tamannoRecuadros > panelJuego.jugador.mundoY - panelJuego.jugador.pantallaY
            && mundoY - panelJuego.tamannoRecuadros < panelJuego.jugador.mundoY + panelJuego.jugador.pantallaY) {
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
            g2.drawImage(imagen, pantallaX, pantallaY, panelJuego.tamannoRecuadros, panelJuego.tamannoRecuadros, null);
       }
    }
    
    public BufferedImage configurar(String nombreImagen) {
        HerramientaUtil herramientaUtil= new HerramientaUtil();
        BufferedImage imagen=null;
        try {
            imagen= ImageIO.read(getClass().getResourceAsStream(""+nombreImagen+".png"));
            imagen= herramientaUtil.escalarImagen(imagen, panelJuego.tamannoRecuadros, panelJuego.tamannoRecuadros);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imagen;
    }
}
