package fr.univ_angers.agenda_ua;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class Groupes implements Parcelable{

    public Groupes(String _intitule, String _lien) {
        this._intitule = _intitule;
        this._lien = _lien;
    }

    protected Groupes(Parcel in) {
        _intitule = in.readString();
        _lien = in.readString();
    }

    public static final Creator<Groupes> CREATOR = new Creator<Groupes>() {
        @Override
        public Groupes createFromParcel(Parcel in) {
            return new Groupes(in);
        }

        @Override
        public Groupes[] newArray(int size) {
            return new Groupes[size];
        }
    };

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_intitule);
        dest.writeString(_lien);
    }
}
