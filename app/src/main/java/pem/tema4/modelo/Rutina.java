package pem.tema4.modelo;

import java.io.Serializable;

/**
 * Created by IvanGlez on 13/12/2017.
 */

public class Rutina implements Serializable {

    private static final long serialVersionUID = 9209248968657407946L;

    private int id;
    private String nombre;

    public Rutina(int id, String nombre) {
        super();
        this.id = id;
        this.nombre = nombre;
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

}
