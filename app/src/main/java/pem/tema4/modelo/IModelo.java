package pem.tema4.modelo;

import java.util.Vector;

public interface IModelo {
	
	// TODO Añadir el método obtenerDatos() que recupera los datos a mostrar en la lista maestro.
    public void obtenerDatos();
	// TODO Añadir el método obtenerDetalles(int posicion) que recupera los datos de una receta dada su posición
	// en la lista maestro.
    public void obtenerDetalle(int posicion);

	// TODO Añadir el método agregarReceta(Object[] datos) que almacena una nueva receta en la lista
	// de recetas.
    public void agregarReceta(Object[] datos);

    public void eliminarReceta(int posicion);
}


