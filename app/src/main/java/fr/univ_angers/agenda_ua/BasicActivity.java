package fr.univ_angers.agenda_ua;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.alamkanak.weekview.WeekViewEvent;
import com.facebook.stetho.Stetho;

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

        _datasource = new EventsDataSource(this);
        _datasource.open();

        _events = _datasource.getAllEvents();

        _datasource.close();

        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 3);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth - 1);
        startTime.set(Calendar.YEAR, newYear);
        Calendar endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR, 1);
        endTime.set(Calendar.MONTH, newMonth - 1);
        WeekViewEvent event = new WeekViewEvent(1, getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_01));
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 16);
        startTime.set(Calendar.MINUTE, 30);
        startTime.set(Calendar.MONTH, 4);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.set(Calendar.HOUR_OF_DAY, 17);
        endTime.set(Calendar.MINUTE, 30);
        endTime.set(Calendar.MONTH, 4);
        event = new WeekViewEvent(2, _events.get(15).get_summary(), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_02));
        events.add(event);

        return events;
    }
}
