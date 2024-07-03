/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objeto;

import com.mycompany.ecocrossing.PanelJuego;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Maria Sandoval
 */
public class Basura extends SuperObjeto {
    
    //Constructor
    public Basura (String nombre){ //Sin parametros
        this.nombre = nombre;
        colision=true;
    }
    
    //metodos 
    public void cargar(int escala){
        try{
            imagen= ImageIO.read(getClass().getResourceAsStream("/Objetos/Basura/"+nombre+".png"));
            herramientaUtil.escalarImagen(imagen, escala, escala);
        }catch(IOException e){
            System.out.println("Error al cargar la imagen (Line 29)");
            e.printStackTrace();
        }
    }
    
    
}
