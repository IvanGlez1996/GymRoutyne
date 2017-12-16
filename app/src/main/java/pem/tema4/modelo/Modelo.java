package pem.tema4.modelo;

import android.os.Bundle;
import android.os.Environment;

import java.util.ArrayList;

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
            ArrayList<Ejercicio_rutina> ejercicio_rutinas = new ArrayList<>();
            ejercicio_rutinas = adaptadorBD.obtenerEjerciciosRutina(idRutina);
            Bundle extras = new Bundle();
            extras.putParcelableArrayList(AppMediador.CLAVE_DETALLE_RUTINAS, ejercicio_rutinas);
            AppMediador.getInstance().sendBroadcast(AppMediador.AVISO_DETALLE_LISTO, extras);
            adaptadorBD.cerrar();
        }
    }



    @Override
    public void agregarRutina(Object[] datos) {
        //conjuntoDeRutinas.agregarItem(new Ejercicio((String)datos[1], (String)datos[0], (String)datos[2]));
        AppMediador.getInstance().sendBroadcast(AppMediador.AVISO_DATOS_AGREGADOS, null);
    }

    @Override
    public void eliminarRutina(int posicion) {
        ConjuntoDeRutinas conjuntoDeRutinas = ConjuntoDeRutinas.getInstance();
        conjuntoDeRutinas.getListaDeRutinas().remove(posicion);
        Bundle extras = new Bundle();
        extras.putSerializable(AppMediador.CLAVE_LISTA_RUTINAS, conjuntoDeRutinas.getListaDeRutinas());
        AppMediador.getInstance().sendBroadcast(AppMediador.AVISO_DATOS_ELIMINADOS, extras);
    }
}

