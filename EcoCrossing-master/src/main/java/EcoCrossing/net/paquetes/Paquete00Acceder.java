package EcoCrossing.net.paquetes;

import EcoCrossing.net.ClienteJuego;
import EcoCrossing.net.ServidorJuego;
import java.util.ArrayList;

public class Paquete00Acceder extends Paquete {
    private String nombreUsuario, direccion;
    private int x, y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public Paquete00Acceder(byte[] datos) {
        super(00);
        String[] arregloDatos = leerDatos(datos).split(",");
        this.nombreUsuario = arregloDatos[0];
        this.x = Integer.parseInt(arregloDatos[1]);
        this.y = Integer.parseInt(arregloDatos[2]);
        this.direccion = arregloDatos[3]; // Agregar dirección desde los datos
    }
    
    public Paquete00Acceder(String nombreUsuario, int x, int y, String direccion) {
        super(00);
        this.nombreUsuario = nombreUsuario;
        this.x = x;
        this.y = y;
        this.direccion = direccion;
    }
    
    @Override
    public void escribirDatos(ClienteJuego cliente) { 
        cliente.enviarDatos(getDatos());
    }
    
    @Override
    public void escribirDatos(ServidorJuego servidor) {   
        servidor.enviarDatos_TodoslosClientes(getDatos());
    }
    
    @Override
    public byte[] getDatos() {
        return ("00" + this.nombreUsuario + "," + this.x + "," + this.y + "," + this.direccion).getBytes();
    }
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getDireccion() {
        return direccion;
    }
}
