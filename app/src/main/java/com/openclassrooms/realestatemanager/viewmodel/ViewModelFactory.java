package com.openclassrooms.realestatemanager.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.model.RealAgentDataRepository;
import com.openclassrooms.realestatemanager.model.RealEstateDataRepository;

import java.util.concurrent.Executor;

/**
 * Created by Philippe on 27/02/2018.
 */


/*
public class ViewModelFactory implements ViewModelProvider.Factory {

    private final RealEstateDataRepository itemDataSource;
    private final RealAgentDataRepository userDataSource;
    private final Executor executor;

    public ViewModelFactory(RealEstateDataRepository itemDataSource, RealAgentDataRepository userDataSource, Executor executor) {
        this.itemDataSource = itemDataSource;
        this.userDataSource = userDataSource;
        this.executor = executor;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RealEstateViewModel.class)) {
            return (T) new RealEstateViewModel(itemDataSource, userDataSource, executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}*/
