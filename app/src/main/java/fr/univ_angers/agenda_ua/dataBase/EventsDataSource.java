package fr.univ_angers.agenda_ua.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fr.univ_angers.agenda_ua.Event;

/**
 * Created by Thibault Condemine on 26/04/2018.
 */
public class EventsDataSource {

    private SQLiteDatabase m_database;
    private MySQLiteHelper m_dbHelper;
    private String[] allColumns = {
            MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_PERSONNEL,
            MySQLiteHelper.COLUMN_LOCATION,
            MySQLiteHelper.COLUMN_MATIERE,
            MySQLiteHelper.COLUMN_GROUPE,
            MySQLiteHelper.COLUMN_SUMMARY,
            MySQLiteHelper.COLUMN_DATE_DEB,
            MySQLiteHelper.COLUMN_DATE_FIN,
            MySQLiteHelper.COLUMN_DESCRIPTION,
            MySQLiteHelper.COLUMN_DATE_STAMP,
            MySQLiteHelper.COLUMN_REMARQUE
    };

    public EventsDataSource(Context context){
        m_dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException{
        m_database = m_dbHelper.getWritableDatabase();
    }

    public void close(){
        m_dbHelper.close();
    }

    public Event createEvent(String personnel, String location, String matiere, String groupe, String summary, String dateDeb, String dateFin, String description, String dateStamp, String remarque){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_PERSONNEL, personnel);
        values.put(MySQLiteHelper.COLUMN_LOCATION, location);
        values.put(MySQLiteHelper.COLUMN_MATIERE, matiere);
        values.put(MySQLiteHelper.COLUMN_GROUPE, groupe);
        values.put(MySQLiteHelper.COLUMN_SUMMARY, summary);
        values.put(MySQLiteHelper.COLUMN_DATE_DEB, dateDeb);
        values.put(MySQLiteHelper.COLUMN_DATE_FIN, dateFin);
        values.put(MySQLiteHelper.COLUMN_DESCRIPTION, description);
        values.put(MySQLiteHelper.COLUMN_DATE_STAMP, dateStamp);
        values.put(MySQLiteHelper.COLUMN_REMARQUE, remarque);
        long insertId = m_database.insert(MySQLiteHelper.TABLE_EVENTS, null, values);
        Cursor cursor = m_database.query(MySQLiteHelper.TABLE_EVENTS, allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Event newEvent = cursorToEvent(cursor);
        cursor.close();
        return newEvent;
    }

    public void deleteEvent(Event event){
        long id = event.get_id();
        System.out.println("Event deleted with id : " + id);
        m_database.delete(MySQLiteHelper.TABLE_EVENTS, MySQLiteHelper.COLUMN_ID + " = " + id, null);
    }

    public ArrayList<Event> getAllEvents(){
        ArrayList<Event> events = new ArrayList<>();
        Cursor cursor = m_database.query(MySQLiteHelper.TABLE_EVENTS, allColumns, null, null, null, null, null);

        // Parcour du curseur !
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Event event = cursorToEvent(cursor);
            events.add(event);
            cursor.moveToNext();
        }

        // Fermeture du curseur !
        cursor.close();
        return events;
    }

    private Event cursorToEvent(Cursor cursor){
        Event event = new Event();
        event.set_id(cursor.getLong(0));
        event.set_personnel(cursor.getString(1));
        event.set_location(cursor.getString(2));
        event.set_matiere(cursor.getString(3));
        event.set_groupe(cursor.getString(4));
        event.set_summary(cursor.getString(5));
        event.set_date_debut(cursor.getString(6));
        event.set_date_fin(cursor.getString(7));
        event.set_description(cursor.getString(8));
        event.set_date_stamp(cursor.getString(9));
        event.set_remarque(cursor.getString(10));
        return event;
    }
}
