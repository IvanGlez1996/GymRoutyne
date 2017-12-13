package pem.tema4.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ConjuntoDeRecetas {

	private static ConjuntoDeRecetas singleton = null;
	private ArrayList<Item> listaDeRecetas;
	
	private ConjuntoDeRecetas() {
		listaDeRecetas = new ArrayList<Item>();
		listaDeRecetas.add(new Item("calamares","Calamares encebollados","Thermomix"));
		listaDeRecetas.add(new Item("pollo","Pollo a la huertana","Thermomix"));
		listaDeRecetas.add(new Item("sopa","Sopa de pollo","Vitro"));
		
	}
	public static ConjuntoDeRecetas getInstance() {
		if (singleton == null)
			singleton = new ConjuntoDeRecetas();
		return singleton;
	}

	public ArrayList<Item> getListaDeRecetas() {
		return listaDeRecetas;
	}

	public void setListaDeRecetas(ArrayList<Item> listaDeRecetas) {
		this.listaDeRecetas = listaDeRecetas;
	}
	
	public void agregarItem(Item nuevo) {
		listaDeRecetas.add(nuevo);
		Collections.sort(listaDeRecetas, new Comparator<Item>() {
	        @Override
	        public int compare(Item item1, Item item2) {
	            return  item1.getNombreReceta().compareTo(item2.getNombreReceta());
	        }
	    });
	}
}


