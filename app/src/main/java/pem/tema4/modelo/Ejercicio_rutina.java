package pem.tema4.modelo;

import java.io.Serializable;

/**
 * Created by IvanGlez on 13/12/2017.
 */

public class Ejercicio_rutina implements Serializable{

    private static final long serialVersionUID = 6471945105247696351L;
    private int id;
    private int idRutina;
    private int idEjercicio;
    private int sets;
    private int reps;

    public Ejercicio_rutina (int id, int idRutina, int idEjercicio, int sets, int reps){
        this.id = id;
        this.idRutina = idRutina;
        this.idEjercicio = idEjercicio;
        this.sets = sets;
        this.reps = reps;
    }

    public int getId(){
        return this.id;
    }

    public int getIdRutina(){
        return this.idRutina;
    }

    public void setIdRutina(int idRutina){
        this.idRutina = idRutina;
    }

    public int getIdEjercicio(){
        return this.idEjercicio;
    }

    public void setIdEjercicio(int idEjercicio){
        this.idEjercicio = idEjercicio;
    }

    public int getSets(){
        return this.sets;
    }

    public void setSets(int sets){
        this.sets = sets;
    }

    public int getReps(){
        return this.reps;
    }

    public void setReps(int reps){
        this.reps = reps;
    }
}
