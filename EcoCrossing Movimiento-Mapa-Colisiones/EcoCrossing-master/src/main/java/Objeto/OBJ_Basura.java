/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objeto;

import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Maria Sandoval
 */
public class OBJ_Basura extends SuperObjeto {
    
    //Constructor
    public OBJ_Basura (){ //Sin parametros
        nombre="Basura";
        try{
            imagen= ImageIO.read(getClass().getResourceAsStream("/Objetos/basura.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
