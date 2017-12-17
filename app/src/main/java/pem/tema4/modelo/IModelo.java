package pem.tema4.modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public interface IModelo {
	
    public void obtenerRutinas();
    public void obtenerEjerciciosRutinas(int posicion);

    public void agregarRutina(String nombre);

    public void eliminarRutina(int posicion);

    ArrayList<Object[]> getEjercicios();

    void agregarEjercicioRutina(int idRutina, int idEjercicio, int sets, int reps);

    int getIdRutina(int posicion);

    void eliminarEjercicioRutina(int id);

    String[] obtenerDetallesEjercicio(int id);

}


