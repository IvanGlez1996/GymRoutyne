package pem.tema4.presentador;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

import pem.tema4.AppMediador;
import pem.tema4.modelo.IModelo;
import pem.tema4.modelo.Ejercicio;
import pem.tema4.modelo.Modelo;
import pem.tema4.modelo.Rutina;

public class PresentadorPrincipal implements IPresentadorPrincipal {


    // TODO Declarar una variable modelo para acceder al Modelo
    private IModelo modelo;

    public PresentadorPrincipal(){
        modelo = Modelo.getInstance();
    }

	// TODO Declarar e implementar el receptor broadcast que espera por la notificación del modelo. 
	// El modelo notificará cuando los datos de los teléfonos a notificar están disponibles.
    private BroadcastReceiver receptorDeAvisos = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equals(AppMediador.AVISO_DATOS_LISTOS)){
                ArrayList<Rutina> listaRutinas = (ArrayList<Rutina>) intent.getSerializableExtra(AppMediador.CLAVE_LISTA_RUTINAS);
                String[] datos = new String[listaRutinas.size()];
                for(int i = 0; i<listaRutinas.size(); i++){
                    datos[i] = listaRutinas.get(i).getNombre();
                }
                AppMediador.getInstance().getVistaPrincipal().actualizarMaestro(datos);
            }

            else if (intent.getAction().equals(AppMediador.AVISO_DETALLE_LISTO)){
                String[] datosDetalle = intent.getStringArrayExtra(AppMediador.CLAVE_DETALLE_RUTINAS);
                Object[] datos = new Object[2];
                datos[0] = datosDetalle[0];
                datos[1] = BitmapFactory.decodeFile(datosDetalle[1]);
                AppMediador.getInstance().getVistaPrincipal().actualizarDetalle(datos);
            }

            else if (intent.getAction().equals(AppMediador.AVISO_DATOS_ELIMINADOS)){
                ArrayList<Rutina> listaRutinas = (ArrayList<Rutina>) intent.getSerializableExtra(AppMediador.CLAVE_LISTA_RUTINAS);
                String[] datos = new String[listaRutinas.size()];
                for(int i = 0; i<listaRutinas.size(); i++){
                    datos[i] = listaRutinas.get(i).getNombre();
                }
                AppMediador.getInstance().getVistaPrincipal().actualizarMaestro(datos);
            }

            AppMediador.getInstance().unRegisterReceiver(this);
        }
    };
	// TODO Implementar un constructor que crea el modelo.

	// TODO Implementar el método obtenerRutinas() que registra el receptor para recibir notificaciones y
	// solicita al modelo que recupere los datos de la lista maestro.
    @Override
    public void obtenerRutinas() {
        AppMediador.getInstance().registerReceiver(receptorDeAvisos, AppMediador.AVISO_DATOS_LISTOS);
        modelo.obtenerRutinas();
    }


    // TODO Implementar el método obtenerEjerciciosRutinas(int posicion) que registra el receptor para recibir
	// notificaciones y solicita al modelo que recupere los datos de la lista detalle para una receta dada su posición.
    @Override
    public void obtenerEjercicios(int posicion) {
        AppMediador.getInstance().registerReceiver(receptorDeAvisos, AppMediador.AVISO_DETALLE_LISTO);
        modelo.obtenerEjerciciosRutinas(posicion);
    }


    // TODO Implementar el método tratarAgregar() que lanza la vista de agregación por medio del mediador.
    @Override
    public void tratarAgregar() {
        AppMediador appMediador = AppMediador.getInstance();
        appMediador.launchActivity(appMediador.getVistaParaAgregacion(), appMediador.getVistaPrincipal(), null);
    }

    @Override
    public void eliminarReceta(int posicion) {
        AppMediador.getInstance().registerReceiver(receptorDeAvisos, AppMediador.AVISO_DATOS_ELIMINADOS);
        modelo.eliminarRutina(posicion);
    }
}
