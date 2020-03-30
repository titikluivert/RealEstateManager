/*package com.openclassrooms.realestatemanager.utils;

import android.content.Context;

import com.openclassrooms.realestatemanager.model.RealAgentDataRepository;
import com.openclassrooms.realestatemanager.model.RealEstateDataRepository;
import com.openclassrooms.realestatemanager.viewmodel.ViewModelFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

*//**
 * Created by Philippe on 27/02/2018.
 *//*

public class Injection {

    public static RealEstateDataRepository provideItemDataSource(Context context) {
        SaveMyRealEstateDatabase database = SaveMyRealEstateDatabase.getInstance(context);
        return new RealEstateDataRepository(database.realEstateModelDao());
    }

    public static RealAgentDataRepository provideUserDataSource(Context context) {
        SaveMyRealEstateDatabase database = SaveMyRealEstateDatabase.getInstance(context);
        return new RealAgentDataRepository(database.realEstateAgentDao());
    }

    public static Executor provideExecutor(){ return Executors.newSingleThreadExecutor(); }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        RealEstateDataRepository dataSourceItem = provideItemDataSource(context);
        RealAgentDataRepository dataSourceUser = provideUserDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(dataSourceItem, dataSourceUser, executor);
    }
}*/
