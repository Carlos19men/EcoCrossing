
package Multijugador_2_Servidores;
import java.awt.Color;


public class Prueba1_prototipo {
    
    
    //las pantallas hacen el rol de clientes o jugadores 

    public static void main(String[] args) {

        Jugador j1 = new Jugador("Pepe",Color.magenta,5000,"10.68.17.31");
        j1.Conectar();
        j1.jugar();
    }
}
