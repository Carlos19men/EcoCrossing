package EcoCrossing.net.paquetes;
import EcoCrossing.net.ClienteJuego;
import EcoCrossing.net.ServidorJuego;

public class Paquete02Mover extends Paquete{
    private String nombreUsuario;
    private int x, y;
    private int spriteNum;
    private String direccion;
    
    public Paquete02Mover(byte[] datos){
        super(02);
        String[] arregloDatos = leerDatos(datos).split(",");
        this.nombreUsuario = arregloDatos[0];
        this.x = Integer.parseInt(arregloDatos[1]);
        this.y = Integer.parseInt(arregloDatos[2]);
        this.spriteNum = Integer.parseInt(arregloDatos[3]);
        this.direccion = arregloDatos[4];
    }
    
    public Paquete02Mover(String nombreUsuario, int x, int y, int spriteNum, String direccion) {
        super(02);
        this.nombreUsuario = nombreUsuario;
        this.x = x;
        this.y = y;
        this.spriteNum = spriteNum;
        this.direccion = direccion;
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
    public byte[] getDatos() {
        return ("02" + this.nombreUsuario + "," + this.x + "," + this.y + "," + this.spriteNum + "," + this.direccion).getBytes();
    }
    
    public String getNombreUsuario(){
        return nombreUsuario;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public String getDireccion() {
        return direccion;
    }

    public int getSpriteNum() {
        return spriteNum;
    }
    
    
}
