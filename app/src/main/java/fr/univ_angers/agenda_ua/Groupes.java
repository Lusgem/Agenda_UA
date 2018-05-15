package fr.univ_angers.agenda_ua;

import java.util.Objects;

public class Groupes {

    public Groupes(String _intitule, String _lien) {
        this._intitule = _intitule;
        this._lien = _lien;
    }

    public String get_intitule() {
        return _intitule;
    }

    public void set_intitule(String _intitule) {
        this._intitule = _intitule;
    }

    public String get_lien() {
        return _lien;
    }

    public void set_lien(String _lien) {
        this._lien = _lien;
    }

    public String toString() {
        return _intitule;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Groupes){
            Groupes c = (Groupes) obj;
            if(c.get_intitule().equals(_intitule) && c.get_lien()==_lien ) return true;
        }

        return false;
    }


    private String _intitule;
    private String _lien;


}
