/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package PanelJuego;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Sonido {
    Clip clip;
    // Usamos esta URL para almacenar la ruta de los archivos de sonido
    URL sonidoURL[] = new URL[30];

    public Sonido() {
        sonidoURL[0] = getClass().getResource("/Sonido/EcoCrossingTema.wav");
        sonidoURL[1] = getClass().getResource("/Sonido/RecogerObjetoSonido.wav");
    }

    public void colocarArchivo(int i) {
        try {
            AudioInputStream flujoEntradaAudio = AudioSystem.getAudioInputStream(sonidoURL[i]);
            clip = AudioSystem.getClip();
            clip.open(flujoEntradaAudio);
        } catch (IOException e) {
            System.out.println("Error de IO: " + e.getMessage());
        } catch (LineUnavailableException e) {
            System.out.println("Error de línea no disponible: " + e.getMessage());
        } catch (UnsupportedAudioFileException e) {
            System.out.println("Error de archivo de audio no soportado: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error de índice de arreglo fuera de límites: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("Error de puntero nulo: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error desconocido: " + e.getMessage());
        }
    }

    public void reproducir() {
        try {
            clip.start();
        } catch (NullPointerException e) {
            System.out.println("Error: No se ha cargado ningún archivo de sonido.");
        } catch (Exception e) {
            System.out.println("Error desconocido al reproducir: " + e.getMessage());
        }
    }

    public void bucle() {
        try {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (NullPointerException e) {
            System.out.println("Error: No se ha cargado ningún archivo de sonido.");
        } catch (Exception e) {
            System.out.println("Error desconocido al iniciar el bucle: " + e.getMessage());
        }
    }

    public void parar() {
        try {
            clip.stop();
        } catch (NullPointerException e) {
            System.out.println("Error: No se ha cargado ningún archivo de sonido.");
        } catch (Exception e) {
            System.out.println("Error desconocido al parar: " + e.getMessage());
        }
    }
}
