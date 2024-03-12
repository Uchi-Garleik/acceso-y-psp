package orden;

public class OrdenVigilar extends Orden {

    private Object objetivo;
    private int tiempo;

    public OrdenVigilar(int idOrden, Object objetivo, int tiempo) {
        super(TipoOrden.VIGILAR);
        this.objetivo = objetivo;
        this.tiempo = tiempo;
    }

    public Object getObjetivo() {
        return objetivo;
    }

    public int getTiempo() {
        return tiempo;
    }

    @Override
    public String getDetalles() {
        return "Vigilar " + objetivo.toString() + " durante " + tiempo + " segundos.";
    }

}

