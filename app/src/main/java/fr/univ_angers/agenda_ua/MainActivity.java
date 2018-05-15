package fr.univ_angers.agenda_ua;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import fr.univ_angers.agenda_ua.asyncTask.ICSAsyncTask;
import fr.univ_angers.agenda_ua.classAbstraite.GetEvents;
import fr.univ_angers.agenda_ua.dataBase.EventsDataSource;
import fr.univ_angers.agenda_ua.evenement.Event;

public class MainActivity extends AppCompatActivity {


    private final static String TAG = Activity.class.getName();

    private EditText _etLinkMain;

    private EventsDataSource _datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "MainActivity onCreate");

        _etLinkMain = (EditText) findViewById(R.id.et_link_main);

        this.deleteDatabase("events.db");
        _datasource = new EventsDataSource(this);
        _datasource.open();

        /* chrome://inspect/#devices */
        Stetho.initializeWithDefaults(this);
    }

    @Override
    protected void onStart() {
        Log.i(TAG, "MainActivity onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "MainActivity onResume");
        _datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "MainActivity onPause");
        _datasource.close();
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "MainActivity onStop");
        super.onStop();
    }

    @Override
    protected void onRestart() {
        Log.i(TAG, "MainActivity onRestart");
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "MainActivity onDestroy");
        super.onDestroy();
    }

    public String normURL(String _url) {
        if (!(_url.substring(0, 7).equalsIgnoreCase("http://"))) {
            _url = "http://" + _url;
        }
        if (_url.substring(_url.length() - 4).equalsIgnoreCase(".xml")) {
            _url = _url.substring(0, _url.length() - 4) + ".ics";
        }
        return _url;
    }

    public void onClick(View view) {
        final ICSAsyncTask xat = new ICSAsyncTask(_datasource);
        String chaine = _etLinkMain.getText().toString();
        System.out.println(chaine.substring(chaine.length() - 4));
        chaine = normURL(chaine);
        if (chaine.substring(chaine.length() - 4).equalsIgnoreCase(".ics")) {
            xat.execute(chaine);
        } else {
            Toast.makeText(this, "Entrez un .ics", Toast.LENGTH_SHORT).show();
        }
    }

    public void onData(View view) {
        /*_events = _datasource.getAllEvents();

        for (Event e : _events){
            System.out.println(e.toString());
        }

        System.out.println(_events.get(0));
        _datasource.deleteEvent(_events.get(0));*/
        GetEvents._events = _datasource.getAllEvents();
        Intent WeekView = new Intent(this, BasicActivity.class);
        startActivity(WeekView);

        //getCalendar();
    }

    public void onClickGroup(View view){

        Intent GroupeActivity = new Intent(this, GroupesActivity.class);
        startActivity(GroupeActivity);


    }
}
