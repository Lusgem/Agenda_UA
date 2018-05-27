package fr.univ_angers.agenda_ua;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import fr.univ_angers.agenda_ua.classAbstraite.WeekView;

public class EventActivity extends AppCompatActivity {

    private TextView _tv_resume;
    private TextView _tv_date;
    private TextView _tv_location;
    private TextView _tv_infos_supplementaires;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        _tv_resume = (TextView) findViewById(R.id.tv_event_resume);
        _tv_date = (TextView) findViewById(R.id.tv_event_date);
        _tv_location = (TextView) findViewById(R.id.tv_event_location);
        LinearLayout layoutinfo = (LinearLayout) findViewById(R.id.linearlayout_EventinfoComp);
        LinearLayout layoutLocation = (LinearLayout) findViewById(R.id.linearlayout_Eventlocation);
        layoutinfo.setVisibility(View.GONE);
        Intent i1 = getIntent();
        _tv_date.setText(i1.getStringExtra(WeekView.EVENT_DATE));
        if(i1.getStringExtra(WeekView.EVENT_LOCATION).length()==0){
            layoutLocation.setVisibility(View.GONE);
        }
        else{
            _tv_location.setText(i1.getStringExtra(WeekView.EVENT_LOCATION));
        }

        _tv_resume.setText(i1.getStringExtra(WeekView.EVENT_RESUME));

    }
}
