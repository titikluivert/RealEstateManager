package com.openclassrooms.realestatemanager.utils;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.openclassrooms.realestatemanager.model.RealEstateAgent;
import com.openclassrooms.realestatemanager.model.RealEstateModel;


@Database(entities = {RealEstateModel.class, RealEstateAgent.class}, version = 1, exportSchema = false)
@TypeConverters({mainUtils.Converter.class , mainUtils.DateConverters.class})
public abstract class SaveMyRealEstateDatabase extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile SaveMyRealEstateDatabase instance;

    // --- DAO ---
    public abstract RealEstateModelDao realEstateModelDao();
    public abstract RealEstateAgentDao realEstateAgentDao();


    public static synchronized SaveMyRealEstateDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    SaveMyRealEstateDatabase.class, "Real_Estate")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private RealEstateModelDao realEstateDao;
        private RealEstateAgentDao realEstateAgtDao;

        private PopulateDbAsyncTask(SaveMyRealEstateDatabase db) {
            realEstateAgtDao = db.realEstateAgentDao();
            realEstateDao = db.realEstateModelDao();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            realEstateDao.deleteAllNotes();
            realEstateAgtDao.createUser(new RealEstateAgent( 1,  "Maxwell", "0123456789", "test@test.de"));
            //realEstateDao.insertRealEstateModels(new RealEstateModel("Penthouse", "5000000", "250","5","very Good","Abstatt",
                   // null,"for sale","01.01.2020",null,"restaurant",1));
            return null;
        }
    }

}

