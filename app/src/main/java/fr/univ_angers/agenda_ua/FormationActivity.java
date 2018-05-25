package fr.univ_angers.agenda_ua;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import fr.univ_angers.agenda_ua.asyncTask.ICSAsyncTask;
import fr.univ_angers.agenda_ua.classAbstraite.GetEvents;
import fr.univ_angers.agenda_ua.dataBase.DataSource;
import fr.univ_angers.agenda_ua.matieres.MatieresActivity;

public class FormationActivity extends AppCompatActivity implements ICSAsyncTask.Listeners {

    private final static String TAG = Activity.class.getName();

    private final Context context = this;

    private DataSource _datasource;
    private ArrayAdapter<Formation> _adapter = null;
    private Formation _groupe;

    private Spinner _spinner;
    private Button _suivant;
    private ProgressBar _progressBar;
    private Dialog _dialog;

    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formation);

        Log.i(TAG, "FormationActivity onCreate");

        _datasource = new DataSource(this);
        _datasource.open();

        _spinner = (Spinner) findViewById(R.id.spinner_groupes);
        _suivant = (Button) findViewById(R.id.bu_click_groupes);
        _progressBar = (ProgressBar) findViewById(R.id.activity_main_progress_bar);


        ArrayList <Formation> formations = _datasource.getAllFormation();
        _adapter = new ArrayAdapter<Formation>(this, android.R.layout.simple_spinner_dropdown_item, formations);
        _spinner.setAdapter(_adapter);

        _spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                _groupe = (Formation) parent.getSelectedItem();
                //Toast.makeText(context, "Lien : " + _groupe.get_lien() + ",  Groupe : " + _groupe.get_intitule(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    protected void onStart() {
        Log.i(TAG, "FormationActivity onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "FormationActivity onResume");
        super.onResume();
        _datasource.open();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "FormationActivity onPause");
        _datasource.close();
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "FormationActivity onStop");
        super.onStop();
        _datasource.close();
    }

    @Override
    protected void onRestart() {
        Log.i(TAG, "FormationActivity onRestart");
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "FormationActivity onDestroy");
        super.onDestroy();
    }

    public void onClickGroups(View view){
        afficherPopup();
    }

    @Override
    public void onPreExecute() {
        updateUIAvantTache();
        _suivant.setEnabled(false);
        fermerPopup();
    }

    @Override
    public void doInBackground() {}

    @Override
    public void onPostExecute() {
        updateUIApresTache();
        GetEvents._evenements = _datasource.getAllEvenements();
        GetEvents._listeMatieres = _datasource.getMatieres();
        Intent matiere = new Intent(this, MatieresActivity.class);
        startActivity(matiere);
        _suivant.setEnabled(true);
        finish();
    }

    public void updateUIAvantTache(){
        _progressBar.setVisibility(View.VISIBLE);
    }

    public void updateUIApresTache(){
        _progressBar.setVisibility(View.GONE);
        _suivant.setEnabled(true);
    }

    public void afficherPopup(){
        TextView tv;
        _dialog = new Dialog(this);
        _dialog.setContentView(R.layout.popup_verification);
        tv = (TextView)_dialog.findViewById(R.id.verification_popup_txt);
        tv.setText(_groupe.get_intitule());
        _dialog.show();
    }

    public void fermerPopup(){
        _dialog.cancel();
    }

    public void popup_annuler(View view){
        fermerPopup();
    }

    public void popup_valider(View view){
        final ICSAsyncTask xat = new ICSAsyncTask(_datasource, this);
        String url = "http://celcat.univ-angers.fr/ics_etu.php?url=publi/etu/" + _groupe.get_lien();
        GetEvents._url = url;
        xat.execute(url);
        if (!_datasource.utilisateurVide()){
            _datasource.supprimeUtilisateurs();
        }
        _datasource.creationUtilisateur(_groupe.get_lien(), "");
    }

    private void configureAlarmManager(){
        Intent alarmIntent = new Intent(context, MyAlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
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
}
