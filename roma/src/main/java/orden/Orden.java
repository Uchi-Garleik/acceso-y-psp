package orden;

public abstract class Orden {

    private int idOrden;
    private TipoOrden tipoOrden;
    private EstadoOrden estadoOrden;

    public Orden(TipoOrden tipoOrden) {
        this.tipoOrden = tipoOrden;
        this.estadoOrden = EstadoOrden.PENDIENTE;
    }

    public int getIdOrden() {
        return idOrden;
    }

    public TipoOrden getTipoOrden() {
        return tipoOrden;
    }

    public EstadoOrden getEstadoOrden() {
        return estadoOrden;
    }

    public void setEstadoOrden(EstadoOrden estadoOrden) {
        this.estadoOrden = estadoOrden;
    }

    public abstract String getDetalles();

}

