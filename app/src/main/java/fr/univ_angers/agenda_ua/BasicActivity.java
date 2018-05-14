package fr.univ_angers.agenda_ua;

import android.app.Activity;
import android.os.Bundle;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.univ_angers.agenda_ua.dataBase.EventsDataSource;

public class BasicActivity extends WeekView {

    private final static String TAG = Activity.class.getName();

    private EventsDataSource _datasource;

    private ArrayList<Event> _events;

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        List<WeekViewEvent> events = new ArrayList<>();

            System.out.println(newMonth);
            System.out.println(newYear);
            /*System.out.println(getEvents._events.get(5).get_summary());
            System.out.println(getEvents._events.get(5).get_jour_debut());
            System.out.println(getEvents._events.get(5).get_mois_debut());
            System.out.println(getEvents._events.get(5).get_annee_debut());*/
            int j = 0;
            int test = 0;
            for (int i = 0 ; i < getEvents._events.size(); i++) {
                if (newYear == getEvents._events.get(i).get_annee_debut() && newMonth == getEvents._events.get(i).get_mois_debut() + 1) {
                    Calendar startTime = Calendar.getInstance();
                    startTime.set(Calendar.DAY_OF_MONTH, getEvents._events.get(i).get_jour_debut());
                    startTime.set(Calendar.HOUR_OF_DAY, getEvents._events.get(i).get_heure_debut());
                    startTime.set(Calendar.MINUTE, getEvents._events.get(i).get_minute_debut());
                    startTime.set(Calendar.MONTH, getEvents._events.get(i).get_mois_debut());
                    startTime.set(Calendar.YEAR, getEvents._events.get(i).get_annee_debut());
                    Calendar endTime = (Calendar) startTime.clone();
                    //startTime.set(Calendar.DAY_OF_MONTH, getEvents._events.get(i).get_jour_fin());
                    endTime.set(Calendar.HOUR_OF_DAY, getEvents._events.get(i).get_heure_fin());
                    //endTime.set(Calendar.MINUTE, getEvents._events.get(i).get_heure_fin());
                    //endTime.set(Calendar.MONTH, getEvents._events.get(i).get_mois_fin() - 1);
                    //endTime.set(Calendar.YEAR, getEvents._events.get(i).get_annee_fin());
                    if(getEvents._events.get(i).get_matiere().equals(null)){
                        WeekViewEvent event = new WeekViewEvent(i, getEvents._events.get(i).get_summary() ,getEvents._events.get(i).get_location(), startTime, endTime);
                        event.setColor(getResources().getColor(R.color.event_color_01));
                        events.add(event);
                    }else{
                        WeekViewEvent event = new WeekViewEvent(i, getEvents._events.get(i).get_matiere() ,getEvents._events.get(i).get_location(), startTime, endTime);
                        event.setColor(getResources().getColor(R.color.event_color_01));
                        events.add(event);
                    }
                    j++;
                }
                test = i;
            }
            System.out.println(test);
            System.out.println(j);

        return events;
    }
}
