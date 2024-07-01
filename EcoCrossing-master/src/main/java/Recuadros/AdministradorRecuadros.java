package Recuadros;
import com.mycompany.ecocrossing.HerramientaUtil;
import com.mycompany.ecocrossing.PanelJuego;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

public final class AdministradorRecuadros {
    PanelJuego panelJuego;
    public Recuadro[] recuadros;
    public int numeroRecuadroMapa[][]; 
    
    public AdministradorRecuadros(PanelJuego panelJuego){
        this.panelJuego = panelJuego;
        recuadros = new Recuadro[100];
        numeroRecuadroMapa = new int [panelJuego.maxColumnasMundo][panelJuego.maxFilasMundo];
        obtenerImagenRecuadro();
        cargarMapa("/Mapas/mapa01.txt");
    }
    
    public void obtenerImagenRecuadro(){
        configuracion(0, "tierra1", false);
        configuracion(1, "cofre", true);
        configuracion(2, "agua", true);
        configuracion(3, "tierra0", false);
        configuracion(4, "tierra2", false);
        configuracion(5, "flor", false);
        configuracion(6, "flor2", false);
        configuracion(7, "arbol1_1", true);
        configuracion(8, "arbol1_2", true);
        configuracion(9, "arbol1_3", true);
        configuracion(10, "arbol1_4", true);
        configuracion(11, "arbol2_1", true);
        configuracion(12, "arbol2_2", true);
        configuracion(13, "casa3_1", true);
        configuracion(14, "casa3_2", true);
        configuracion(15, "casa3_3", true);
        configuracion(16, "casa3_4", true);
        configuracion(17, "casa3_5", true);
        configuracion(18, "casa3_6", true);
        configuracion(19, "casa3_7", true);
        configuracion(20, "casa3_8", true);
        configuracion(21, "casa3_9", true);
        configuracion(22, "casa3_10", true);
        configuracion(23, "casa3_11", true);
        configuracion(24, "casa3_12", true);
        configuracion(25, "casa3_13", true);
        configuracion(26, "casa3_14", true);
        configuracion(27, "casa3_15", true);
        configuracion(28, "casa3_16", true);
        configuracion(29, "casa3_17", true);
        configuracion(30, "casa3_18", true);
        configuracion(31, "casa3_19", true);
        configuracion(32, "casa3_20", true);
        configuracion(33, "casa3_21", true);
        configuracion(34, "casa3_22", true);
        configuracion(35, "casa3_23", true);
        configuracion(36, "casa3_24", true);
        configuracion(37, "fuente_1", true);
        configuracion(38, "fuente_2", true);
        configuracion(39, "fuente_3", true);
        configuracion(40, "fuente_4", true);
        configuracion(41, "fuente_5", true);
        configuracion(42, "fuente_6", true);
        configuracion(43, "fuente_7", true);
        configuracion(44, "fuente_8", true);
        configuracion(45, "fuente_9", true);
        configuracion(46, "palmera_1", true);
        configuracion(47, "palmera_2", true);
        configuracion(48, "palmera_3", true);
        configuracion(49, "palmera_4", true);
        configuracion(50, "cartel", true);
        configuracion(51, "farol_1", true);
        configuracion(52, "farol_2", true);
        configuracion(53, "casaPlaya_1", true);
        configuracion(54, "casaPlaya_2", true);
        configuracion(55, "casaPlaya_3", true);
        configuracion(56, "casaPlaya_4", true);
        configuracion(57, "casaPlaya_5", true);
        configuracion(58, "casaPlaya_6", true);
        configuracion(59, "casaPlaya_7", true);
        configuracion(60, "casaPlaya_8", true);
        configuracion(61, "casaPlaya_9", true);
        configuracion(62, "casaPlaya_10", true);
        configuracion(63, "casaPlaya_11", true);
        configuracion(64, "casaPlaya_12", true);
        configuracion(65, "casaPlaya_13", true);
        configuracion(66, "casaPlaya_14", true);
        configuracion(67, "casaPlaya_15", true);
        configuracion(68, "casaPlaya_16", true);
        configuracion(69, "casaPlaya_17", true);
        configuracion(70, "casaPlaya_18", true);
        configuracion(71, "casaPlaya_19", true);
        configuracion(72, "casaPlaya_20", true);
        configuracion(73, "aguaTierra1", true);
        configuracion(74, "aguaTierra2", true);
        configuracion(75, "tierraAlta_1", true);
        configuracion(76, "tierraAlta_2", true);
        configuracion(77, "tierraAlta_3", true);
        configuracion(78, "tierraAlta_4", true);
    }
    
    public void configuracion(int indice, String nombreImagen, boolean colision) {
        HerramientaUtil herramientaUtil= new HerramientaUtil();
    
        try {
            recuadros[indice]= new Recuadro();
            recuadros[indice].imagen= ImageIO.read(getClass().getResourceAsStream("/Recuadros/"+nombreImagen+".png"));
            recuadros[indice].imagen= herramientaUtil.escalarImagen(recuadros[indice].imagen, panelJuego.tamannoRecuadros, panelJuego.tamannoRecuadros);
            recuadros[indice].colision= colision;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //Leer el archivo del mapa linea por linea
    public void cargarMapa(String direccionArchivo){
    try {
        InputStream is = getClass().getResourceAsStream(direccionArchivo);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        
        int columna = 0;
        int fila = 0;
        
        while (fila < panelJuego.maxFilasMundo) {
            String linea = br.readLine();
            
            String numeros[] = linea.split(" ");
            
            while (columna < panelJuego.maxColumnasMundo) {
                int num = Integer.parseInt(numeros[columna]);
                numeroRecuadroMapa[columna][fila] = num;
                columna++;
            }
            
            if (columna == panelJuego.maxColumnasMundo) {
                columna = 0;
                fila++;
            }
        }
        br.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
        
    public void dibujar(Graphics2D g2){
   int columnaMundo = 0;
   int filaMundo = 0;
   
   while (columnaMundo < panelJuego.maxColumnasMundo && filaMundo < panelJuego.maxFilasMundo) {
       int numeroRecuadro = numeroRecuadroMapa[columnaMundo][filaMundo];
       int mundoX = columnaMundo * panelJuego.tamannoRecuadros;
       int mundoY = filaMundo * panelJuego.tamannoRecuadros;
       int pantallaX = mundoX - panelJuego.jugador.mundoX + panelJuego.jugador.pantallaX;
       int pantallaY = mundoY - panelJuego.jugador.mundoY + panelJuego.jugador.pantallaY;
        
       if (mundoX + panelJuego.tamannoRecuadros > panelJuego.jugador.mundoX - panelJuego.jugador.pantallaX
          && mundoX - panelJuego.tamannoRecuadros < panelJuego.jugador.mundoX + panelJuego.jugador.pantallaX 
          && mundoY + panelJuego.tamannoRecuadros > panelJuego.jugador.mundoY - panelJuego.jugador.pantallaY
          && mundoY - panelJuego.tamannoRecuadros < panelJuego.jugador.mundoY + panelJuego.jugador.pantallaY) {
            g2.drawImage(recuadros[numeroRecuadro].imagen, pantallaX, pantallaY, panelJuego.tamannoRecuadros, panelJuego.tamannoRecuadros, null);
       }
       columnaMundo++;
       if (columnaMundo == panelJuego.maxColumnasMundo) {
           columnaMundo = 0;
           filaMundo++;
       }
   }
}
    
    
}
