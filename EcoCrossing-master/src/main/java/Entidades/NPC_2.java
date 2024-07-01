package Entidades;

import com.mycompany.ecocrossing.PanelJuego;

/**
 *
 * @author Maria Sandoval
 */
public class NPC_2 extends Entidad{
    
    public NPC_2(PanelJuego panelJuego) {
        super(panelJuego);
        direccion="frente";
        velocidad=1;
        ObtenerImagen();
    }
    
    public void ObtenerImagen() {
        espalda= configurar("/NPC/npc2_espalda");
        arriba1= configurar("/NPC/npc2_espalda_caminando1");
        arriba2= configurar("/NPC/npc2_espalda_caminando2");
        frente= configurar("/NPC/npc2_frente");
        abajo1= configurar("/NPC/npc2_caminando1");
        abajo2= configurar("/NPC/npc2_caminando2");
        lado1= configurar("/NPC/npc2_Derecha");
        der1= configurar("/NPC/npc2_Derecha_caminando1");  
        der2= configurar("/NPC/npc2_Derecha_caminando2");
        lado2= configurar("/NPC/npc2_Izquierda");
        izq1= configurar("/NPC/npc2_Izquierda_caminando1");
        izq2= configurar("/NPC/npc2_Izquierda_caminando2"); 
    }
}
