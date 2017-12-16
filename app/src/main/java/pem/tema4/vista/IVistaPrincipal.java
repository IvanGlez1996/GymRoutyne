package pem.tema4.vista;

import java.util.ArrayList;

import pem.tema4.modelo.Ejercicio_rutina;
import pem.tema4.modelo.Rutina;

public interface IVistaPrincipal {
	
	// TODO Añadir el método actualizarMaestro(Object[] datos) que actualiza la lista maestro con los datos
	// recibidos por parámetros. En cada entrada del vector, está el nombre de una receta.
    public void actualizarMaestro(Object[] datos);
	// TODO Añadir el método actualizarDetalle(Object[] datos) que actualiza la lista detalle con los datos
	// de una receta recibidos por parámetros. Así:
	// datos[0] = almacena el nombre de la receta y en qué se usa para realizarla (String).
	// datos[1] = almacena una imagen de la receta (Bitmap).
	// datos[2] = almacena la descripción de la receta (String).
    public void actualizarDetalle(ArrayList<Ejercicio_rutina> datos);

    public void presentarAlerta(int posicion);

}
