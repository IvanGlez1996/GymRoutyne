package pem.tema4.modelo;

import android.os.Bundle;
import android.os.Environment;

import pem.tema4.AppMediador;

public class Modelo implements IModelo {

    private static Modelo singleton = null;
    private ConjuntoDeRecetas conjuntoDeRecetas;

    private Modelo() {
        conjuntoDeRecetas =	ConjuntoDeRecetas.getInstance();
    }

    public static Modelo getInstance() {
        if (singleton == null)
            singleton = new Modelo();
        return singleton;
    }



    // TODO Implementar el método obtenerDatos() que recupera los datos de la lista de recetas del
	// conjunto de recetas y envia una notificación del tipo AVISO_DATOS_LISTOS al presentador.
    @Override
    public void obtenerDatos() {
        Bundle extras = new Bundle();
        extras.putSerializable(AppMediador.CLAVE_LISTA_RECETAS, conjuntoDeRecetas.getListaDeRecetas());
        AppMediador.getInstance().sendBroadcast(AppMediador.AVISO_DATOS_LISTOS, extras);
    }


    // TODO Implementar el método obtenerDetalle(int posicion) que recupera los datos del detalle de una receta del
	// conjunto de recetas y envia una notificación del tipo AVISO_DETALLE_LISTO al presentador.
    @Override
    public void obtenerDetalle(int posicion) {
        Item receta = conjuntoDeRecetas.getListaDeRecetas().get(posicion);
        String[] datos = new String[4];
        datos[0] = receta.getNombreReceta();
        datos[1] = receta.getAparatoReceta();
        datos[2] = Environment.getExternalStorageDirectory().getAbsolutePath() + "/imagenes/" + receta.getIdReceta() + ".png";
        datos[3] = AccesoArchivo.leerReceta(receta.getIdReceta() + ".txt");
        Bundle extras = new Bundle();
        extras.putStringArray(AppMediador.CLAVE_DETALLE_RECETA, datos);
        AppMediador.getInstance().sendBroadcast(AppMediador.AVISO_DETALLE_LISTO, extras);
    }


    // TODO Añadir el método agregarReceta(Object[] datos) que almacena una nueva receta en la lista
	// de recetas. En la posición 0 se almacena el nombre del archivo de imagen, en la posición 1 se
	// almacena el nombre de la receta y en la posición 2 se almacena la descripción de la receta.
    @Override
    public void agregarReceta(Object[] datos) {
        conjuntoDeRecetas.agregarItem(new Item((String)datos[1], (String)datos[0], (String)datos[2]));
        AppMediador.getInstance().sendBroadcast(AppMediador.AVISO_DATOS_AGREGADOS, null);
    }

    @Override
    public void eliminarReceta(int posicion) {
        ConjuntoDeRecetas conjuntoDeRecetas = ConjuntoDeRecetas.getInstance();
        conjuntoDeRecetas.getListaDeRecetas().remove(posicion);
        Bundle extras = new Bundle();
        extras.putSerializable(AppMediador.CLAVE_LISTA_RECETAS, conjuntoDeRecetas.getListaDeRecetas());
        AppMediador.getInstance().sendBroadcast(AppMediador.AVISO_DATOS_ELIMINADOS, extras);
    }
}

