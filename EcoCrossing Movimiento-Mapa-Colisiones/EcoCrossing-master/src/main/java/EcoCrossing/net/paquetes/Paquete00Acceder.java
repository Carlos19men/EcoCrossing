package EcoCrossing.net.paquetes;
import EcoCrossing.net.ClienteJuego;
import EcoCrossing.net.ServidorJuego;

public class Paquete00Acceder extends Paquete{
    //private String nombreUsuario;
    
    public Paquete00Acceder(byte[] datos){
        super(00);
        //this.nombreUsuario = leerDatos(datos);
    }
    
    public Paquete00Acceder(String nombreUsuario){
        super(00);
        //this.nombreUsuario = leerDatos(datos);
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
        //return("00" + this.nombreUsuario).getBytes();
        return "00".getBytes();
    }
}
