package fr.univ_angers.agenda_ua;

import android.app.Activity;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import fr.univ_angers.agenda_ua.asyncTask.ICSAsyncTask;
import fr.univ_angers.agenda_ua.classAbstraite.GetEvents;
import fr.univ_angers.agenda_ua.dataBase.EventsDataSource;

/**
 * Created by Thibault Condemine on 18/05/2018.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class SyncJobService extends JobService implements ICSAsyncTask.Listeners {

    private final static String TAG = Activity.class.getName();
    private ICSAsyncTask _icsTask;
    private JobParameters jobParameters;
    private EventsDataSource _datasource;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.e(TAG, "SyncJob is started.");
        String url = GetEvents._url;
        _datasource = new EventsDataSource(this);
        _datasource.open();
        _datasource.deleteEvent();
        jobParameters = params;
        _icsTask = new ICSAsyncTask(_datasource, this);
        _icsTask.execute(url);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.e(TAG, "SyncJob is stopped !");
        if (_icsTask != null){
            _icsTask.cancel(true);
        }
        return false;
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void doInBackground() {

    }

    @Override
    public void onPostExecute() {
        _datasource.close();
        jobFinished(jobParameters, false);
    }
}
