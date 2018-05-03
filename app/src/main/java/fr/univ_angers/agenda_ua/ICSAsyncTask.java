package fr.univ_angers.agenda_ua;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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
import java.util.ArrayList;
import java.util.Iterator;


public class ICSAsyncTask extends android.os.AsyncTask<String, Void, ArrayList<Event>> {
    interface CalendrierConsumer{
        void setArrayEvent(ArrayList<Event> arrayEvent);
    }

    private final static String TAG = Activity.class.getName();

    private CalendrierConsumer m_consumer;
    private ArrayList<Event> m_events;

    public ICSAsyncTask(CalendrierConsumer consumer){
        m_consumer = consumer;
        m_events = new ArrayList<>();
    }

    @Override
    protected ArrayList<Event> doInBackground(String ... strings) {
        try {
            URL url = new URL(strings[0]);
            Log.e(TAG, url.toString());

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            InputStream is = connection.getInputStream();

            CalendarBuilder bld = new CalendarBuilder();
            Calendar cal = bld.build(is);

            for (Iterator i = cal.getComponents().iterator(); i.hasNext();) {
                Component component = (Component) i.next();
                if(component.getName()=="VEVENT") {
                    //System.out.println("EVENT :");
                    Event e = new Event();
                    for (Iterator j = component.getProperties().iterator(); j.hasNext(); ) {
                        Property property = (Property) j.next();

                        if (property.getName()=="DTSTART") {
                            //e.set_date_debut(e.convert_date(property.getValue()));
                            e.set_date_debut(property.getValue());
                        }
                        else if (property.getName()=="DTEND"){
                            //e.set_date_fin(e.convert_date(property.getValue()));
                            e.set_date_fin(property.getValue());
                        }
                        else if (property.getName()=="DTSTAMP") {
                            //e.set_date_stamp(e.convert_date(property.getValue()));
                            e.set_date_stamp(property.getValue());
                        }
                        else if (property.getName()=="SUMMARY"){
                            e.set_summary(property.getValue());
                        }
                        else if (property.getName()=="LOCATION"){
                            e.set_location(property.getValue());
                        }
                        else if (property.getName()=="DESCRIPTION"){
                            String tab[] = property.getValue().split("\n");
                            e.set_description(property.getValue());
                            for (int k=0;k<tab.length;k++){
                                if (tab[k].substring(0,7).equalsIgnoreCase("matière"))
                                    e.set_matiere(tab[k].substring(10));
                                else if (tab[k].substring(0,9).equalsIgnoreCase("personnel"))
                                    e.set_personnel(tab[k].substring(12));
                                else if (tab[k].substring(0,6).equalsIgnoreCase("groupe"))
                                    e.set_groupe(tab[k].substring(9));
                                else if (tab[k].substring(0,9).equalsIgnoreCase("remarques"))
                                    e.set_remarque(tab[k].substring(12));
                            }
                        }
                    }
                    //System.out.println(e.to_string());
                    m_events.add(e);
                }
            }
            return m_events;
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
    protected void onPostExecute(ArrayList<Event> events) {
        Log.e(TAG, "Finished");
        for(Event e : m_events){

        }
        m_consumer.setArrayEvent(events);
    }
}
