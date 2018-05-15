package fr.univ_angers.agenda_ua.recyclerView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Date;

import fr.univ_angers.agenda_ua.evenement.Event;
import fr.univ_angers.agenda_ua.R;
import fr.univ_angers.agenda_ua.classAbstraite.GetEvents;

public class EventRecyclerView extends AppCompatActivity {

    private RecyclerView _rv_event;
    private EventAdapter _adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_recycler_view);

        _rv_event = (RecyclerView) findViewById(R.id.main_recycler_view);
        _adapter = new EventAdapter();
        _rv_event.setAdapter(_adapter);
        _rv_event.setLayoutManager(new LinearLayoutManager(this));

        ajouteEvent();
    }

    public void ajouteEvent(){
        Date dateActuelle = new Date();
        for (Event e : GetEvents._events) {
            if (dateActuelle.getMonth() == e.get_mois_debut() && dateActuelle.getDate() <= e.get_jour_debut() && (dateActuelle.getYear()+1900) <= e.get_annee_debut()){
                _adapter.ajoute(e);
            }else if (dateActuelle.getMonth() < e.get_mois_debut() && (dateActuelle.getYear()+1900) <= e.get_annee_debut()){
                _adapter.ajoute(e);
            }
        }
    }
}
