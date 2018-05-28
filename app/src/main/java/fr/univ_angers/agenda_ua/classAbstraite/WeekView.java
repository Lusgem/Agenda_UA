package fr.univ_angers.agenda_ua.classAbstraite;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.RectF;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekViewEvent;
import com.facebook.stetho.Stetho;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import fr.univ_angers.agenda_ua.EventActivity;
import fr.univ_angers.agenda_ua.FormationActivity;
import fr.univ_angers.agenda_ua.R;
import fr.univ_angers.agenda_ua.Traitements.Traitement;
import fr.univ_angers.agenda_ua.Utilisateur;
import fr.univ_angers.agenda_ua.asyncTask.GroupesAsyncTask;
import fr.univ_angers.agenda_ua.calendrier.MainActivity;
import fr.univ_angers.agenda_ua.dataBase.DataSource;
import fr.univ_angers.agenda_ua.evenement.Evenement;
import fr.univ_angers.agenda_ua.evenement.EvenementExterieur;
import fr.univ_angers.agenda_ua.recyclerView.EventRecyclerView;


public abstract class WeekView extends AppCompatActivity implements com.alamkanak.weekview.WeekView.EventClickListener, MonthLoader.MonthChangeListener, GroupesAsyncTask.Listeners{

    private final static String TAG = Activity.class.getName();
    private static final int MY_PERMISSIONS_REQUEST_READ_CALENDAR = 1;
    public final static String EVENT_LOCATION = "EVENT_LOCATION";
    public final static String EVENT_DETAILS = "EVENT_DETAILS";
    public final static String EVENT_DATE = "EVENT_DATE";
    public final static String EVENT_RESUME = "EVENT_RESUME";

    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_THREE_DAY_VIEW = 2;
    private static final int TYPE_WEEK_VIEW = 3;
    private int mWeekViewType = TYPE_THREE_DAY_VIEW;
    private com.alamkanak.weekview.WeekView mWeekView;

    private DataSource _dataSource;
    private Dialog _dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekview);

        Log.i(TAG, "WeekView onCreate");

        _dataSource = new DataSource(this);
        _dataSource.open();

        // Premier lancement de l'application
        if (_dataSource.formationVide()){
            /* chrome://inspect/#devices */
            Stetho.initializeWithDefaults(this);

            GroupesAsyncTask groupes = new GroupesAsyncTask(_dataSource, this);
            groupes.execute();
        }

        // Retourne une reference pour la week view dans le layout activity_weekview !
        mWeekView = (com.alamkanak.weekview.WeekView) findViewById(R.id.weekView);

        // Cliquer sur un evenement affiche l'évenement !
        mWeekView.setOnEventClickListener(this);

        // La week view est scrollable horizontalement a l'infini. Nous fournissons les evenements
        // a chaque changement de mois.
        mWeekView.setMonthChangeListener(this);

        // Personnalise l'affichage de l'heure dans la week view
        setupDateTimeInterpreter(true);

        //Permet de démarrer l'application à 7h à la place de 0h
        mWeekView.goToHour(7);
    }

    @Override
    protected void onStart() {
        Log.i(TAG, "WeekView onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "WeekView onResume");
        super.onResume();
        _dataSource.open();
        if (!_dataSource.utilisateurVide()){
            Gson gson = new Gson();
            ArrayList<Utilisateur> uti = _dataSource.getAllUtilisateur();
            String form = uti.get(0).get_formation();
            Type type = new TypeToken<ArrayList<String>>() {}.getType();
            ArrayList<String> finalOutputString = gson.fromJson(form, type);
            for (String s : finalOutputString) {
                System.out.println(s);
            }
        }
        GetEvents._evenements = _dataSource.getAllEvenements();
        Traitement.TraitementMatiere();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "WeekView onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "WeekView onStop");
        super.onStop();
        _dataSource.close();
    }

    @Override
    protected void onRestart() {
        Log.i(TAG, "WeekView onRestart");
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "WeekView onDestroy");
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        setupDateTimeInterpreter(id == R.id.action_week_view);
        switch (id){
            case R.id.action_today:
                mWeekView.goToToday();
                return true;
            case R.id.action_day_view:
                if (mWeekViewType != TYPE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(1);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                }
                return true;
            case R.id.action_three_day_view:
                if (mWeekViewType != TYPE_THREE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_THREE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(3);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                }
                return true;
            case R.id.action_week_view:
                if (mWeekViewType != TYPE_WEEK_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_WEEK_VIEW;
                    mWeekView.setNumberOfVisibleDays(7);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                }
                return true;
            case R.id.action_parametre:
                Intent main = new Intent(this, FormationActivity.class);
                startActivity(main);
                WeekView.this.finish();
                return true;
            case R.id.action_taches_a_venir:
                Intent vue = new Intent(this, EventRecyclerView.class);
                startActivity(vue);
                return true;
            case R.id.action_comparer_evenenement:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED){
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CALENDAR)){
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALENDAR}, MY_PERMISSIONS_REQUEST_READ_CALENDAR);
                    }else {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALENDAR}, MY_PERMISSIONS_REQUEST_READ_CALENDAR);
                    }
                }else {
                    comparerEvenements();
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CALENDAR: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // La permission est garantie
                    comparerEvenements();
                } else {
// 1. Instantiate an AlertDialog.Builder with its constructor
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);

