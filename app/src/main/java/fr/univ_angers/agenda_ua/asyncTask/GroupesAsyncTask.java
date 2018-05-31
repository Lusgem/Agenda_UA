package fr.univ_angers.agenda_ua.asyncTask;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import fr.univ_angers.agenda_ua.Formation;
import fr.univ_angers.agenda_ua.dataBase.DataSource;

/**
 * Tache asycnhrone permettant de récupérer la liste des formations à partir d'un fichier html
 */
public class GroupesAsyncTask extends AsyncTask<Void,Void,Void> {

    private final static String TAG = Activity.class.getName();

    public interface Listeners{
        void onPreExecute();
        void doInBackground();
        void onPostExecute();
    }

    private DataSource _datasource;

    private final WeakReference<Listeners> _callback;

    public GroupesAsyncTask(DataSource dataSource, Listeners callback){
        _datasource = dataSource;
        _callback = new WeakReference<Listeners>(callback);
    }

    @Override
    protected void onPreExecute() {
        Log.i(TAG, "Debut");
        _callback.get().onPreExecute();
        if (!_datasource.formationVide()){
            _datasource.supprimeFormations();
        }
    }

    @Override
    protected Void doInBackground(Void ... voids) {
        Log.i(TAG, "En cours d'execution");
        _callback.get().doInBackground();

        ArrayList<Formation> formationList = new ArrayList<>();

        try {
            Document doc = Jsoup.connect("http://celcat.univ-angers.fr/web/publi/etu/gindex.html").get();
            Element content = doc.getElementById("content");
            Elements links = content.getElementsByTag("a");
            for (Element link : links) {
                String lien = link.attr("href");
                String intitule = link.text();
                if (lien.length()>5 && lien.substring(lien.length()-5)==".html");
                {
                    _datasource.creationFormation(intitule, lien.substring(0,lien.length()-5)+".ics");
                    //formationList.add(new Formation(intitule,lien.substring(0,lien.length()-5)+".ics"));
                }
            }
            //ArrayAdapter<Formation> adapter = new ArrayAdapter<Formation>(contexts[0], android.R.layout.simple_spinner_dropdown_item, formationList);
            //return adapter;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Log.i(TAG, "Fin");
        _callback.get().onPostExecute();
    }
}
