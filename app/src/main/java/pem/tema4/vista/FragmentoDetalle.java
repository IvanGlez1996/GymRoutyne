package pem.tema4.vista;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import pem.tema4.modelo.Ejercicio_rutina;

public class FragmentoDetalle extends Fragment implements AdaptadorDetalle.SeleccionListener{

	private RecyclerView recyclerView;
	private EscuchaFragmento escucha;



	//@Override
	public void onClick(AdaptadorDetalle.FilaViewHolder fvh, int posicion) {
		escucha.alSeleccionarItem(posicion);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_fragmento_detalle,
				container, false);

		recyclerView = (RecyclerView)v.findViewById(R.id.lista_detalle);

		return v;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}


	@Override
	public void onAttach(Context contexto) {
		super.onAttach(contexto);
		if (contexto instanceof EscuchaFragmento) {
			escucha = (EscuchaFragmento) contexto;
		} else {
			throw new RuntimeException(contexto.toString()
					+ " debes implementar EscuchaFragmento");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		escucha = null;
	}

	public void crearLista(ArrayList<Ejercicio_rutina> datos) {
		if (datos == null){
			return;
		}
		// crea un adaptador
		AdaptadorDetalle adaptador = new AdaptadorDetalle(datos, this);
		recyclerView.setAdapter(adaptador);
		if (datos!=null && datos.size() !=0) {
			ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adaptador);
			ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
			touchHelper.attachToRecyclerView(recyclerView);
		}
	}


	public interface EscuchaFragmento {
		void alSeleccionarItem(int posicion);
	}
	
}
