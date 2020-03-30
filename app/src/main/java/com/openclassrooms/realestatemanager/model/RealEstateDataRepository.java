package com.openclassrooms.realestatemanager.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.utils.RealEstateModelDao;
import com.openclassrooms.realestatemanager.utils.SaveMyRealEstateDatabase;

import java.util.List;

/**
 * Created by Philippe on 27/02/2018.
 */

public class RealEstateDataRepository {

    private final RealEstateModelDao estateModelDao;

    private LiveData<List<RealEstateModel>> AllRealEstate;

    public RealEstateDataRepository(Application application) {
        SaveMyRealEstateDatabase database = SaveMyRealEstateDatabase.getInstance(application);
        estateModelDao = database.realEstateModelDao();
        AllRealEstate = estateModelDao.getAllNotes();
    }

    public void insert(RealEstateModel note) {
        new InsertNoteAsyncTask(estateModelDao).execute(note);
    }

    public void update(RealEstateModel note) {
        new UpdateNoteAsyncTask(estateModelDao).execute(note);
    }

    public void delete(RealEstateModel note) {
        new DeleteNoteAsyncTask(estateModelDao).execute(note);
    }

    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(estateModelDao).execute();
    }

    public LiveData<List<RealEstateModel>> getAllNotes() {
        return AllRealEstate;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<RealEstateModel, Void, Void> {
        private RealEstateModelDao noteDao;

        private InsertNoteAsyncTask(RealEstateModelDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(RealEstateModel... notes) {
            noteDao.insertRealEstateModels(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<RealEstateModel, Void, Void> {
        private RealEstateModelDao noteDao;

        private UpdateNoteAsyncTask(RealEstateModelDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(RealEstateModel... notes) {
            noteDao.updateRealEstateModel(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<RealEstateModel, Void, Void> {
        private RealEstateModelDao noteDao;

        private DeleteNoteAsyncTask(RealEstateModelDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(RealEstateModel... notes) {
            noteDao.deleteRealEstateModel(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private RealEstateModelDao noteDao;

        private DeleteAllNotesAsyncTask(RealEstateModelDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }

}
