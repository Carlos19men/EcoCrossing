package Jugador;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entidad {
    public int mundoX, mundoY, velocidad;
    public BufferedImage frente, espalda, arriba1, arriba2, abajo1, abajo2,lado1, der1, der2, lado2, izq1, izq2;
    public String direccion, mostrar;
    public int spriteCont=0;
    public int spriteNum=1;  
    public Rectangle areaSolida;
    public int areaSolidaDefaultX, areaSolidaDefaultY;
    public boolean colisionActivada=false;
    public boolean enMovimiento;

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

    public int getSpriteNum() {
        return spriteNum;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    
    
}
