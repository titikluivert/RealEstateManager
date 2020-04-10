package com.openclassrooms.realestatemanager.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.model.RealAgentDataRepository;
import com.openclassrooms.realestatemanager.model.RealEstateAgent;
import com.openclassrooms.realestatemanager.model.RealEstateDataRepository;
import com.openclassrooms.realestatemanager.model.RealEstateModel;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by Philippe on 27/02/2018.
 */

public class RealEstateViewModel extends AndroidViewModel {

    private  RealEstateDataRepository repository;
    //private  RealAgentDataRepository realAgent;

    private LiveData<List<RealEstateModel>> allRealEstates;
    private LiveData<List<RealEstateAgent>> currentUser;


    public RealEstateViewModel(@NonNull Application application) {
        super(application);
        repository = new RealEstateDataRepository(application);
       // realAgent  = new RealAgentDataRepository(application);
        allRealEstates = repository.getAllNotes();

    }

    public LiveData<List<RealEstateAgent>> getUser() {
        return this.currentUser;  }


    public void insert(RealEstateModel note) {
        repository.insert(note);
    }

    public void update(RealEstateModel note) {
        repository.update(note);
    }

    public void delete(RealEstateModel note) {
        repository.delete(note);
    }



    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }


    public LiveData<List<RealEstateModel>> getAllNotes() {
        return allRealEstates;
    }

    public LiveData<List<RealEstateModel>> SearchBySurfaceRepo(int minSurface, int maxSurface) {
        return repository.SearchBySurface(minSurface,maxSurface);
    }

    public LiveData<List<RealEstateModel>> SearchByPriceRepo(int minPrice, int maxPrice) {
        return repository.SearchByPrice(minPrice,maxPrice);
    }

    public LiveData<List<RealEstateModel>> SearchByOnlineSinceRepo(String dateOfEntrance) {
        return repository.SearchByOnlineSince(dateOfEntrance);
    }

    public LiveData<List<RealEstateModel>> SearchBySaleSinceRepo(String dateOfSale) {
        return repository.SearchBySaleSince(dateOfSale);
    }

    public LiveData<List<RealEstateModel>> SearchByAddressRepo(String address) {
        return repository.SearchByAddress(address);
    }


}


