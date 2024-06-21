/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Multijugador;

/**
 *
 * @author carlos
 */
public class Paquete {
    private String tipo; 
    private Byte[] buffer; 

    public Paquete(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Byte[] getBuffer() {
        return buffer;
    }

    public void setBuffer(Byte[] buffer) {
        this.buffer = buffer;
    }
    
    //metodos 
}
