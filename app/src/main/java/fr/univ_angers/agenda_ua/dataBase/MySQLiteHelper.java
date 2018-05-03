package fr.univ_angers.agenda_ua.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Thibault Condemine on 26/04/2018.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    // Nom de ma table
    public static final String TABLE_EVENTS = "events";

    // Nom des colonnes
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PERSONNEL = "personnel";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_MATIERE = "matiere";
    public static final String COLUMN_GROUPE = "groupe";
    public static final String COLUMN_SUMMARY = "summary";
    public static final String COLUMN_DATE_DEB = "dateDeb";
    public static final String COLUMN_DATE_FIN = "dateFin";
    public static final String COLUMN_DATE_STAMP = "dateStamp";
    public static final String COLUMN_REMARQUE = "remarque";

    private static final String DATABASE_NAME = "events.db";
    private static final int DATABASE_VERSION = 1;

    // Commande sql pour la création de la base de données
    private static final String DATABASE_CREATE = "create table " + TABLE_EVENTS + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_PERSONNEL + ", "
            + COLUMN_LOCATION + ", "
            + COLUMN_MATIERE + ", "
            + COLUMN_GROUPE + ", "
            + COLUMN_SUMMARY + ", "
            + COLUMN_DATE_DEB + ", "
            + COLUMN_DATE_FIN + ", "
            + COLUMN_DATE_STAMP + ", "
            + COLUMN_REMARQUE + ");";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old date");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        onCreate(db);
    }
}
