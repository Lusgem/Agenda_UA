package fr.univ_angers.agenda_ua.Traitements;

import java.util.ArrayList;

import fr.univ_angers.agenda_ua.classAbstraite.GetEvents;
import fr.univ_angers.agenda_ua.evenement.Event;

public abstract class Traitement {
    public static void TraitementMatiere(){
        ArrayList<Event> _eventsTraites = (ArrayList<Event>)GetEvents._events.clone();
        if(GetEvents._listeMatieresAEnlever.isEmpty()){
            System.out.println("Pas de traitement");
        }
        else{
            for(String matiere : GetEvents._listeMatieresAEnlever){
                for(int i=0;i<_eventsTraites.size();i++){
                    if(_eventsTraites.get(i).get_matiere()!=null && _eventsTraites.get(i).get_matiere().equalsIgnoreCase(matiere)){
                        _eventsTraites.remove(i);

                    }
                }
            }

        }
        GetEvents._eventsTraites = _eventsTraites;
        System.out.println("Size event : "+GetEvents._events.size());
        System.out.println("Size eventTraite : "+GetEvents._eventsTraites.size());
    }



}
