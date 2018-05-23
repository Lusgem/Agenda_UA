package fr.univ_angers.agenda_ua.classAbstraite;

import java.util.ArrayList;

import fr.univ_angers.agenda_ua.evenement.Evenement;
import fr.univ_angers.agenda_ua.evenement.EvenementExterieur;

/**
 * Created by Thibault Condemine on 12/05/2018.
 */


public abstract class GetEvents {
    public static ArrayList<Evenement> _evenements;
    public static ArrayList<EvenementExterieur> _eventsExterieur;
    public static ArrayList<String> _listeMatieres; // Liste des matières de la formation choisie
    public static ArrayList<String> _listeMatieresAEnlever; //Liste des matières que l'étudiant ne veut pas afficher
    public static ArrayList<Evenement> _eventsTraites; // Liste des evenements après traitements
    public static String _url;
}
