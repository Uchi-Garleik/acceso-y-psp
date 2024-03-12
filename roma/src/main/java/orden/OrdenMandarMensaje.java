package orden;

import Centurion.src.main.centurion.Centurion;

public class OrdenMandarMensaje extends Orden {

    private Centurion destinatario;
    private String mensaje;

    public OrdenMandarMensaje(int idOrden, Centurion destinatario, String mensaje) {
        super(TipoOrden.MANDAR_MENSAJE);
        this.destinatario = destinatario;
        this.mensaje = mensaje;
    }

    public Centurion getDestinatario() {
        return destinatario;
    }

    public String getMensaje() {
        return mensaje;
    }

    @Override
    public String getDetalles() {
        return "Mandar mensaje a : " + mensaje;
    }

}

