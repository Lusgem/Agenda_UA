package fr.univ_angers.agenda_ua.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;

import fr.univ_angers.agenda_ua.Formation;
import fr.univ_angers.agenda_ua.Utilisateur;
import fr.univ_angers.agenda_ua.evenement.Evenement;

/**
 * Created by Thibault Condemine on 26/04/2018.
 */

/**
 * Cette classe représente la base de données
 */
public class DataSource {

    private SQLiteDatabase _database;
    private Tables _dbHelper;

    private String[] colonnesEvenement = {
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

    private String[] colonnesFormation = {
            Tables.COLONNE_FORMATION,
            Tables.COLONNE_LIEN
    };

    private String[] colonnesUtilisateur = {
            Tables.COLONNE_LIEN_UTILISATEUR,
            Tables.COLONNE_FORMATION_UTILISATEUR
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

    /**
     * Création de la tables evenements
     * @param personnel
     * @param location
     * @param matiere
     * @param groupe
     * @param summary
     * @param dateDeb
     * @param dateFin
     * @param dateStamp
     * @param remarque
     */
    public void creationEvenement(String personnel, String location, String matiere, String groupe, String summary, String dateDeb, String dateFin, String dateStamp, String remarque){
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

    /**
     * Création de la table formation
     * @param formation
     * @param lien
     */
    public void creationFormation(String formation, String lien){
        ContentValues values = new ContentValues();
        values.put(Tables.COLONNE_FORMATION, formation);
        values.put(Tables.COLONNE_LIEN, lien);

        _database.insert(Tables.TABLE_FORMATIONS, null, values);
    }

    /**
     * Création de la table utilisateur
     * @param lien
     * @param formation
     */
    public void creationUtilisateur(String lien, String formation){
        ContentValues values = new ContentValues();
        values.put(Tables.COLONNE_LIEN_UTILISATEUR, lien);
        values.put(Tables.COLONNE_FORMATION_UTILISATEUR, formation);

        _database.insert(Tables.TABLE_UTILISATEUR, null, values);
    }

    public void updateUtilisateur(int id, String formation){
        ContentValues values = new ContentValues();
        values.put(Tables.COLONNE_FORMATION_UTILISATEUR, formation);

        _database.update(Tables.TABLE_UTILISATEUR, values, "rowid="+id, null);
    }

    public void supprimeEvenements(){
        _database.delete(Tables.TABLE_EVENEMENTS, null, null);
    }

    public void supprimeFormations(){
        _database.delete(Tables.TABLE_FORMATIONS, null, null);
    }

    public void supprimeUtilisateurs(){
        _database.delete(Tables.TABLE_UTILISATEUR, null, null);
    }

    public boolean evenementsVide(){
        String count = "SELECT count(*) FROM " + Tables.TABLE_EVENEMENTS;
        Cursor cursor = _database.rawQuery(count, null);
        cursor.moveToFirst();
        int icount = cursor.getInt(0);
        if(icount>0){
            return false;
        } else{
            return true;
        }
    }

    public boolean formationVide(){
        String count = "SELECT count(*) FROM " + Tables.TABLE_FORMATIONS;
        Cursor cursor = _database.rawQuery(count, null);
        cursor.moveToFirst();
        int icount = cursor.getInt(0);
        if(icount>0){
            return false;
        } else{
            return true;
        }
    }

    public boolean utilisateurVide(){
        String count = "SELECT count(*) FROM " + Tables.TABLE_UTILISATEUR;
        Cursor cursor = _database.rawQuery(count, null);
        cursor.moveToFirst();
        int icount = cursor.getInt(0);
        if(icount>0){
            return false;
        } else{
            return true;
        }
    }

    /**
     * Permet de récupérer la liste de tous les évenements
     * @return
     */
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
        return evenements;
    }

    /**
     * Permet de récupérer les intitulés de toutes les formations
     * @return
     */
    public ArrayList<Formation> getAllFormation(){
        ArrayList<Formation> formations = new ArrayList<>();
        String orderBy = Tables.COLONNE_FORMATION;
        Cursor cursor = _database.query(Tables.TABLE_FORMATIONS, colonnesFormation, null, null, null, null, orderBy);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Formation formation = cursorFormations(cursor);
            formations.add(formation);
            cursor.moveToNext();
        }

        cursor.close();
        return formations;
    }

    /**
     * Permet de récupérer les informations concernant l'utilisateur
     * @return
     */
    public ArrayList<Utilisateur> getAllUtilisateur(){
        ArrayList<Utilisateur> utilisateurs = new ArrayList<>();
        Cursor cursor = _database.query(Tables.TABLE_UTILISATEUR, colonnesUtilisateur, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Utilisateur utilisateur = cursorUtilisateur(cursor);
            utilisateurs.add(utilisateur);
            cursor.moveToNext();
        }

        cursor.close();
        return utilisateurs;
    }

    /**
     * Permet de récupérer la liste des matières
     * @return
     */
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
        evenement.set_personnel(cursor.getString(0));
        evenement.set_location(cursor.getString(1));
        evenement.set_matiere(cursor.getString(2));
        evenement.set_groupe(cursor.getString(3));
        evenement.set_summary(cursor.getString(4));
        evenement.set_date_debut(cursor.getString(5));
        evenement.set_date_fin(cursor.getString(6));
        evenement.set_date_stamp(cursor.getString(7));
        evenement.set_remarque(cursor.getString(8));
        return evenement;
    }

    private Formation cursorFormations(Cursor cursor){
        Formation formation = new Formation(cursor.getString(0), cursor.getString(1));
        return formation;
    }

    private Utilisateur cursorUtilisateur(Cursor cursor){
        Utilisateur utilisateur = new Utilisateur(cursor.getString(0), cursor.getString(1));
        return utilisateur;
    }
}
