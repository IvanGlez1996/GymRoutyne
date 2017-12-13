package pem.tema4.modelo;

import java.util.Vector;

public interface IModelo {
	
	// TODO A�adir el m�todo obtenerDatos() que recupera los datos a mostrar en la lista maestro.
    public void obtenerDatos();
	// TODO A�adir el m�todo obtenerDetalles(int posicion) que recupera los datos de una receta dada su posici�n
	// en la lista maestro.
    public void obtenerDetalle(int posicion);

	// TODO A�adir el m�todo agregarReceta(Object[] datos) que almacena una nueva receta en la lista
	// de recetas.
    public void agregarReceta(Object[] datos);

    public void eliminarReceta(int posicion);
}


