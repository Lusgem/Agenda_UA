package fr.univ_angers.agenda_ua.Traitements;

import java.util.ArrayList;
import java.util.ListIterator;

import fr.univ_angers.agenda_ua.classAbstraite.GetEvents;
import fr.univ_angers.agenda_ua.evenement.Evenement;

public abstract class Traitement {

    public static void TraitementMatiere(){
        ArrayList<Evenement> _eventsTraites = (ArrayList<Evenement>)GetEvents._evenements.clone();
        if(GetEvents._listeMatieresAEnlever.isEmpty()){
            System.out.println("Pas de traitement");
        }
        else{
            for(String matiere : GetEvents._listeMatieresAEnlever){
                ListIterator<Evenement> iterator = _eventsTraites.listIterator();
                while(iterator.hasNext()){
                    Evenement e = (Evenement) iterator.next();
                    if(e.get_matiere()!=null && e.get_matiere().equalsIgnoreCase(matiere)) iterator.remove();


                }
            }

        }
        GetEvents._eventsTraites = _eventsTraites;
        System.out.println("Size event : "+GetEvents._evenements.size());
        System.out.println("Size eventTraite : "+GetEvents._eventsTraites.size());
    }





}
