package fr.univ_angers.agenda_ua;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import fr.univ_angers.agenda_ua.asyncTask.ICSAsyncTask;
import fr.univ_angers.agenda_ua.classAbstraite.GetEvents;
import fr.univ_angers.agenda_ua.dataBase.EventsDataSource;
import fr.univ_angers.agenda_ua.evenement.Event;

public class GroupesActivity extends AppCompatActivity {

    Spinner spinner;
    Context context = this;
    Groupes groupe;


    private final static String TAG = Activity.class.getName();

    private EditText _etLinkMain;

    private ArrayList<Event> _events;
    private EventsDataSource _datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupes);

        Log.i(TAG, "GroupesActivity onCreate");

        _etLinkMain = (EditText) findViewById(R.id.et_link_main);

        this.deleteDatabase("events.db");
        _datasource = new EventsDataSource(this);
        _datasource.open();

        /* chrome://inspect/#devices */
        Stetho.initializeWithDefaults(this);

        spinner = (Spinner) findViewById(R.id.spinner_groupes);
        final GroupesAsyncTask gat = new GroupesAsyncTask();
        gat.execute(this);
        ArrayAdapter<Groupes> adapter = null;
        try {
            adapter = gat.get();
            spinner.setAdapter(adapter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                groupe = (Groupes) parent.getSelectedItem();

                Toast.makeText(context, "Lien : "+groupe.get_lien()+",  Groupe : "+groupe.get_intitule(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }

    public void onClickGroups(View view){
        final ICSAsyncTask xat = new ICSAsyncTask(_datasource);
        String chaine = "http://celcat.univ-angers.fr/ics_etu.php?url=publi/etu/"+groupe.get_lien();
        xat.execute(chaine);
    }

    public void onData_Groupes(View view){
        GetEvents._events = _datasource.getAllEvents();
        Intent WeekView = new Intent(this, BasicActivity.class);
        startActivity(WeekView);
    }
}
