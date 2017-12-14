package pem.tema4.presentador;

public interface IPresentadorPrincipal {
	
	// TODO A�adir el m�todo obtenerRutinas() de tipo void que solicita los datos de la lista maestro al modelo.
    public void obtenerRutinas();
	// TODO A�adir el m�todo obtenerDetalles(int posicion) que recupera los datos de una receta dada su posici�n
	// en la lista maestro.
    public void obtenerEjercicios(int posicion);
	// TODO A�adir el m�todo tratarAgregar() que lanza la vista de agregaci�n por medio del mediador.
    public void tratarAgregar();

    public void eliminarReceta(int posicion);
}
