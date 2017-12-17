package pem.tema4.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by IvanGlez on 13/12/2017.
 */

public class AdaptadorBD {
    // las tablas de la base de datos deben usar como nombre de la clave primaria _id
    public static final String COLUMNA_ID = "_id";
    public static final String COLUMNA_NOMBRE = "nombre";
    public static final String COLUMNA_ID_RUTINA = "id_rutina";
    public static final String COLUMNA_ID_EJERCICIO = "id_ejercicio";
    public static final String COLUMNA_DESCRIPTION = "description";
    public static final String COLUMNA_VIDEO_URL = "video_url";
    public static final String COLUMNA_SETS = "sets";
    public static final String COLUMNA_REPS = "reps";


    private static final String NOMBRE_BD = "gymroutyne.db";
    private static final String NOMBRE_TABLA_RUTINAS = "rutinas";
    private static final String NOMBRE_TABLA_EJERCICIOS_RUTINAS = "ejercicios_rutinas";
    private static final String NOMBRE_TABLA_EJERCICIOS = "ejercicios";
    private static final int VERSION_BD = 1;
    private SQLiteDatabase db;
    private Context contexto;
    private DataBaseHelper dataBaseHelper;

    private static class DataBaseHelper extends SQLiteOpenHelper {

        DataBaseHelper(Context contexto) {
            super(contexto, NOMBRE_BD, null, VERSION_BD);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sql1 =  "CREATE TABLE IF NOT EXISTS "+NOMBRE_TABLA_EJERCICIOS+" ( " + COLUMNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMNA_NOMBRE + " TEXT, " + COLUMNA_DESCRIPTION + " TEXT, " + COLUMNA_VIDEO_URL +
                    " TEXT);";
            db.execSQL(sql1);

            String sql2 =  "CREATE TABLE IF NOT EXISTS "+NOMBRE_TABLA_RUTINAS+" ( " + COLUMNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMNA_NOMBRE + " TEXT);";
            db.execSQL(sql2);

            String sql3 =  "CREATE TABLE IF NOT EXISTS "+NOMBRE_TABLA_EJERCICIOS_RUTINAS+" ( " + COLUMNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMNA_ID_RUTINA + " INTEGER NOT NULL, " + COLUMNA_ID_EJERCICIO + " INTEGER NOT NULL, " + COLUMNA_SETS + " INTEGER," + COLUMNA_REPS +
                    " INTEGER,"
                    + " FOREIGN KEY ("+COLUMNA_ID_RUTINA+") REFERENCES "+NOMBRE_TABLA_RUTINAS+"("+COLUMNA_ID+"),"
                    + " FOREIGN KEY ("+COLUMNA_ID_EJERCICIO+") REFERENCES "+NOMBRE_TABLA_EJERCICIOS+"("+COLUMNA_ID+"));";
            db.execSQL(sql3);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int verionAnterior, int nuevaVersion) {
            String sql1 = "DROP TABLE IF EXISTS " + NOMBRE_TABLA_EJERCICIOS+";";
            db.execSQL(sql1);

            String sql2 = "DROP TABLE IF EXISTS " + NOMBRE_TABLA_RUTINAS+";";
            db.execSQL(sql2);

            String sql3 = "DROP TABLE IF EXISTS " + NOMBRE_TABLA_EJERCICIOS_RUTINAS+";";
            db.execSQL(sql3);

            onCreate(db);
        }
    }

    public AdaptadorBD(Context contexto) {
        this.contexto = contexto;
    }

