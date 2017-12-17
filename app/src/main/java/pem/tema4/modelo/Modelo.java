package pem.tema4.modelo;

import android.os.Bundle;
import android.os.Environment;

import java.util.ArrayList;
import java.util.HashMap;

import pem.tema4.AppMediador;

public class Modelo implements IModelo {

    private static Modelo singleton = null;
    private ConjuntoDeRutinas conjuntoDeRutinas;
    private AdaptadorBD adaptadorBD;


    private Modelo() {
        conjuntoDeRutinas =	ConjuntoDeRutinas.getInstance();
        adaptadorBD = new AdaptadorBD(AppMediador.getInstance().getApplicationContext());

    }

    public static Modelo getInstance() {
        if (singleton == null)
            singleton = new Modelo();
        return singleton;
    }



    @Override
    public void obtenerRutinas() {
        if(adaptadorBD.abrir() != null) {
            Bundle extras = new Bundle();
            extras.putSerializable(AppMediador.CLAVE_LISTA_RUTINAS, adaptadorBD.obtenerRutinas());
            AppMediador.getInstance().sendBroadcast(AppMediador.AVISO_DATOS_LISTOS, extras);
            adaptadorBD.cerrar();
        }
    }


    @Override
    public void obtenerEjerciciosRutinas(int posicion) {
        if(adaptadorBD.abrir() != null) {
            int idRutina = adaptadorBD.obtenerRutinas().get(posicion).getId();
            Bundle bundle = new Bundle();
            bundle.putInt("idRutina", idRutina);
            ArrayList<Ejercicio_rutina> ejercicio_rutinas = new ArrayList<>();
            ejercicio_rutinas = adaptadorBD.obtenerEjerciciosRutina(idRutina);
            Bundle extras = new Bundle();
            extras.putParcelableArrayList(AppMediador.CLAVE_DETALLE_RUTINAS, ejercicio_rutinas);
            AppMediador.getInstance().sendBroadcast(AppMediador.AVISO_DETALLE_LISTO, extras);
            adaptadorBD.cerrar();
        }
    }



    @Override
    public void agregarRutina(String nombre) {
        if (adaptadorBD.abrir() != null) {
            adaptadorBD.añadirRutina(nombre);
            Bundle extras = new Bundle();
            extras.putSerializable(AppMediador.CLAVE_LISTA_RUTINAS, adaptadorBD.obtenerRutinas());
            AppMediador.getInstance().sendBroadcast(AppMediador.AVISO_DATOS_AGREGADOS, extras);
            adaptadorBD.cerrar();
        }
    }

    @Override
    public void eliminarRutina(int posicion) {
        if(adaptadorBD.abrir() != null) {
            ArrayList<Rutina> rutinas = adaptadorBD.obtenerRutinas();
            int id = rutinas.get(posicion).getId();
            adaptadorBD.eliminarRutina(id);
            Bundle extras = new Bundle();
            extras.putSerializable(AppMediador.CLAVE_LISTA_RUTINAS, adaptadorBD.obtenerRutinas());
            AppMediador.getInstance().sendBroadcast(AppMediador.AVISO_DATOS_ELIMINADOS, extras);
            adaptadorBD.cerrar();
        }
    }

    @Override
    public ArrayList<Object[]> getEjercicios(){
        ArrayList<Object[]> ejercicios = new ArrayList<>();
        if (adaptadorBD.abrir() != null){
            ejercicios = adaptadorBD.getEjercicios();
        }
        return ejercicios;
    }

    @Override
    public void agregarEjercicioRutina(int idRutina, int idEjercicio, int sets, int reps) {
        if (adaptadorBD.abrir() != null){
            adaptadorBD.añadirEjercicioRutina(idRutina, idEjercicio, sets, reps);
            adaptadorBD.cerrar();
        }
    }

    @Override
    public int getIdRutina(int posicion) {
        int resultado = 0;
        if(adaptadorBD.abrir() != null) {
            ArrayList<Rutina> rutinas = adaptadorBD.obtenerRutinas();
            resultado = rutinas.get(posicion).getId();
            adaptadorBD.cerrar();
        }
        return resultado;
    }

    @Override
    public void eliminarEjercicioRutina(int id) {
        if (adaptadorBD.abrir() != null){
            adaptadorBD.eliminarEjercicioRutina(id);
            adaptadorBD.cerrar();
        }
    }
}

