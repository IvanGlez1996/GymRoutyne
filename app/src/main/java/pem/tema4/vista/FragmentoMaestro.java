package pem.tema4.vista;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentoMaestro extends Fragment implements Adaptador.SeleccionListener{

	private RecyclerView recyclerView;
	private EscuchaFragmento escucha;



	//@Override
	public void onClick(Adaptador.FilaViewHolder fvh, int posicion) {
		escucha.alSeleccionarItem(posicion);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_fragmento_maestro, container, false);
		recyclerView = (RecyclerView)v.findViewById(R.id.lista);

		return v;
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

	public void crearLista(String[] datos) {
		if (datos == null){
            return;
        }
		// crea un adaptador
		Adaptador adaptador = new Adaptador(datos, this);
		recyclerView.setAdapter(adaptador);
		if (datos!=null && datos.length !=0) {
            ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adaptador);
            ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
            touchHelper.attachToRecyclerView(recyclerView);
		}
	}

	public interface EscuchaFragmento {
		void alSeleccionarItem(int posicion);
	}



}
