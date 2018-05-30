package fr.univ_angers.agenda_ua.synchronisation;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.evernote.android.job.DailyJob;
import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import fr.univ_angers.agenda_ua.asyncTask.ICSAsyncTask;
import fr.univ_angers.agenda_ua.calendrier.MainActivity;
import fr.univ_angers.agenda_ua.classAbstraite.GetEvents;
import fr.univ_angers.agenda_ua.classAbstraite.WeekView;
import fr.univ_angers.agenda_ua.dataBase.DataSource;

public class AgendaSyncJob extends Job {

    public static final String TAG = "job_agenda_tag";

    private String _dateDebut;
    private String _dateFin;
    private String _summary;
    private String _dateStamp;
    private String _location;
    private String _matiere;
    private String _personnel;
    private String _groupe;
    private String _remarque;

    private DataSource _datasource;




    @Override
    @NonNull
    protected Result onRunJob(Params params) {
        _datasource = new DataSource(getContext());
        _datasource.open();
        if (!_datasource.evenementsVide()){
            _datasource.supprimeEvenements();
        }
        if (!_datasource.utilisateurVide()){
        try {
            URL url = new URL("http://celcat.univ-angers.fr/ics_etu.php?url=publi/etu/" +_datasource.getAllUtilisateur().get(0).get_lien());

            Log.i(TAG, "En cours d'execution sur : " + url.toString());

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            InputStream is = connection.getInputStream();

            CalendarBuilder bld = new CalendarBuilder();
            Calendar cal = bld.build(is);

            for (Iterator i = cal.getComponents().iterator(); i.hasNext();) {
                Component component = (Component) i.next();
                if(component.getName()=="VEVENT") {
                    for (Iterator j = component.getProperties().iterator(); j.hasNext(); ) {
                        Property property = (Property) j.next();

                        if (property.getName()=="DTSTART") {
                            _dateDebut = property.getValue();
                        }
                        else if (property.getName()=="DTEND"){
                            _dateFin = property.getValue();
                        }
                        else if (property.getName()=="DTSTAMP") {
                            _dateStamp = property.getValue();
                        }
                        else if (property.getName()=="SUMMARY"){
                            _summary = property.getValue();
                        }
                        else if (property.getName()=="LOCATION"){
                            _location = property.getValue();
                        }
                        else if (property.getName()=="DESCRIPTION"){
                            String tab[] = property.getValue().split("\n");
                            for (int k=0;k<tab.length;k++){
                                if (tab[k].substring(0,7).equalsIgnoreCase("matière"))
                                    _matiere = tab[k].substring(10);
                                else if (tab[k].substring(0,9).equalsIgnoreCase("personnel"))
                                    _personnel = tab[k].substring(11);
                                else if (tab[k].substring(0,6).equalsIgnoreCase("groupe"))
                                    _groupe = tab[k].substring(9);
                                else if (tab[k].substring(0,9).equalsIgnoreCase("remarques"))
                                    _remarque = tab[k].substring(12);
                            }
                        }
                    }
                    _datasource.creationEvenement(_personnel,_location,_matiere,_groupe,_summary,_dateDebut,_dateFin,_dateStamp,_remarque);
                    _personnel=null;
                    _groupe=null;
                    _remarque=null;
                    _matiere=null;
                    _summary=null;
                    _location=null;
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserException e) {
            e.printStackTrace();
        }
            Intent intent = new Intent(getContext(), MainActivity.class);
            getContext().startActivity(intent);
            return Result.SUCCESS;


        }
        else {
            return Result.FAILURE;
        }
    }

    public static void scheduleJob() {
        new JobRequest.Builder(TAG)
                .startNow()
                .build()
                .schedule();
    }




}