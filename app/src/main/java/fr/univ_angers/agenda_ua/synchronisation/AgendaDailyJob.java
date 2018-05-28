package fr.univ_angers.agenda_ua.synchronisation;

import android.support.annotation.NonNull;

import com.evernote.android.job.DailyJob;
import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;

import java.util.concurrent.TimeUnit;

public class AgendaDailyJob extends DailyJob {
    public static final String TAG = "AgendaDailyJob";

    public static void schedule(){
        DailyJob.schedule(new JobRequest.Builder(AgendaSyncJob.TAG), TimeUnit.HOURS.toMillis(1), TimeUnit.HOURS.toMillis(6));
    }
    @NonNull
    @Override
    protected DailyJobResult onRunDailyJob(@NonNull Params params) {
        return DailyJobResult.SUCCESS;
    }
}
