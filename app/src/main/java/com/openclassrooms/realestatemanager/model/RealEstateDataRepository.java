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


    public RealEstateDataRepository(Application application) {
        SaveMyRealEstateDatabase database = SaveMyRealEstateDatabase.getInstance(application);
        estateModelDao = database.realEstateModelDao();
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
        return estateModelDao.getAllNotes();
    }

    public LiveData<List<RealEstateModel>> SearchBySurface(int minSurface, int maxSurface) {
        return estateModelDao.SearchBySurface(minSurface,maxSurface);
    }

    public LiveData<List<RealEstateModel>> SearchByPrice(int minPrice, int maxPrice) {
        return estateModelDao.SearchByPrice(minPrice,maxPrice);
    }

    public LiveData<List<RealEstateModel>> SearchByOnlineSince(String dateOfEntrance) {
        return estateModelDao.SearchByOnlineSince(dateOfEntrance);
    }

    public LiveData<List<RealEstateModel>> SearchBySaleSince(String dateOfSale) {
        return estateModelDao.SearchBySaleSince(dateOfSale);
    }

    public LiveData<List<RealEstateModel>> SearchByAddress(String address) {
        return estateModelDao.SearchByAddress(address);
    }

   /* public LiveData<List<RealEstateModel>> SearchByPhoto(List<String> photo) {
        return searchByPhoto;
    }*/


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
