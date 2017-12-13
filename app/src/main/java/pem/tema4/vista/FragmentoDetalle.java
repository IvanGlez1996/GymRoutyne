package pem.tema4.vista;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;

public class FragmentoDetalle extends Fragment {
	
	private TextView nombreReceta;
	private ImageView imagenDeReceta;
	private TextView infoReceta;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_fragmento_detalle,
				container, false); 
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		nombreReceta = (TextView) getView().findViewById(R.id.nombreReceta);
		imagenDeReceta = (ImageView) getView().findViewById(R.id.imagenDeReceta);
		infoReceta = (TextView) getView().findViewById(R.id.infoReceta);
	}
	
	public void actualizarNombreReceta(String nombre) {
		nombreReceta.setText(nombre);
	}

	public void actualizarImagenReceta(Bitmap imagen) {
		imagenDeReceta.setImageBitmap(imagen);
	}

	public void actualizarDescripcion(String descripcion) {
		infoReceta.setText(descripcion);
	}
	
}
