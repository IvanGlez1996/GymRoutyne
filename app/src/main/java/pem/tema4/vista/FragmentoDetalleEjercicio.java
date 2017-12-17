package pem.tema4.vista;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentoDetalleEjercicio extends Fragment {
	
	private TextView nombreEjercicio;
	private ImageView imagenEjercicio;
	private TextView descripcionEjercicio;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_fragmento_detalle_ejercicio,
				container, false); 
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		nombreEjercicio = (TextView) getView().findViewById(R.id.nombre_ejercicio_detalle);
		imagenEjercicio = (ImageView) getView().findViewById(R.id.imagen_ejercicio);
		descripcionEjercicio = (TextView) getView().findViewById(R.id.descripcion_ejercicio);
	}
	
	public void actualizarNombreEjercicio(String nombre) {
		nombreEjercicio.setText(nombre);
	}

	public void actualizarImagenEjercicio(String uri) {
		imagenEjercicio.setImageURI(Uri.parse(uri));
	}

	public void actualizarDescripcionEjercicio(String descripcion) {
		descripcionEjercicio.setText(descripcion);
	}
	
}
