package pem.tema4.presentador;

import java.util.ArrayList;
import java.util.HashMap;

public interface IPresentadorPrincipal {
	
	// TODO Añadir el método obtenerRutinas() de tipo void que solicita los datos de la lista maestro al modelo.
    public void obtenerRutinas();
	// TODO Añadir el método obtenerDetalles(int posicion) que recupera los datos de una receta dada su posición
	// en la lista maestro.
    public void obtenerEjercicios(int posicion);
	// TODO Añadir el método agregarRutina() que lanza la vista de agregación por medio del mediador.
    public void agregarRutina(String nombre);

    public void eliminarRutina(int posicion);

    ArrayList<Object[]> getEjercicios();

    void agregarEjercicioRutina(int idRutina, int idEjercicio, int sets, int reps);

    int getIdRutina(int posicion);

    void eliminarEjercicioRutina(int id);
}
