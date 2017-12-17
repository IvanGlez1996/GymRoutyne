package pem.tema4.modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public interface IModelo {
	
    public void obtenerRutinas();
	// TODO Añadir el método obtenerDetalles(int posicion) que recupera los datos de una receta dada su posición
	// en la lista maestro.
    public void obtenerEjerciciosRutinas(int posicion);

	// TODO Añadir el método agregarRutina(Object[] datos) que almacena una nueva receta en la lista
	// de recetas.
    public void agregarRutina(String nombre);

    public void eliminarRutina(int posicion);

    ArrayList<Object[]> getEjercicios();

    void agregarEjercicioRutina(int idRutina, int idEjercicio, int sets, int reps);

    int getIdRutina(int posicion);

}


