package orden;

import java.io.Serializable;

public class OrdenTraerCosa extends Orden implements Serializable {

    private Cosa cosa;
    private int cantidad;

    public OrdenTraerCosa(){
        super(TipoOrden.TRAER_COSA);
    }

    public OrdenTraerCosa(Cosa cosa, int cantidad) {
        super(TipoOrden.TRAER_COSA);
        this.cosa = cosa;
        this.cantidad = cantidad;
    }

    public OrdenTraerCosa(Cosa cosa) {
        super(TipoOrden.TRAER_COSA);
        this.cosa = cosa;
    }

    public Cosa getCosa() {
        return cosa;
    }

    public int getCantidad() {
        return cantidad;
    }

    @Override
    public String getDetalles() {
        return "Traer " + cantidad + " " + cosa.toString() + " para Julio César.";
    }

}

