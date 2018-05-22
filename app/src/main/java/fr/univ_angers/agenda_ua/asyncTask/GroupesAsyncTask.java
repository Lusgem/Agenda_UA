package fr.univ_angers.agenda_ua.asyncTask;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fr.univ_angers.agenda_ua.Groupes;

public class GroupesAsyncTask extends AsyncTask<Context,Void,ArrayAdapter<Groupes>> {

    private final static String TAG = Activity.class.getName();

    @Override
    protected ArrayAdapter<Groupes> doInBackground(Context... contexts) {
        ArrayList<Groupes> groupesList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("http://celcat.univ-angers.fr/web/publi/etu/gindex.html").get();
            Element content = doc.getElementById("content");
            Elements links = content.getElementsByTag("a");
            for (Element link : links) {
                String lien = link.attr("href");
                String intitule = link.text();
                if (lien.length()>5 && lien.substring(lien.length()-5)==".html");
                {
                    groupesList.add(new Groupes(intitule,lien.substring(0,lien.length()-5)+".ics"));
                }

            }
            ArrayAdapter<Groupes> adapter = new ArrayAdapter<Groupes>(contexts[0], android.R.layout.simple_spinner_dropdown_item, groupesList);
            return adapter;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        Log.e(TAG, "Start");
    }

    @Override
    protected void onPostExecute(ArrayAdapter<Groupes> groupesArrayAdapter) {
        Log.e(TAG, "Finished");
    }

}
