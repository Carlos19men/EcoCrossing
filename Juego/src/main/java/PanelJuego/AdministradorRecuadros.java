package PanelJuego;
import Jugador.Jugador;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public final class AdministradorRecuadros {
    PanelJuego panelJuego;
    public Recuadro[] recuadros;
    public int numeroRecuadroMapa[][]; 
    
    public AdministradorRecuadros(PanelJuego panelJuego){
        this.panelJuego = panelJuego;
        recuadros = new Recuadro[10];
        numeroRecuadroMapa = new int [panelJuego.maxColumnasMundo][panelJuego.maxFilasMundo];
        obtenerImagenRecuadro();
        cargarMapa("/Mapas/mapa01.txt");
    }
    
    public void obtenerImagenRecuadro(){
        try {
            recuadros[0]= new Recuadro();
            recuadros[0].imagen= ImageIO.read(getClass().getResourceAsStream("/Recuadros/tierra1.png"));
            
            recuadros[1]= new Recuadro();
            recuadros[1].imagen= ImageIO.read(getClass().getResourceAsStream("/Recuadros/arbol1.png"));
            recuadros[1].colision= true;
            
            recuadros[2] = new Recuadro();
            recuadros[2].imagen= ImageIO.read(getClass().getResourceAsStream("/Recuadros/agua.png"));
            recuadros[2].colision=true;
            
            recuadros[3] = new Recuadro();
            recuadros[3].imagen= ImageIO.read(getClass().getResourceAsStream("/Recuadros/tierra0.png"));
            
            recuadros[4] = new Recuadro();
            recuadros[4].imagen= ImageIO.read(getClass().getResourceAsStream("/Recuadros/tierra2.png"));
            
            recuadros[5] = new Recuadro();
            recuadros[5].imagen= ImageIO.read(getClass().getResourceAsStream("/Recuadros/flor.png"));
            
            recuadros[6] = new Recuadro();
            recuadros[6].imagen= ImageIO.read(getClass().getResourceAsStream("/Recuadros/flor2.png"));
            
        } catch (IOException ex) {
            Logger.getLogger(AdministradorRecuadros.class.getName()).log(Level.SEVERE, null, ex);
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
        
    public void dibujar(Graphics2D g2, Jugador jugador){
        int columnaMundo = 0;
        int filaMundo = 0;

        while (columnaMundo < panelJuego.maxColumnasMundo && filaMundo < panelJuego.maxFilasMundo) {
            int numeroRecuadro = numeroRecuadroMapa[columnaMundo][filaMundo];
            int mundoX = columnaMundo * panelJuego.tamannoRecuadros;
            int mundoY = filaMundo * panelJuego.tamannoRecuadros;
            int pantallaX = mundoX - jugador.mundoX + jugador.pantallaX;
            int pantallaY = mundoY - jugador.mundoY + jugador.pantallaY;

            if (mundoX + panelJuego.tamannoRecuadros > jugador.mundoX - jugador.pantallaX
               && mundoX - panelJuego.tamannoRecuadros < jugador.mundoX + jugador.pantallaX 
               && mundoY + panelJuego.tamannoRecuadros > jugador.mundoY - jugador.pantallaY
               && mundoY - panelJuego.tamannoRecuadros < jugador.mundoY + jugador.pantallaY) {
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
