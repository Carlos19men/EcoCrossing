package EcoCrossing.net.paquetes;
import EcoCrossing.net.ClienteJuego;
import EcoCrossing.net.ServidorJuego;

public class Paquete01Desconectar extends Paquete{
    private String nombreUsuario;
    
    public Paquete01Desconectar(byte[] datos){
        super(01);
        this.nombreUsuario = leerDatos(datos);
    }
    
    public Paquete01Desconectar(String nombreUsuario){
        super(01);
        this.nombreUsuario = nombreUsuario;
    }
    
    @Override
    public void escribirDatos(ClienteJuego cliente){ 
        cliente.enviarDatos(getDatos());
    }
    
    @Override
    public void escribirDatos(ServidorJuego servidor){   
        servidor.enviarDatos_TodoslosClientes(getDatos());
    }
    
    @Override
    public byte[] getDatos(){
        return("01" + this.nombreUsuario).getBytes();
    }
    
    public String getNombreUsuario(){
        return nombreUsuario;
    }
}