// 2. Chain together various setter methods to set the dialog characteristics
                    builder.setMessage("Vous ne pourrez pas utiliser cette fonctionnalité !")
                            .setTitle("Attention !");

                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK button
                            dialog.cancel();
                        }
                    });

// 3. Get the AlertDialog from create()
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                return;
            }
        }
    }

    public void comparerEvenements(){
        Date dateActuelle = new Date();
        ArrayList<EvenementExterieur> array = new ArrayList<EvenementExterieur>();
        Uri uri = CalendarContract.Events.CONTENT_URI;
        String[] projection = new String[]{CalendarContract.Events.TITLE, CalendarContract.Events.EVENT_LOCATION, CalendarContract.Events.DTSTART, CalendarContract.Events.DTEND};

        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        System.out.println(cursor.getCount());
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String titre = cursor.getString(0);
            String lieu = cursor.getString(1);
            Date debut = new Date(cursor.getLong(2));
            Date fin = new Date(cursor.getLong(3));

            if ((dateActuelle.compareTo(debut) < 0 || dateActuelle.compareTo(debut)== 0) && debut.getHours() != 0) {
                EvenementExterieur event = new EvenementExterieur(titre, lieu, debut, fin);
                System.out.println(event.toString());
                array.add(event);
            }
            cursor.moveToNext();
        }
        cursor.close();
        GetEvents._eventsExterieur = array;
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Set up a date time interpreter which will show short date values when in week view and long
     * date values otherwise.
     * @param shortDate True if the date values should be short.
     */
    private void setupDateTimeInterpreter(final boolean shortDate) {
        mWeekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEE", Locale.FRANCE);
                String weekday = weekdayNameFormat.format(date.getTime());
                SimpleDateFormat format = new SimpleDateFormat(" d/M", Locale.getDefault());

                // All android api level do not have a standard way of getting the first letter of
                // the week day name. Hence we get the first char programmatically.
                // Details: http://stackoverflow.com/questions/16959502/get-one-letter-abbreviation-of-week-day-of-a-date-in-java#answer-16959657
                if (shortDate)
                    weekday = String.valueOf(weekday.charAt(0));
                return weekday.toUpperCase() + format.format(date.getTime());
            }

            @Override
            public String interpretTime(int hour) {
                return hour + " H";
            }
        });
    }

    protected String getEventTitle(Calendar time) {
        return String.format("Evenement of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH)+1, time.get(Calendar.DAY_OF_MONTH));
    }

    private String toString_Date(Date datedebut,Date datefin){
        return Evenement.conversion(datedebut.getDate())+"/"+Evenement.conversion(datedebut.getMonth()+1)+"/"+(datedebut.getYear()+1900)+" de "+Evenement.conversion(datedebut.getHours())+"h"+Evenement.conversion(datedebut.getMinutes())+" à "+Evenement.conversion(datefin.getHours())+"h"+Evenement.conversion(datefin.getMinutes());

    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        Intent eventview = new Intent(this, EventActivity.class);
        eventview.putExtra(EVENT_DATE,toString_Date(event.getStartTime().getTime(),event.getEndTime().getTime()));
        eventview.putExtra(EVENT_LOCATION,event.getLocation().toString());
        eventview.putExtra(EVENT_RESUME,event.getName());
        startActivity(eventview);
    }

    public com.alamkanak.weekview.WeekView getWeekView() {
        return mWeekView;
    }

    public void afficherPopupLancement(){
        _dialog = new Dialog(this);
        _dialog.setContentView(R.layout.popup_lancement);
        if (_dataSource.evenementsVide()){
            _dialog.show();
        }
    }

    public void fermerPopupLancement(){
        _dialog.cancel();
    }

    public void popupClick(View view){
        Intent intent = new Intent(this, FormationActivity.class);
        startActivity(intent);
        fermerPopupLancement();
        finish();
    }
}
