package pem.tema4.presentador;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

import pem.tema4.AppMediador;
import pem.tema4.modelo.IModelo;
import pem.tema4.modelo.Item;
import pem.tema4.modelo.Modelo;

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
                ArrayList<Item> infoRecetas = (ArrayList<Item>) intent.getSerializableExtra(AppMediador.CLAVE_LISTA_RECETAS);
                String[] datos = new String[infoRecetas.size()];
                for(int i = 0; i<infoRecetas.size(); i++){
                    datos[i] = infoRecetas.get(i).getNombreReceta();
                }
                AppMediador.getInstance().getVistaPrincipal().actualizarMaestro(datos);
            }

            else if (intent.getAction().equals(AppMediador.AVISO_DETALLE_LISTO)){
                String[] datosDetalle = intent.getStringArrayExtra(AppMediador.CLAVE_DETALLE_RECETA);
                Object[] datos = new Object[3];
                datos[0] = datosDetalle[0] + "(" + datosDetalle[1] + ")";
                datos[1] = BitmapFactory.decodeFile(datosDetalle[2]);
                datos[2] = datosDetalle[3];
                AppMediador.getInstance().getVistaPrincipal().actualizarDetalle(datos);
            }

            else if (intent.getAction().equals(AppMediador.AVISO_DATOS_ELIMINADOS)){
                ArrayList<Item> infoRecetas = (ArrayList<Item>) intent.getSerializableExtra(AppMediador.CLAVE_LISTA_RECETAS);
                String[] datos = new String[infoRecetas.size()];
                for(int i = 0; i<infoRecetas.size(); i++){
                    datos[i] = infoRecetas.get(i).getNombreReceta();
                }
                AppMediador.getInstance().getVistaPrincipal().actualizarMaestro(datos);
            }

            AppMediador.getInstance().unRegisterReceiver(this);
        }
    };
	// TODO Implementar un constructor que crea el modelo.

	// TODO Implementar el método obtenerDatos() que registra el receptor para recibir notificaciones y 
	// solicita al modelo que recupere los datos de la lista maestro.
    @Override
    public void obtenerDatos() {
        AppMediador.getInstance().registerReceiver(receptorDeAvisos, AppMediador.AVISO_DATOS_LISTOS);
        modelo.obtenerDatos();
    }


    // TODO Implementar el método obtenerDetalle(int posicion) que registra el receptor para recibir
	// notificaciones y solicita al modelo que recupere los datos de la lista detalle para una receta dada su posición.
    @Override
    public void obtenerDetalle(int posicion) {
        AppMediador.getInstance().registerReceiver(receptorDeAvisos, AppMediador.AVISO_DETALLE_LISTO);
        modelo.obtenerDetalle(posicion);
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
        modelo.eliminarReceta(posicion);
    }
}
