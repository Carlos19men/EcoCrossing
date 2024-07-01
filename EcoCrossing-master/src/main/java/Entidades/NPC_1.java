package Entidades;

import EcoCrossing.net.paquetes.Paquete02Mover;
import com.mycompany.ecocrossing.PanelJuego;
import java.util.Random;

/**
 *
 * @author Maria Sandoval
 */
public class NPC_1 extends Entidad{
    
    public NPC_1(PanelJuego panelJuego) {
        super(panelJuego);
        direccion="frente";
        velocidad=1;
        ObtenerImagen();
    }
    
    public void ObtenerImagen() {
        espalda= configurar("/NPC/npc1_espalda");
        arriba1= configurar("/NPC/npc1_espalda_caminando1");
        arriba2= configurar("/NPC/npc1_espalda_caminando2");
        frente= configurar("/NPC/npc1_frente");
        abajo1= configurar("/NPC/npc1_caminando1");
        abajo2= configurar("/NPC/npc1_caminando2");
        lado1= configurar("/NPC/npc1_Derecha");
        der1= configurar("/NPC/npc1_Derecha_caminando1");  
        der2= configurar("/NPC/npc1_Derecha_caminando2");
        lado2= configurar("/NPC/npc1_Izquierda");
        izq1= configurar("/NPC/npc1_Izquierda_caminando1");
        izq2= configurar("/NPC/npc1_Izquierda_caminando2"); 
    }
    
    @Override
    public void setAccion() {
        contAccion++;
        if (contAccion == 120) {
            Random random = new Random();
            int numR = random.nextInt(100) + 1;  // Genera un número del 1 al 100
            if (numR <= 25)
                direccion = "arriba";
            else if (numR <= 50)
                direccion = "abajo";
            else if (numR <= 75)
                direccion = "derecha";
            else
                direccion = "izquierda";
            contAccion = 0;

            // Enviar el movimiento del NPC a todos los clientes conectados
            Paquete02Mover paqueteMovimientoNPC = new Paquete02Mover("Entidades.NPC_1", this.mundoX, this.mundoY, this.spriteNum, this.direccion);
            if(panelJuego.clienteSocket != null)
                paqueteMovimientoNPC.escribirDatos(panelJuego.clienteSocket);
        }
    }

}
