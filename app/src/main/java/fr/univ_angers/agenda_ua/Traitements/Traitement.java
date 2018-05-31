package fr.univ_angers.agenda_ua.Traitements;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.ListIterator;

import fr.univ_angers.agenda_ua.Utilisateur;
import fr.univ_angers.agenda_ua.classAbstraite.GetEvents;
import fr.univ_angers.agenda_ua.dataBase.DataSource;
import fr.univ_angers.agenda_ua.evenement.Evenement;

/**
 * La classe traitement est une classe abstraite permettant de lancer des traitements comme
 * le traitement des matières.
 */

public abstract class Traitement {
    /**
     * Ce traitement filtre la liste des évenements en enelevant les matières déselectionnées par l'utilisateur
     * @param dataSource (base de données permettant de récupérer les évenements et les matières à
     *                   enlever
     */
    public static void TraitementMatiere(DataSource dataSource){
        ArrayList<Evenement> _eventsTraites = dataSource.getAllEvenements();
        ArrayList<String> finalOutputString = new ArrayList<>();

        if (!dataSource.utilisateurVide()) {
            Gson gson = new Gson();
            ArrayList<Utilisateur> uti = dataSource.getAllUtilisateur();
            String form = uti.get(0).get_formation();
            Type type = new TypeToken<ArrayList<String>>() {}.getType();
            finalOutputString = gson.fromJson(form, type);
        }
        if(finalOutputString == null){
            System.out.println("Pas de traitement");
        }
        else{
            for(String matiere : finalOutputString){
                ListIterator<Evenement> iterator = _eventsTraites.listIterator();
                while(iterator.hasNext()){
                    Evenement e = (Evenement) iterator.next();
                    if(e.get_matiere()!=null && e.get_matiere().equalsIgnoreCase(matiere)){
                        iterator.remove();
                    }
                }
            }
        }
        GetEvents._eventsTraites = _eventsTraites;

    }





}
