package fr.univ_angers.agenda_ua;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Event {
    private String _personnel; // Dans description
    private String _location;
    private String _matiere; // Dans description
    private String _groupe; // Dans description
    private String _summary; // Exemple : TP - Programmation orientée objet en C++
    private Date _date_debut;
    private Date _date_fin;
    private String _description;
    private Date _date_stamp;
    private String _remarque; // Dans description

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


    public Date get_date_debut() {
        return _date_debut;
    }

    public void set_date_debut(Date _date_debut) {
        this._date_debut = _date_debut;
    }

    public String get_personnel() {
        return _personnel;
    }

    public void set_personnel(String _personnel) {
        this._personnel = _personnel;
    }

    public String get_location() {
        return _location;
    }

    public void set_location(String _location) {
        this._location = _location;
    }

    public String get_matiere() {
        return _matiere;
    }

    public void set_matiere(String _matiere) {
        this._matiere = _matiere;
    }

    public String get_groupe() {
        return _groupe;
    }

    public void set_groupe(String _groupe) {
        this._groupe = _groupe;
    }

    public String get_summary() {
        return _summary;
    }

    public void set_summary(String _summary) {
        this._summary = _summary;
    }

    public Date get_date_fin() {
        return _date_fin;
    }

    public void set_date_fin(Date _date_fin) {
        this._date_fin = _date_fin;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }


    public Date get_date_stamp() {
        return _date_stamp;
    }

    public void set_date_stamp(Date _date_stamp) {
        this._date_stamp = _date_stamp;
    }

    public String to_string(){
        return "Date debut : "+_date_debut+"\nDate fin : "+_date_fin+
                "\nDescription :\n"+_description;
    }

    public String get_remarque() {
        return _remarque;
    }

    public void set_remarque(String _remarque) {
        this._remarque = _remarque;
    }
}
