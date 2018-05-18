package fr.univ_angers.agenda_ua.calendrier;

import android.app.Activity;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.univ_angers.agenda_ua.R;
import fr.univ_angers.agenda_ua.classAbstraite.GetEvents;
import fr.univ_angers.agenda_ua.classAbstraite.WeekView;

public class BasicActivity extends WeekView {

    private final static String TAG = Activity.class.getName();

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        List<WeekViewEvent> events = new ArrayList<>();

        int j = 0;
        int test = 0;
        for (int i = 0; i < GetEvents._eventsTraites.size(); i++) {

            if (newYear == GetEvents._eventsTraites.get(i).get_annee_debut() && newMonth == (GetEvents._eventsTraites.get(i).get_mois_debut() + 1)) {
                Calendar startTime = Calendar.getInstance();
                startTime.set(Calendar.DAY_OF_MONTH, GetEvents._eventsTraites.get(i).get_jour_debut());
                startTime.set(Calendar.HOUR_OF_DAY, GetEvents._eventsTraites.get(i).get_heure_debut());
                startTime.set(Calendar.MINUTE, GetEvents._eventsTraites.get(i).get_minute_debut());
                startTime.set(Calendar.MONTH, GetEvents._eventsTraites.get(i).get_mois_debut());
                startTime.set(Calendar.YEAR, GetEvents._eventsTraites.get(i).get_annee_debut());
                Calendar endTime = (Calendar) startTime.clone();
                endTime.set(Calendar.HOUR_OF_DAY, GetEvents._eventsTraites.get(i).get_heure_fin());
                endTime.set(Calendar.MINUTE, GetEvents._eventsTraites.get(i).get_minute_fin());
                WeekViewEvent event = new WeekViewEvent(i, GetEvents._eventsTraites.get(i).get_summary() , GetEvents._eventsTraites.get(i).get_location(), startTime, endTime);
                event.setColor(getResources().getColor(R.color.event_color_01));
                events.add(event);
                j++;
            }
            test = i;
        }
        System.out.println(test);
        System.out.println(j);

        return events;
    }
}
