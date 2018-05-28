package fr.univ_angers.agenda_ua;

/**
 * Created by Thibault Condemine on 23/05/2018.
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
