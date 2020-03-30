package com.openclassrooms.realestatemanager.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.utils.RealEstateAgentDao;
import com.openclassrooms.realestatemanager.utils.SaveMyRealEstateDatabase;

import java.util.List;

/**
 * Created by Philippe on 28/02/2018.
 */

public class RealAgentDataRepository {

    private final RealEstateAgentDao estateAgentDao;

    private LiveData<List<RealEstateAgent>> AllAgent;

    public RealAgentDataRepository(Application application) {
        SaveMyRealEstateDatabase database = SaveMyRealEstateDatabase.getInstance(application);
        estateAgentDao = database.realEstateAgentDao();
        AllAgent = estateAgentDao.getUser();
    }

    public void create(RealEstateAgent note) {
        new RealAgentDataRepository.CreateNoteAsyncTask(estateAgentDao).execute(note);
    }


    public LiveData<List<RealEstateAgent>> getUser() {
        return AllAgent;
    }

    private static class CreateNoteAsyncTask extends AsyncTask<RealEstateAgent, Void, Void> {
        private RealEstateAgentDao agentDao;

        private CreateNoteAsyncTask(RealEstateAgentDao agentDao) {
            this.agentDao = agentDao;
        }

        @Override
        protected Void doInBackground(RealEstateAgent... notes) {
            agentDao.createUser(notes[0]);
            return null;
        }
    }

}
