package pem.tema4.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by IvanGlez on 13/12/2017.
 */

public class Ejercicio_rutina implements Serializable, Parcelable{

    private static final long serialVersionUID = 6471945105247696351L;
    private int id;
    private int idRutina;
    private int idEjercicio;
    private int sets;
    private int reps;
    private String nombre;

    public Ejercicio_rutina (int id, int idRutina, int idEjercicio, int sets, int reps, String nombre){
        this.id = id;
        this.idRutina = idRutina;
        this.idEjercicio = idEjercicio;
        this.sets = sets;
        this.reps = reps;
        this.nombre = nombre;
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

    public String getNombre(){
        return this.nombre;
    }

    // Parcelling part
    public Ejercicio_rutina(Parcel in){
        String[] data = new String[5];

        in.readStringArray(data);
        // the order needs to be the same as in writeToParcel() method
        this.id = Integer.parseInt(data[0]);
        this.idRutina = Integer.parseInt(data[1]);
        this.idEjercicio = Integer.parseInt(data[2]);
        this.sets = Integer.parseInt(data[3]);
        this.reps = Integer.parseInt(data[4]);
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {String.valueOf(this.id),
                String.valueOf(this.idRutina),
                String.valueOf(this.idEjercicio), String.valueOf(this.sets), String.valueOf(this.reps)});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Ejercicio_rutina createFromParcel(Parcel in) {
            return new Ejercicio_rutina(in);
        }

        public Ejercicio_rutina[] newArray(int size) {
            return new Ejercicio_rutina[size];
        }
    };
}
