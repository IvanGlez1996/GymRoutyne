package pem.tema4.presentador;

public interface IPresentadorPrincipal {
	
	// TODO Añadir el método obtenerRutinas() de tipo void que solicita los datos de la lista maestro al modelo.
    public void obtenerRutinas();
	// TODO Añadir el método obtenerDetalles(int posicion) que recupera los datos de una receta dada su posición
	// en la lista maestro.
    public void obtenerEjercicios(int posicion);
	// TODO Añadir el método tratarAgregar() que lanza la vista de agregación por medio del mediador.
    public void tratarAgregar();

    public void eliminarReceta(int posicion);
}
