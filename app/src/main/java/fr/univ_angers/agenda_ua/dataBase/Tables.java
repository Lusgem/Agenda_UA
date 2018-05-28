package fr.univ_angers.agenda_ua.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Thibault Condemine on 26/04/2018.
 */
public class Tables extends SQLiteOpenHelper {

    // Version et nom de la base de données
    public static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;

    // Nom des tables
    public static final String TABLE_EVENEMENTS = "evenements";
    public static final String TABLE_FORMATIONS = "formations";
    public static final String TABLE_UTILISATEUR = "utilisateur";

    // Colonnes de la table evenements
    //public static final String COLONNE_ID = "_id";
    public static final String COLONNE_PERSONNEL = "personnel";
    public static final String COLONNE_LOCATION = "location";
    public static final String COLONNE_MATIERE = "matiere";
    public static final String COLONNE_GROUPE = "groupe";
    public static final String COLONNE_SUMMARY = "summary";
    public static final String COLONNE_DATE_DEB = "dateDeb";
    public static final String COLONNE_DATE_FIN = "dateFin";
    public static final String COLONNE_DATE_STAMP = "dateStamp";
    public static final String COLONNE_REMARQUE = "remarque";

    // Colonnes de la table formations
    //public static final String COLONNE_ID_FORMATION = "_id";
    public static final String COLONNE_FORMATION = "formation";
    public static final String COLONNE_LIEN = "lien";

    // Colonnes de la table utilisateur
    //public static final String COLONNE_ID_UTILISATEUR = "_id";
    public static final String COLONNE_LIEN_UTILISATEUR = "lien";
    public static final String COLONNE_FORMATION_UTILISATEUR = "formation";

    // Creation de la table evenements
    private static final String DATABASE_EVENEMENTS = "create table " + TABLE_EVENEMENTS + "("
            //+ COLONNE_ID + " integer primary key autoincrement, "
            + COLONNE_PERSONNEL + ", "
            + COLONNE_LOCATION + ", "
            + COLONNE_MATIERE + ", "
            + COLONNE_GROUPE + ", "
            + COLONNE_SUMMARY + ", "
            + COLONNE_DATE_DEB + ", "
            + COLONNE_DATE_FIN + ", "
            + COLONNE_DATE_STAMP + ", "
            + COLONNE_REMARQUE + ");";

    // Creation de la table formation
    private static final String DATABASE_FORMATIONS = "create table " + TABLE_FORMATIONS + "("
            //+ COLONNE_ID_FORMATION + " integer primary key autoincrement, "
            + COLONNE_FORMATION + ", "
            + COLONNE_LIEN + ");";

    // Creation de la table utilisateur
    private static final String DATABASE_UTILISATEUR = "create table " + TABLE_UTILISATEUR + "("
            //+ COLONNE_ID_UTILISATEUR + " integer primary key autoincrement, "
            + COLONNE_LIEN_UTILISATEUR + ", "
            + COLONNE_FORMATION_UTILISATEUR + ");";

    public Tables(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_EVENEMENTS);
        db.execSQL(DATABASE_FORMATIONS);
        db.execSQL(DATABASE_UTILISATEUR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(Tables.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old date");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENEMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FORMATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UTILISATEUR);
        onCreate(db);

    }
}
