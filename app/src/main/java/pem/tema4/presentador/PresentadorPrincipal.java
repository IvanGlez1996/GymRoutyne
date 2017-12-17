package pem.tema4.presentador;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.HashMap;

import pem.tema4.AppMediador;
import pem.tema4.modelo.Ejercicio_rutina;
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
            }else if(intent.getAction().equals(AppMediador.AVISO_DATOS_AGREGADOS)){
                ArrayList<Rutina> listaRutinas = (ArrayList<Rutina>) intent.getSerializableExtra(AppMediador.CLAVE_LISTA_RUTINAS);
                String[] datos = new String[listaRutinas.size()];
                for(int i = 0; i<listaRutinas.size(); i++){
                    datos[i] = listaRutinas.get(i).getNombre();
                }
                AppMediador.getInstance().getVistaPrincipal().actualizarMaestro(datos);

            }

            else if (intent.getAction().equals(AppMediador.AVISO_DETALLE_LISTO)){
                ArrayList<Ejercicio_rutina> datos = intent.getParcelableArrayListExtra(AppMediador.CLAVE_DETALLE_RUTINAS);
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


    // TODO Implementar el método agregarRutina() que lanza la vista de agregación por medio del mediador.
    @Override
    public void agregarRutina(String nombre) {
        AppMediador.getInstance().registerReceiver(receptorDeAvisos, AppMediador.AVISO_DATOS_AGREGADOS);
        modelo.agregarRutina(nombre);
    }

    @Override
    public void eliminarRutina(int posicion) {
        AppMediador.getInstance().registerReceiver(receptorDeAvisos, AppMediador.AVISO_DATOS_ELIMINADOS);
        modelo.eliminarRutina(posicion);
    }

    @Override
    public ArrayList<Object[]> getEjercicios(){
        return modelo.getEjercicios();
    }

    @Override
    public void agregarEjercicioRutina(int idRutina, int idEjercicio, int sets, int reps) {
        modelo.agregarEjercicioRutina(idRutina, idEjercicio, sets, reps);
    }

    @Override
    public int getIdRutina(int posicion) {
        return modelo.getIdRutina(posicion);
    }

    @Override
    public void eliminarEjercicioRutina(int id) {
        modelo.eliminarEjercicioRutina(id);
    }
}
