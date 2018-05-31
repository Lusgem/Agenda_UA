package fr.univ_angers.agenda_ua;

/**
 * Cette classe sert à stocké le choix de formation de l'utilisateur
 * Elle stocke le lien de téléchargement de l'emploi du temps (pour la synchronisation) et
 * l'intitulé de la formation
 */


public class Utilisateur {

    private String _lien;
    private String _formation;

    public Utilisateur(String lien, String formation) {
        this._lien = lien;
        this._formation = formation;
    }

    //GETTER
    public String get_lien() {
        return _lien;
    }

    public String get_formation() {
        return _formation;
    }

    //SETTER
    public void set_lien(String lien) {
        this._lien = lien;
    }

    public void set_formation(String formation) {
        this._formation = formation;
    }
}
