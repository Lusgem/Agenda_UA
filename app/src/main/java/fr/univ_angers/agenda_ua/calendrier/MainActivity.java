package fr.univ_angers.agenda_ua.calendrier;

import android.app.Activity;
import android.view.View;
import android.widget.ProgressBar;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.univ_angers.agenda_ua.R;
import fr.univ_angers.agenda_ua.classAbstraite.GetEvents;
import fr.univ_angers.agenda_ua.classAbstraite.WeekView;
import fr.univ_angers.agenda_ua.evenement.EvenementExterieur;

/**
 * Activité principale héritant de la classe WeekView, elle représente la base de notre interface
 * graphique
 */
public class MainActivity extends WeekView {

    private final static String TAG = Activity.class.getName();

    private ProgressBar _progressBar;

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        List<WeekViewEvent> events = new ArrayList<>();

        if (GetEvents._eventsTraites != null) {

            for (int i = 0; i < GetEvents._eventsTraites.size(); i++) {

                if (newYear == GetEvents._eventsTraites.get(i).get_annee_debut() && newMonth == (GetEvents._eventsTraites.get(i).get_mois_debut() + 1)) {

                    if (GetEvents._eventsExterieur != null) {

                        for (EvenementExterieur e : GetEvents._eventsExterieur) {
                            // Comparaison avec les evenements exterieurs !
                            if (e.get_debut().getHours() >= GetEvents._eventsTraites.get(i).get_heure_debut() && e.get_fin().getHours() <= GetEvents._eventsTraites.get(i).get_heure_fin() && e.get_debut().getDate() == GetEvents._eventsTraites.get(i).get_jour_debut() && e.get_debut().getMonth() == GetEvents._eventsTraites.get(i).get_mois_debut()) {
                                // Si il y a une occurence ajoute l'evenement exterieur au calendrier !
                                Calendar startTime = Calendar.getInstance();
                                startTime.set(Calendar.DAY_OF_MONTH, e.get_debut().getDate());
                                startTime.set(Calendar.HOUR_OF_DAY, e.get_debut().getHours());
                                startTime.set(Calendar.MINUTE, e.get_debut().getMinutes());
                                startTime.set(Calendar.MONTH, e.get_debut().getMonth());
                                startTime.set(Calendar.YEAR, (e.get_debut().getYear() + 1900));
                                Calendar endTime = (Calendar) startTime.clone();
                                endTime.set(Calendar.HOUR_OF_DAY, e.get_fin().getHours());
                                endTime.set(Calendar.MINUTE, e.get_fin().getMinutes());
                                WeekViewEvent event = new WeekViewEvent(i, e.get_titre(), e.get_lieu(), startTime, endTime);
                                event.setColor(getResources().getColor(R.color.event_color_02));
                                events.add(event);
                            }
                        }
                    }
                    // Ajout de tout les evenements de l'emploi du temps !
                    Calendar startTime = Calendar.getInstance();
                    startTime.set(Calendar.DAY_OF_MONTH, GetEvents._eventsTraites.get(i).get_jour_debut());
                    startTime.set(Calendar.HOUR_OF_DAY, GetEvents._eventsTraites.get(i).get_heure_debut());
                    startTime.set(Calendar.MINUTE, GetEvents._eventsTraites.get(i).get_minute_debut());
                    startTime.set(Calendar.MONTH, GetEvents._eventsTraites.get(i).get_mois_debut());
                    startTime.set(Calendar.YEAR, GetEvents._eventsTraites.get(i).get_annee_debut());
                    Calendar endTime = (Calendar) startTime.clone();
                    endTime.set(Calendar.HOUR_OF_DAY, GetEvents._eventsTraites.get(i).get_heure_fin());
                    endTime.set(Calendar.MINUTE, GetEvents._eventsTraites.get(i).get_minute_fin());
                    WeekViewEvent event = new WeekViewEvent(i, GetEvents._eventsTraites.get(i).toString(), GetEvents._eventsTraites.get(i).get_location(), startTime, endTime);
                    event.setColor(getResources().getColor(R.color.event_color_01));
                    events.add(event);
                }
            }
        }
        return events;
    }

    @Override
    public void onPreExecute() {
        _progressBar = (ProgressBar)findViewById(R.id.weekView_pb);
        updateUIAvantTache();
    }

    @Override
    public void doInBackground() {

    }

    @Override
    public void onPostExecute() {
        updateUIApresTache();
        afficherPopupLancement();
    }

    public void updateUIAvantTache(){
        _progressBar.setVisibility(View.VISIBLE);
    }

    public void updateUIApresTache(){
        _progressBar.setVisibility(View.GONE);
    }


}
