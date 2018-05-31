package fr.univ_angers.agenda_ua;



public class Formation{

    private String _intitule;
    private String _lien;

    public Formation(String intitule, String lien) {
        this._intitule = intitule;
        this._lien = lien;
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


}
