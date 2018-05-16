package fr.univ_angers.agenda_ua.asyncTask;

import android.app.Activity;
import android.util.Log;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;


import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import javax.security.auth.callback.Callback;

import fr.univ_angers.agenda_ua.dataBase.EventsDataSource;


public class ICSAsyncTask extends android.os.AsyncTask<String, Void, Void> {

    private final static String TAG = Activity.class.getName();

    public interface Listeners{
        void onPreExecute();
        void doInBackground();
        void onPostExecute();
    }

    private String _dateDebut;
    private String _dateFin;
    private String _summary;
    private String _dateStamp;
    private String _location;
    private String _matiere;
    private String _personnel;
    private String _groupe;
    private String _remarque;

    private EventsDataSource _datasource;

    private final WeakReference<Listeners> _callback;


    public ICSAsyncTask(EventsDataSource dataSource, Listeners callback){
        _datasource = dataSource;
        _callback = new WeakReference<Listeners>(callback);
    }

    @Override
    protected Void doInBackground(String ... strings) {
        _callback.get().doInBackground();
        try {
            URL url = new URL(strings[0]);

            Log.e(TAG, "En cours d'execution sur : " + url.toString());

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
                    _datasource.createEvent(_personnel,_location,_matiere,_groupe,_summary,_dateDebut,_dateFin,_dateStamp,_remarque);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        Log.e(TAG, "Debut");
        _callback.get().onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Log.e(TAG, "Fin");
        _callback.get().onPostExecute();
    }
}
