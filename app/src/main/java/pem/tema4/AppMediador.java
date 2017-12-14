package pem.tema4;

import pem.tema4.presentador.IPresentadorAgregacion;
import pem.tema4.presentador.IPresentadorPrincipal;
import pem.tema4.presentador.PresentadorAgregacion;
import pem.tema4.presentador.PresentadorPrincipal;
import pem.tema4.vista.IVistaAgregacion;
import pem.tema4.vista.IVistaPrincipal;
import pem.tema4.vista.VistaAgregacion;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

@SuppressWarnings("rawtypes") 
public class AppMediador extends Application {
	private static AppMediador singleton;
	// variables correspondientes a los presentadores, vistas y modelo
	private IPresentadorPrincipal presentadorPrincipal;
	private IVistaPrincipal vistaPrincipal;
	private IPresentadorAgregacion presentadorAgregacion;
	private IVistaAgregacion vistaAgregacion;

	// constantes de comunicación, almacenamiento y petición
	public static final String CLAVE_LISTA_RUTINAS = "listaRutinas";
	public static final String AVISO_DATOS_LISTOS = "pem.tema4.AVISO_DATOS_LISTOS";	
	public static final String CLAVE_DETALLE_RUTINAS = "detalleRutina";
	public static final String AVISO_DETALLE_LISTO = "pem.tema4.AVISO_DETALLE_LISTO";
	public static final String AVISO_DATOS_AGREGADOS = "pem.tema4.AVISO_DATOS_AGREGADOS";
	public static final String AVISO_DATOS_ELIMINADOS = "pem.tema4.AVISO_DATOS_ELIMINADOS";
	 
	
	public static AppMediador getInstance(){
		return singleton;
	}

	// Métodos accessor de los presentadores, vistas y modelo	
	public IPresentadorPrincipal getPresentadorPrincipal() {
		if (presentadorPrincipal == null)
			presentadorPrincipal = new PresentadorPrincipal();
		return presentadorPrincipal;
	}
	
	public void removePresentadorPrincipal() {
		presentadorPrincipal = null;
	}
	
	public IVistaPrincipal getVistaPrincipal() {
		return vistaPrincipal;
	}

	public void setVistaPrincipal(IVistaPrincipal vistaPrincipal) {
		this.vistaPrincipal = vistaPrincipal;
	}

	public IPresentadorAgregacion getPresentadorAgregacion() {
		if (presentadorAgregacion == null)
			presentadorAgregacion = new PresentadorAgregacion();
		return presentadorAgregacion;
	}

	public void removePresentadorAgregacion() {
		presentadorAgregacion = null;
	}

	public IVistaAgregacion getVistaAgregacion() {
		return vistaAgregacion;
	}

	public void setVistaAgregacion(IVistaAgregacion vistaAgregacion) {
		this.vistaAgregacion = vistaAgregacion;
	}

	// Métodos destinados a la navegación en la aplicación y a la definición de servicios

		public Class getVistaParaAgregacion(){
			return VistaAgregacion.class;
		}

	// Métodos de manejo de los componentes de Android
	public void launchActivity(Class actividadInvocada, Object invocador, Bundle extras) {
		Intent i = new Intent(this, actividadInvocada);
		if (extras != null)
			i.putExtras(extras);	
		if (!invocador.getClass().equals(Activity.class)) 
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
	}
	
	public void launchActivityForResult(Class actividadInvocada, 
			Activity actividadInvocadora, int requestCode, Bundle extras) {
		Intent i = new Intent(actividadInvocadora, actividadInvocada);
		if (extras != null)
			i.putExtras(extras);
		actividadInvocadora.startActivityForResult(i, requestCode);
	}
	
	public void launchService(Class servicioInvocado, Bundle extras) {
		Intent i = new Intent(this, servicioInvocado);
		if (extras != null)
			i.putExtras(extras);
        startService(i);
	}
	
	public void stopService(Class servicioInvocado) {
		Intent i = new Intent(this, servicioInvocado);
        stopService(i);
	}
	
	public void registerReceiver(BroadcastReceiver receptor, String accion) {
		LocalBroadcastManager.getInstance(this).registerReceiver(receptor, new IntentFilter(accion));
	}	
	
	public void unRegisterReceiver(BroadcastReceiver receptor) {
		LocalBroadcastManager.getInstance(this).unregisterReceiver(receptor);
	}
	
	public void sendBroadcast(String accion, Bundle extras) {
		Intent intent = new Intent();
		intent.setAction(accion);
		if (extras != null)
			intent.putExtras(extras);
		LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		presentadorPrincipal = null;
		presentadorAgregacion = null;
		singleton = this;
	}
}
