package DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//Clase responsable de crear la base de datos y tablas
public class BaseDAO extends SQLiteOpenHelper {

    public static final String TABLA_EVENTOS = "eventos";
    public static final String EVENTO_ID = "_id";
    public static final String EVENTO_NOMBRE = "nom_evento";
    public static final String EVENTO_LUGAR = "lugar";
    public static final String EVENTO_FECHA = "fecha";

    private static final String DATABASE_NAME = "evento.db";
    private static final int DATABASE_VERSION = 1;

    //Evento estructura de la tabla (SQL)
    private static final String CREATE_EVENTO = "create table " +
            TABLA_EVENTOS + "( " + EVENTO_ID       + " integer primary key autoincrement, " +
            EVENTO_NOMBRE     + " text not null, " +
            EVENTO_LUGAR + " text not null, " +
            EVENTO_FECHA + " text not null);";

    public BaseDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        //creación de la tabla
        database.execSQL(CREATE_EVENTO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Si es necesario cambiar la estructura de la tabla
        // Primero debe eliminar la tabla y luego volver a crearlo
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_EVENTOS);
        onCreate(db);
    }

}