    public AdaptadorBD abrir() {
        dataBaseHelper = new DataBaseHelper(contexto);
        db = dataBaseHelper.getWritableDatabase();
        if (db == null)
            return null;
        if (!hayRutinas()) {
            añadirRutina("Rutina 1");
            añadirRutina("Rutina 2");
            añadirEjercicio("Sentadillas", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum", "android.resource://pem.tema4.vista.recetas/drawable/pw_sentadillas");
            añadirEjercicio("Press de banca", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum", "android.resource://pem.tema4.vista.recetas/drawable/drawable/pw_press_banca");
            añadirEjercicio("Press militar", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", "android.resource://pem.tema4.vista.recetas/drawable/drawable/pw_press_militar");


            añadirEjercicioRutina(1, 1, 5, 14);
            añadirEjercicioRutina(1, 2, 3, 8);
            añadirEjercicioRutina(1, 3, 5, 11);


            añadirEjercicioRutina(2, 1, 2, 10);
            añadirEjercicioRutina(2, 2, 3, 8);
            añadirEjercicioRutina(2, 3, 5, 11);

        }
        return this;
    }


    public void cerrar() {
        dataBaseHelper.close();
        db.close();
    }

    private boolean hayRutinas() {
        String sql = "SELECT * FROM "+ NOMBRE_TABLA_RUTINAS+";";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() == 0)
            return false;
        return true;
    }

    public long añadirRutina(String nombre) {
        ContentValues valores = new ContentValues();
        valores.put(COLUMNA_NOMBRE, nombre);
        return db.insert(NOMBRE_TABLA_RUTINAS, null, valores);
    }

    private long añadirEjercicio(String nombre, String descripcion, String videoURL) {
        ContentValues valores = new ContentValues();
        valores.put(COLUMNA_NOMBRE, nombre);
        valores.put(COLUMNA_DESCRIPTION, descripcion);
        valores.put(COLUMNA_VIDEO_URL, videoURL);
        return db.insert(NOMBRE_TABLA_EJERCICIOS, null, valores);
    }

    public long añadirEjercicioRutina(int idRutina, int idEjercicio, int sets, int reps) {
        ContentValues valores = new ContentValues();
        valores.put(COLUMNA_ID_RUTINA, idRutina);
        valores.put(COLUMNA_ID_EJERCICIO, idEjercicio);
        valores.put(COLUMNA_SETS, sets);
        valores.put(COLUMNA_REPS, reps);
        return db.insert(NOMBRE_TABLA_EJERCICIOS_RUTINAS, null, valores);
    }

    /*public long borrarRegistro(double latitud, double longitud, String titulo) {
        // TODO Crear la cláusula WHERE que compruebe si la latitud, longitud y titulo están almacenados en un registro
        // único de la base de datos (para luego eliminarlo). Tener en cuenta que tanto la latitud como la longitud son
        // de tipo double, y por tanto, hay que comprobarlas usando un valor epsilon (=1e-3). Fijarse en la página
        // https://www.techonthenet.com/sqlite/between.php donde se explica la condición BETWEEN.
        String clausula = COLUMNA_LATITUD + " BETWEEN " + (latitud-1e-3) + " AND " + (latitud+1e-3) + " AND "
                + COLUMNA_LONGITUD + " BETWEEN " + (longitud-1e-3) + " AND " + (longitud+1e-3) +
                " AND " + COLUMNA_TITULO + " = '" + titulo + "'";
        return db.delete(NOMBRE_TABLA, clausula, null);
    }*/

    public ArrayList<Rutina> obtenerRutinas() {
        String sql = "SELECT * FROM "+ NOMBRE_TABLA_RUTINAS+";";
        Cursor cursor = db.rawQuery(sql, null);
        ArrayList<Rutina> lista = new ArrayList<Rutina>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String nombre = cursor.getString(1);
            lista.add(new Rutina(id, nombre));
        }
        cursor.close();
        return lista;
    }

    public String[] obtenerDetallesEjercicio(int id) {
        String sql = "SELECT * FROM "+ NOMBRE_TABLA_EJERCICIOS+" WHERE "+COLUMNA_ID + "=" + id +";";
        Cursor cursor = db.rawQuery(sql, null);
        String[] detalles = new String[3];
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            String nombre = cursor.getString(1);
            String descipcion = cursor.getString(2);
            String videoURL = cursor.getString(3);
            detalles[0] = nombre;
            detalles[1] = descipcion;
            detalles[2] = videoURL;
        }
        cursor.close();
        return detalles;
    }

    public ArrayList<Ejercicio_rutina> obtenerEjerciciosRutina(int id) {
        String sql = "SELECT * FROM "+ NOMBRE_TABLA_EJERCICIOS_RUTINAS+" WHERE "+COLUMNA_ID_RUTINA + "=" + id +";";
        Cursor cursor = db.rawQuery(sql, null);
        ArrayList<Ejercicio_rutina> lista = new ArrayList<Ejercicio_rutina>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            int id1 = cursor.getInt(0);
            int idRutina = cursor.getInt(1);
            int idEjercicio = cursor.getInt(2);
            int sets = cursor.getInt(3);
            int reps = cursor.getInt(4);
            lista.add(new Ejercicio_rutina(id1, idRutina, idEjercicio, sets, reps, obtenerNombreEjercicio(idEjercicio)));
        }
        cursor.close();
        return lista;
    }

    public String obtenerNombreEjercicio(int idEjercicio){
        String sql = "SELECT * FROM " + NOMBRE_TABLA_EJERCICIOS + " WHERE " + COLUMNA_ID + "=" + idEjercicio + ";";
        Cursor cursor = db.rawQuery(sql, null);
        String nombre = "";
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            nombre = cursor.getString(1);
        }
        cursor.close();
        return nombre;
    }

    public Integer obtenerIdEjercicioEjercicioRutina(int id){
        String sql = "SELECT * FROM " + NOMBRE_TABLA_EJERCICIOS_RUTINAS + " WHERE " + COLUMNA_ID + "=" + id + ";";
        Cursor cursor = db.rawQuery(sql, null);
        int idEjercicio = 0;
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            idEjercicio = cursor.getInt(2);
        }
        cursor.close();
        return idEjercicio;
    }

    public void eliminarRutina(int id){
        String sql = "DELETE FROM " + NOMBRE_TABLA_RUTINAS + " WHERE " + COLUMNA_ID + "=" + id + ";";
        db.execSQL(sql);
    }

    public void eliminarEjercicioRutina(int id){
        String sql = "DELETE FROM " + NOMBRE_TABLA_EJERCICIOS_RUTINAS + " WHERE " + COLUMNA_ID + "=" + id + ";";
        db.execSQL(sql);
    }

    public ArrayList<Object[]> getEjercicios(){
            String sql = "SELECT * FROM "+ NOMBRE_TABLA_EJERCICIOS+";";
            Cursor cursor = db.rawQuery(sql, null);
            ArrayList<Object[]> ejercicios = new ArrayList<>();
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                Object[] elementos = new Object[2];
                elementos[0] = cursor.getInt(0);
                elementos[1] = cursor.getString(1);
                ejercicios.add(elementos);
            }
            cursor.close();
            return ejercicios;
    }



}
