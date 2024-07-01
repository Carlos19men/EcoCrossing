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
public class OBJ_Basura extends SuperObjeto {
    
    PanelJuego panelJuego;
    
    //Constructor
    public OBJ_Basura (PanelJuego panelJuego){ //Sin parametros
        nombre="Basura";
        try{
            imagen= ImageIO.read(getClass().getResourceAsStream("/Objetos/basura.png"));
            herramientaUtil.escalarImagen(imagen, panelJuego.tamannoRecuadros, panelJuego.tamannoRecuadros);
        }catch(IOException e){
            e.printStackTrace();
        }
        colision=true;
    }
}
