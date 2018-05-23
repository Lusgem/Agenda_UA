package fr.univ_angers.agenda_ua.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fr.univ_angers.agenda_ua.evenement.Evenement;

/**
 * Created by Thibault Condemine on 26/04/2018.
 */
public class DataSource {

    private SQLiteDatabase _database;
    private Tables _dbHelper;

    private String[] colonnesEvenement = {
            Tables.COLONNE_ID,
            Tables.COLONNE_PERSONNEL,
            Tables.COLONNE_LOCATION,
            Tables.COLONNE_MATIERE,
            Tables.COLONNE_GROUPE,
            Tables.COLONNE_SUMMARY,
            Tables.COLONNE_DATE_DEB,
            Tables.COLONNE_DATE_FIN,
            Tables.COLONNE_DATE_STAMP,
            Tables.COLONNE_REMARQUE
    };

    public DataSource(Context context){
        _dbHelper = new Tables(context);
    }

    public void open() throws SQLException{
        _database = _dbHelper.getWritableDatabase();
    }

    public void close(){
        _dbHelper.close();
    }

    public void createEvent(String personnel, String location, String matiere, String groupe, String summary, String dateDeb, String dateFin, String dateStamp, String remarque){
        // Créer un pseudo objet
        ContentValues values = new ContentValues();
        // Lui ajoute des valeurs sous forme de cle / attribut
        values.put(Tables.COLONNE_PERSONNEL, personnel);
        values.put(Tables.COLONNE_LOCATION, location);
        values.put(Tables.COLONNE_MATIERE, matiere);
        values.put(Tables.COLONNE_GROUPE, groupe);
        values.put(Tables.COLONNE_SUMMARY, summary);
        values.put(Tables.COLONNE_DATE_DEB, dateDeb);
        values.put(Tables.COLONNE_DATE_FIN, dateFin);
        values.put(Tables.COLONNE_DATE_STAMP, dateStamp);
        values.put(Tables.COLONNE_REMARQUE, remarque);

        _database.insert(Tables.TABLE_EVENEMENTS, null, values);
    }

    public void deleteEvenements(){
        _database.delete(Tables.TABLE_EVENEMENTS, null, null);
    }

    public ArrayList<Evenement> getAllEvenements(){
        ArrayList<Evenement> evenements = new ArrayList<>();
        String orderBy = Tables.COLONNE_DATE_DEB;
        Cursor cursor = _database.query(Tables.TABLE_EVENEMENTS, colonnesEvenement, null, null, null, null, orderBy);

        // Parcour du curseur !
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Evenement evenement = cursorEvenements(cursor);
            evenements.add(evenement);
            cursor.moveToNext();
        }

        // Fermeture du curseur !
        cursor.close();
        getMatieres();
        return evenements;
    }

    public ArrayList<String> getMatieres(){
        ArrayList<String> matieres = new ArrayList<>();
        Cursor cursor = _database.query(true, Tables.TABLE_EVENEMENTS, new String[]{Tables.COLONNE_MATIERE},null,null,null,null,null,null); //query(Tables.TABLE_EVENTS, Tables.COLUMN_MATIERE, null, null, null, null, null);

        // Parcour du curseur !
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String matiere = cursor.getString(0);
            if (matiere!=null)
            matieres.add(matiere);
            cursor.moveToNext();
        }

        // Fermeture du curseur !
        cursor.close();
        return matieres;
    }

    // Créer l'objet event après l'ajout dans la db ou au moment de renvoyer toute la liste
    private Evenement cursorEvenements(Cursor cursor){
        Evenement evenement = new Evenement();
        evenement.set_id(cursor.getLong(0));
        evenement.set_personnel(cursor.getString(1));
        evenement.set_location(cursor.getString(2));
        evenement.set_matiere(cursor.getString(3));
        evenement.set_groupe(cursor.getString(4));
        evenement.set_summary(cursor.getString(5));
        evenement.set_date_debut(cursor.getString(6));
        evenement.set_date_fin(cursor.getString(7));
        evenement.set_date_stamp(cursor.getString(8));
        evenement.set_remarque(cursor.getString(9));
        return evenement;
    }
}
