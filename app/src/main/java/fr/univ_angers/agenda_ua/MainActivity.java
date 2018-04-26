package fr.univ_angers.agenda_ua;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {


    private final static String TAG = Activity.class.getName();

    private EditText m_etLinkMain;
    private Button bu_clickMain;
    private TextView m_tv1;
    private TextView m_tv2;

    public String normURL(String _url){
        if (!(_url.substring(0,7).equalsIgnoreCase("http://"))){
            _url = "http://"+_url;
        }
        if (_url.substring(_url.length()-4).equalsIgnoreCase(".xml")){
            _url = _url.substring(0,_url.length()-4)+".ics";
        }
        return _url;

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "MainActivity onCreate");

        m_etLinkMain = (EditText) findViewById(R.id.et_link_main);
        m_tv1 = (TextView) findViewById(R.id.textView1);
        m_tv2 = (TextView) findViewById(R.id.textView2);
        bu_clickMain = (Button) findViewById(R.id.bu_click_main);

    }

    @Override
    protected void onStart() {
        Log.i(TAG, "MainActivity onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "MainActivity onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "MainActivity onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "MainActivity onStop");
        super.onStop();
    }

    @Override
    protected void onRestart() {
        Log.i(TAG, "MainActivity onRestart");
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "MainActivity onDestroy");
        super.onDestroy();
    }

    public void onClick(View view){
        final ICSAsyncTask xat = new ICSAsyncTask(this);
        String chaine = m_etLinkMain.getText().toString();
        System.out.println(chaine.substring(chaine.length()-4));
        chaine = normURL(chaine);
        if (chaine.substring(chaine.length()-4).equalsIgnoreCase(".ics")) {
            xat.execute(chaine);
        }
        else{
            Toast.makeText(this,"Entrez un .ics",Toast.LENGTH_SHORT).show();

        }

    }
}
