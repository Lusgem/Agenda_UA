package fr.univ_angers.agenda_ua.classAbstraite;

import java.util.ArrayList;

import fr.univ_angers.agenda_ua.evenement.Event;
import fr.univ_angers.agenda_ua.evenement.EventExterieur;

/**
 * Created by Thibault Condemine on 12/05/2018.
 */


public abstract class GetEvents {
    public static ArrayList<Event> _events;
    public static ArrayList<EventExterieur> _eventsExterieur;
    public static ArrayList<String> _listeMatieres; // Liste des matières de la formation choisie
    public static ArrayList<String> _listeMatieresAEnlever; //Liste des matières que l'étudiant ne veut pas afficher
}
