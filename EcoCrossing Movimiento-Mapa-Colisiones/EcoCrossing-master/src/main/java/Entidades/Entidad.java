package Entidades;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entidad {
    public int mundoX, mundoY, velocidad;
    public BufferedImage frente, espalda, arriba1, arriba2, abajo1, abajo2,lado1, der1, der2, lado2, izq1, izq2;
    public String direccion, mostrar;
    public int spriteCont=0;
    public int spriteNum=1;  
    public Rectangle areaSolida;
    public boolean colisionActivada=false;
    
}
