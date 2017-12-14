package pem.tema4.vista;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentoDetalle extends Fragment {
	
	private TextView nombre;
	private ImageView imagenDeReceta;
	private TextView sets;
	private TextView reps;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_fragmento_detalle,
				container, false); 
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		nombre = (TextView) getView().findViewById(R.id.idRutina);
		imagenDeReceta = (ImageView) getView().findViewById(R.id.imagenDeReceta);
		sets = (TextView) getView().findViewById(R.id.infoReceta);
	}
	
	public void actualizarNombreReceta(String nombre) {
		this.nombre.setText(nombre);
	}

	public void actualizarImagenReceta(Bitmap imagen) {
		imagenDeReceta.setImageBitmap(imagen);
	}

	public void actualizarDescripcion(String descripcion) {
		sets.setText(descripcion);
	}
	
}
