package fr.univ_angers.agenda_ua.evenement;

import java.util.Date;

/**
 * Created by Thibault Condemine on 14/05/2018.
 */
public class EventExterieur {
    private String _titre;
    private String _lieu;
    private Date _debut;
    private Date _fin;


    public EventExterieur(String titre, String lieu, Date debut, Date fin) {
        this._titre = titre;
        this._lieu = lieu;
        this._debut = debut;
        this._fin = fin;
    }

    // GETTER

    public String get_titre() {
        return _titre;
    }
    public String get_lieu() {
        return _lieu;
    }
    public Date get_debut() {
        return _debut;
    }
    public Date get_fin() {
        return _fin;
    }

    // SETTER

    public void set_titre(String _titre) {
        this._titre = _titre;
    }
    public void set_lieu(String _lieu) {
        this._lieu = _lieu;
    }
    public void set_debut(Date _debut) {
        this._debut = _debut;
    }
    public void set_fin(Date _fin) {
        this._fin = _fin;
    }

    @Override
    public String toString() {
        return "Titre -> " + this._titre + " Lieu -> " + this._lieu +
                " Debut -> " + this._debut.getDate() + " - " + this._debut.getMonth() + " - " + (this._debut.getYear() + 1900) + " - " + this._debut.getHours() + ":" + this._debut.getMinutes() +
                " Fin -> " + this._fin.getDate() + " - " + this._fin.getMonth() + " - " + (this._debut.getYear() + 1900) + " - " + this._fin.getHours() + ":" + this._fin.getMinutes();
    }
}
