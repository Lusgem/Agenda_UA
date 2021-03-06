package fr.univ_angers.agenda_ua.classAbstraite;

import java.util.ArrayList;

import fr.univ_angers.agenda_ua.evenement.Evenement;
import fr.univ_angers.agenda_ua.evenement.EvenementExterieur;

/**
 * Created by Thibault Condemine on 12/05/2018.
 */

/**
 * Classe abstraite permettant d'avoir rapidement accès à certaines informations essentielles
 */

public abstract class GetEvents {
    public static ArrayList<EvenementExterieur> _eventsExterieur;
    public static ArrayList<String> _listeMatieres; // Liste des matières de la formation choisie
    public static ArrayList<Evenement> _eventsTraites; // Liste des evenements après traitements
    public static String _url;
}
