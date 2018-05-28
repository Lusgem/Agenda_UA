package fr.univ_angers.agenda_ua.synchronisation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

public class SyncJobCreator implements JobCreator {
    @Nullable
    @Override
    public Job create(@NonNull String tag) {
        switch (tag) {
            case AgendaSyncJob.TAG :
                return new AgendaSyncJob();
                default:
                    return null;
        }
    }

}
