package EcoCrossing.net.paquetes;

import EcoCrossing.net.ClienteJuego;
import EcoCrossing.net.ServidorJuego;

public abstract class Paquete{
    
    public static enum TiposPaquete{
        INVALIDO(-1), ACCEDER(00), DESCONECTAR(01), MOVER(02), ELIMINAR_OBJETO(03);
        
        private int paqueteID;
        
        private TiposPaquete(int paqueteID){
            this.paqueteID = paqueteID;
        }
        
        public int getID(){
            return paqueteID;
        }
    }
    
    public byte paqueteID;
    
    public Paquete(int paqueteID){
        this.paqueteID = (byte) paqueteID;
    } 
    
    public abstract void escribirDatos(ClienteJuego cliente);
    
    public abstract void escribirDatos(ServidorJuego servidor);
    
    public String leerDatos(byte[] datos){
        String mensaje = new String(datos).trim();
        return mensaje.substring(2);
    }
    
    public abstract byte[] getDatos();
    
    public static TiposPaquete buscarPaquete(String paqueteID){
        try{
            return buscarPaquete(Integer.parseInt(paqueteID));
        } catch (NumberFormatException e){
            return TiposPaquete.INVALIDO;
        }
    }
    
    //Buscar paquetes por su ID
    public static TiposPaquete buscarPaquete(int ID){
        for(TiposPaquete tp : TiposPaquete.values())
            if(tp.getID() == ID)
                return tp;
        return TiposPaquete.INVALIDO;
    }
}
