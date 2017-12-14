package pem.tema4.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ConjuntoDeRutinas {

	private static ConjuntoDeRutinas singleton = null;
	private ArrayList<Rutina> listaDeRutinas;
	
	private ConjuntoDeRutinas() {
		listaDeRutinas = new ArrayList<Rutina>();
		listaDeRutinas.add(new Rutina(1, "Hola"));

		
	}
	public static ConjuntoDeRutinas getInstance() {
		if (singleton == null)
			singleton = new ConjuntoDeRutinas();
		return singleton;
	}

	public ArrayList<Rutina> getListaDeRutinas() {
		return listaDeRutinas;
	}

	public void setListaDeRutinas(ArrayList<Rutina> listaDeRutinas) {
		this.listaDeRutinas = listaDeRutinas;
	}
	
	public void agregarItem(Rutina nuevo) {
		listaDeRutinas.add(nuevo);
		Collections.sort(listaDeRutinas, new Comparator<Rutina>() {
	        @Override
	        public int compare(Rutina rutina1, Rutina rutina2) {
	            return  rutina1.getNombre().compareTo(rutina2.getNombre());
	        }
	    });
	}
}


