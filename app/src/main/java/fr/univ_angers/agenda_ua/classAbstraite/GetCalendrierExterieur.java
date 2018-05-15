package fr.univ_angers.agenda_ua.classAbstraite;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.Date;

import fr.univ_angers.agenda_ua.evenement.EventExterieur;

/**
 * Created by Thibault Condemine on 15/05/2018.
 */
public abstract class GetCalendrierExterieur extends AppCompatActivity {

    public void getCalendar() {
        Date dateActuelle = new Date();

        Uri uri = CalendarContract.Events.CONTENT_URI;
        String[] projection = new String[]{CalendarContract.Events.TITLE, CalendarContract.Events.EVENT_LOCATION, CalendarContract.Events.DTSTART, CalendarContract.Events.DTEND};
        String selection = CalendarContract.Events.ALL_DAY + " IS NULL";
        String[] selectionArgs = new String[]{"null"};

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALENDAR}, 1);
        }

        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        System.out.println(cursor.getCount());
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String titre = cursor.getString(0);
            String lieu = cursor.getString(1);
            Date debut = new Date(cursor.getLong(2));
            Date fin = new Date(cursor.getLong(3));

            if ((dateActuelle.compareTo(debut) < 0 || dateActuelle.compareTo(debut)== 0) && debut.getHours() != 0) {
                EventExterieur event = new EventExterieur(titre, lieu, debut, fin);
                System.out.println(event.toString());
            }
            cursor.moveToNext();
        }
        cursor.close();
    }
}
