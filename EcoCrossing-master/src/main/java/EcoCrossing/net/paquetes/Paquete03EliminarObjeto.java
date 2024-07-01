package EcoCrossing.net.paquetes;

import EcoCrossing.net.ClienteJuego;
import EcoCrossing.net.ServidorJuego;

public class Paquete03EliminarObjeto extends Paquete {
    private int indice;

    public Paquete03EliminarObjeto(byte[] datos) {
        super(03);
        this.indice = Integer.parseInt(leerDatos(datos));
    }

    public Paquete03EliminarObjeto(int indiceEliminado) {
        super(03);
        this.indice = indiceEliminado;
    }

    @Override
    public byte[] getDatos() {
        return ("03" + indice).getBytes();
    }

    @Override
    public void escribirDatos(ClienteJuego cliente) {
        cliente.enviarDatos(getDatos());
    }

    @Override
    public void escribirDatos(ServidorJuego servidor) {
        servidor.enviarDatos_TodoslosClientes(getDatos());
    }

    public int getIndice() {
        return indice;
    }
}
