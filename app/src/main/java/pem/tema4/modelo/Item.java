package pem.tema4.modelo;

import java.io.Serializable;

public class Item implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -344029098860190065L;
	private String idReceta;
	private String nombreReceta;
	private String aparatoReceta;
	
	public Item(String idReceta, String nombreReceta, String aparatoReceta) {
		super();
		this.idReceta = idReceta;
		this.nombreReceta = nombreReceta;
		this.aparatoReceta = aparatoReceta;
	}

	public String getIdReceta() {
		return idReceta;
	}

	public void setIdReceta(String idReceta) {
		this.idReceta = idReceta;
	}

	public String getNombreReceta() {
		return nombreReceta;
	}

	public void setNombreReceta(String nombreReceta) {
		this.nombreReceta = nombreReceta;
	}

	public String getAparatoReceta() {
		return aparatoReceta;
	}

	public void setAparatoReceta(String aparatoReceta) {
		this.aparatoReceta = aparatoReceta;
	}
	

}
