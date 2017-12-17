package pem.tema4.modelo;

import java.io.Serializable;

public class Ejercicio implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -344029098860190065L;
	private int id;
	private String nombre;
	private String imagenURL;
	private String descripcion;
	
	public Ejercicio(int id, String nombre, String descripcion, String imagenURL) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.imagenURL = imagenURL;
		this.descripcion = descripcion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getImagenURL(){
		return this.imagenURL;
	}

	public String getDescripcion(){
		return this.descripcion;
	}
	

}
