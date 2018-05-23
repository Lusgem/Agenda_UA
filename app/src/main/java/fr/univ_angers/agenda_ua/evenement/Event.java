package fr.univ_angers.agenda_ua.evenement;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Event {

    private Long _id; // id pour la base de donnée
    private String _personnel; // Dans la description
    private String _location;
    private String _matiere; // Dans la description
    private String _groupe; // Dans la description
    private String _summary; // Exemple : TP - Programmation orientée objet en C++
    private String _date_debut;
    private String _date_fin;
    private String _date_stamp;
    private String _remarque; // Dans la description

    private int _jour_debut;
    private int _mois_debut;
    private int _annee_debut;
    private int _jour_fin;
    private int _mois_fin;
    private int _annee_fin;
    private int _heure_debut;
    private int _heure_fin;
    private int _minute_debut;
    private int _minute_fin;

    // GETTER
    public Long get_id() {
        return _id;
    }
    public String get_personnel() {
        return _personnel;
    }
    public String get_location() {
        return _location;
    }
    public String get_matiere() {
        return _matiere;
    }
    public String get_groupe() {
        return _groupe;
    }
    public String get_summary() {
        return _summary;
    }
    public String get_date_debut() {
        return _date_debut;
    }
    public String get_date_fin() {
        return _date_fin;
    }
    public String get_date_stamp() {
        return _date_stamp;
    }
    public String get_remarque() {
        return _remarque;
    }
    public int get_minute_fin() {
        return _minute_fin;
    }
    public int get_minute_debut() {
        return _minute_debut;
    }
    public int get_heure_fin() {
        return _heure_fin;
    }
    public int get_heure_debut() {
        return _heure_debut;
    }
    public int get_annee_fin() {
        return _annee_fin;
    }
    public int get_annee_debut() {
        return _annee_debut;
    }
    public int get_mois_fin() {
        return _mois_fin;
    }
    public int get_mois_debut() {
        return _mois_debut;
    }
    public int get_jour_fin() {
        return _jour_fin;
    }
    public int get_jour_debut() {
        return _jour_debut;
    }


    // SETTER
    public void set_id(Long _id) {
        this._id = _id;
    }
    public void set_personnel(String _personnel) {
        this._personnel = _personnel;
    }
    public void set_location(String _location) {
        this._location = _location;
    }
    public void set_matiere(String _matiere) {
        this._matiere = _matiere;
    }
    public void set_groupe(String _groupe) {
        this._groupe = _groupe;
    }
    public void set_summary(String _summary) {
        this._summary = _summary;
    }
    public void set_mois_debut(int _mois_debut) {
        this._mois_debut = _mois_debut;
    }
    public void set_annee_debut(int _annee_debut) {
        this._annee_debut = _annee_debut;
    }
    public void set_jour_fin(int _jour_fin) {
        this._jour_fin = _jour_fin;
    }
    public void set_mois_fin(int _mois_fin) {
        this._mois_fin = _mois_fin;
    }
    public void set_annee_fin(int _annee_fin) {
        this._annee_fin = _annee_fin;
    }
    public void set_heure_debut(int _heure_debut) {
        this._heure_debut = _heure_debut;
    }
    public void set_heure_fin(int _heure_fin) {
        this._heure_fin = _heure_fin;
    }
    public void set_minute_debut(int _minute_debut) {
        this._minute_debut = _minute_debut;
    }
    public void set_minute_fin(int _minute_fin) {
        this._minute_fin = _minute_fin;
    }
    public void set_jour_debut(int _jour_debut) {
        this._jour_debut = _jour_debut;
    }
    public void set_date_stamp(String _date_stamp) {
        this._date_stamp = _date_stamp;
    }
    public void set_remarque(String _remarque) {
        this._remarque = _remarque;
    }

    public void set_date_debut(String date_debut) {
        Date _date = convert_date(date_debut);
        _jour_debut = _date.getDate();
        _mois_debut = _date.getMonth();
        _annee_debut = _date.getYear() + 1900;
        _heure_debut = _date.getHours();
        _minute_debut = _date.getMinutes();
        _date_debut = date_debut;
    }
    public void set_date_fin(String date_fin) {
        Date _date = convert_date(date_fin);
        _jour_fin = _date.getDate();
        _mois_fin = _date.getMonth();
        _annee_fin = _date.getYear() + 1900;
        _heure_fin = _date.getHours();
        _minute_fin = _date.getMinutes();
        _date_fin = date_fin;
    }

    public static Date convert_date(String _date){
        DateFormat utcFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
        utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date date = null;
        try {
            date = utcFormat.parse(_date);
            DateFormat frFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            frFormat.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));
            Date __date = frFormat.parse(frFormat.format(date));
            return __date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    @Override
    public String toString() {
        return _heure_debut+"h"+_minute_debut+" - "+_heure_fin+"h"+_minute_fin+"\n"+_summary+"\n"+_remarque;
    }
}
