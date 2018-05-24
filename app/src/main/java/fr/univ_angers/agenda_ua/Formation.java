package fr.univ_angers.agenda_ua;

import android.os.Parcel;
import android.os.Parcelable;

public class Formation implements Parcelable{

    private Long _id;
    private String _intitule;
    private String _lien;

    public Formation(Long id , String intitule, String lien) {
        _id = id;
        this._intitule = intitule;
        this._lien = lien;
    }

    protected Formation(Parcel in) {
        _intitule = in.readString();
        _lien = in.readString();
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
        if (obj instanceof Formation){
            Formation c = (Formation) obj;
            if (c.get_intitule().equals(_intitule) && c.get_lien().equals(_lien)) {
                return true;
            }
        }
        return false;
    }

    public static final Creator<Formation> CREATOR = new Creator<Formation>() {
        @Override
        public Formation createFromParcel(Parcel in) {
            return new Formation(in);
        }

        @Override
        public Formation[] newArray(int size) {
            return new Formation[size];
        }
    };

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