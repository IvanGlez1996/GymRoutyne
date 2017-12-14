package pem.tema4.presentador;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import pem.tema4.AppMediador;
import pem.tema4.modelo.Modelo;
import pem.tema4.vista.IVistaAgregacion;

public class PresentadorAgregacion implements IPresentadorAgregacion {
    private AppMediador appMediador;

    public PresentadorAgregacion(){
        appMediador = AppMediador.getInstance();
    }

    // TODO Declarar e implementar el receptor broadcast que espera por la notificación del modelo.
	// El modelo notificará cuando los datos de los teléfonos a notificar están disponibles.
    private BroadcastReceiver receptorDeAvisos = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(AppMediador.AVISO_DATOS_AGREGADOS)){
                ((Activity)appMediador.getVistaAgregacion()).finish();
            }
            appMediador.unRegisterReceiver(this);
        }
    };
	
	// TODO Implementar el método tratarGuardar() que registra el receptor para recibir notificaciones y 
	// solicita al modelo que almacene los datos que recupera de la vista. 
    @Override
    public void tratarGuardar() {
        IVistaAgregacion vistaAgregacion = appMediador.getVistaAgregacion();
        appMediador.registerReceiver(receptorDeAvisos, AppMediador.AVISO_DATOS_AGREGADOS);
        String[] datos = new String[3];
        datos[0] = vistaAgregacion.getNombre();
        datos[1] = vistaAgregacion.getIdentificador();
        datos[2] = vistaAgregacion.getAparato();
        Modelo.getInstance().agregarRutina(datos);
    }

}
