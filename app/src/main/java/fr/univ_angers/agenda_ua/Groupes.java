package fr.univ_angers.agenda_ua;

<<<<<<< HEAD
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class Groupes implements Parcelable{
=======
public class Groupes {
>>>>>>> Re commit

    private String _intitule;
    private String _lien;

<<<<<<< HEAD
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
=======
    public Groupes(String intitule, String lien) {
        this._intitule = intitule;
        this._lien = lien;
>>>>>>> Re commit
    }

    //Getter
    public String get_intitule(){
        return _intitule;
    }
    public String get_lien() {
        return _lien;
    }

    //Setter
    public void set_intitule(String _intitule) {
        this._intitule = _intitule;
    }
    public void set_lien(String _lien) {
        this._lien = _lien;
    }

    @Override
    public String toString() {
        return _intitule;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Groupes){
            Groupes c = (Groupes) obj;
            if (c.get_intitule().equals(_intitule) && c.get_lien() == _lien ) {
                return true;
            }
        }
        return false;
    }
<<<<<<< HEAD


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
=======
>>>>>>> Re commit
}
