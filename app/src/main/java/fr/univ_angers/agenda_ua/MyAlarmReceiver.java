package fr.univ_angers.agenda_ua;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Thibault Condemine on 20/05/2018.
 */
public class MyAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        System.out.println("Alarm Coucou");
        Toast.makeText(context, "Coucou", Toast.LENGTH_SHORT).show();
    }
}
