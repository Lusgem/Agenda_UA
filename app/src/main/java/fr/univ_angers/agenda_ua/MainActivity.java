package fr.univ_angers.agenda_ua;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import fr.univ_angers.agenda_ua.asyncTask.GroupesAsyncTask;
import fr.univ_angers.agenda_ua.asyncTask.ICSAsyncTask;
import fr.univ_angers.agenda_ua.calendrier.BasicActivity;
import fr.univ_angers.agenda_ua.classAbstraite.GetEvents;
import fr.univ_angers.agenda_ua.dataBase.EventsDataSource;
import fr.univ_angers.agenda_ua.matieres.MatieresActivity;

public class MainActivity extends AppCompatActivity implements ICSAsyncTask.Listeners {

    private final static String TAG = Activity.class.getName();

    private final Context context = this;
    private final GroupesAsyncTask gat = new GroupesAsyncTask();

    private EventsDataSource _datasource;
    private ArrayAdapter<Groupes> _adapter = null;
    private Groupes _groupe;

    private Spinner _spinner;
    private Button _charger_groupe;
    private ProgressBar _progressBar;

    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "MainActivity onCreate");

        //Création et ouverture de la base de données des evenements
        _datasource = new EventsDataSource(this);
        _datasource.open();

        /* chrome://inspect/#devices */
        Stetho.initializeWithDefaults(this);

        _spinner = (Spinner) findViewById(R.id.spinner_groupes);
        _charger_groupe = (Button) findViewById(R.id.bu_click_groupes);
        _progressBar = (ProgressBar) findViewById(R.id.activity_main_progress_bar);

        _charger_groupe.setEnabled(false);

        configureAlarmManager();

        startAlarm();

        gat.execute(this);

        try {
            _adapter = gat.get();
            _spinner.setAdapter(_adapter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        _spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                _groupe = (Groupes) parent.getSelectedItem();
                _charger_groupe.setEnabled(true);
                //Toast.makeText(context, "Lien : " + _groupe.get_lien() + ",  Groupe : " + _groupe.get_intitule(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
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

    public void onClickGroups(View view){
        final ICSAsyncTask xat = new ICSAsyncTask(_datasource, this);
        String url = "http://celcat.univ-angers.fr/ics_etu.php?url=publi/etu/" + _groupe.get_lien();
        GetEvents._url = url;
        xat.execute(url);
    }

    @Override
    public void onPreExecute() {
        updateUIAvantTache();
        _charger_groupe.setEnabled(false);
    }

    @Override
    public void doInBackground() {}

    @Override
    public void onPostExecute() {
        updateUIApresTache();
        GetEvents._events = _datasource.getAllEvents();
        GetEvents._listeMatieres = _datasource.getMatieres();
        Intent Matiereactivity = new Intent(this, MatieresActivity.class);
        startActivity(Matiereactivity);
        _charger_groupe.setEnabled(true);
    }

    public void updateUIAvantTache(){
        _progressBar.setVisibility(View.VISIBLE);
    }

    public void updateUIApresTache(){
        _progressBar.setVisibility(View.GONE);
        _charger_groupe.setEnabled(true);
    }

    private void configureAlarmManager(){
        Intent alarmIntent = new Intent(MainActivity.this, MyAlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);
    }

    private void startAlarm() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE,53);

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(this, "Alarm set !", Toast.LENGTH_LONG).show();
    }

    private void stopAlarm() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        Toast.makeText(this, "Alarm Canceled !", Toast.LENGTH_SHORT).show();
    }

    /*public String normURL(String _url) {
        if (!(_url.substring(0, 7).equalsIgnoreCase("http://"))) {
            _url = "http://" + _url;
        }
        if (_url.substring(_url.length() - 4).equalsIgnoreCase(".xml")) {
            _url = _url.substring(0, _url.length() - 4) + ".ics";
        }
        return _url;
    }*/

    /*public void onClick(View view) {
        final ICSAsyncTask xat = new ICSAsyncTask(_datasource);
        String chaine = _etLinkMain.getText().toString();
        System.out.println(chaine.substring(chaine.length() - 4));
        chaine = normURL(chaine);
        if (chaine.substring(chaine.length() - 4).equalsIgnoreCase(".ics")) {
            xat.execute(chaine);
        } else {
            Toast.makeText(this, "Entrez un .ics", Toast.LENGTH_SHORT).show();
        }
    }*/

    /*public void onData(View view) {
        _events = _datasource.getAllEvents();

        for (Event e : _events){
            System.out.println(e.toString());
        }

        System.out.println(_events.get(0));
        _datasource.deleteEvent(_events.get(0));
        GetEvents._events = _datasource.getAllEvents();
        Intent WeekView = new Intent(this, BasicActivity.class);
        startActivity(WeekView);

        getCalendar();
    }*/

    /*public void onClickGroup(View view){
        Intent GroupeActivity = new Intent(this, GroupesActivity.class);
        startActivity(GroupeActivity);
    }*/
}
