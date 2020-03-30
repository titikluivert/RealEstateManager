package com.openclassrooms.realestatemanager.utils;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.openclassrooms.realestatemanager.model.RealEstateModel;

import java.util.List;

@Dao
public interface RealEstateModelDao {

    @Query("SELECT * FROM Real_Estate WHERE realEstateAgentId = :realEstateAgentId")
    LiveData<List<RealEstateModel>> getRealEstateModels(long realEstateAgentId);


    @Query("SELECT * FROM Real_Estate ORDER BY price DESC")
    LiveData<List<RealEstateModel>> getAllNotes();


    // only for search purpose
    //_____________________________________________________________________________________________

    @Query("SELECT * FROM Real_Estate WHERE realEstateAgentId = :realEstateAgentId")
    Cursor getItemsWithCursor(long realEstateAgentId);

    @Query("SELECT * FROM Real_Estate WHERE surface BETWEEN :minSurface AND :maxSurface")
    LiveData<List<RealEstateModel>> SerachBySurface(int minSurface, int maxSurface);

    @Query("SELECT * FROM Real_Estate WHERE price BETWEEN :minPrice AND :maxPrice")
    LiveData<List<RealEstateModel>> SerachByPrice(int minPrice, int maxPrice);

    @Query("SELECT * FROM Real_Estate WHERE dateOfEntrance > :dateOfEntrace")
    LiveData<List<RealEstateModel>> SearchByOnlineSince(String dateOfEntrace);

    @Query("SELECT * FROM Real_Estate WHERE dateOfSale > :dateOfSale")
    LiveData<List<RealEstateModel>> SearchBySaleSince(String dateOfSale);

    @Query("SELECT * FROM Real_Estate WHERE address = :address")
    LiveData<List<RealEstateModel>> SearchByAddrees(String address);

    @Query("SELECT * FROM Real_Estate WHERE photos > :photo")
    LiveData<List<RealEstateModel>> SearchByAddrees(List<String> photo);

    //_____________________________________________________________________________________________

    @Insert
    long insertRealEstateModels(RealEstateModel item);


    @Update
    int updateRealEstateModel(RealEstateModel item);


    @Delete
    void deleteRealEstateModel(RealEstateModel item);


    @Query("DELETE FROM Real_Estate WHERE id = :itemId")
    int deleteRealEstateModelItem(long itemId);


    @Query("DELETE FROM Real_Estate")
    void deleteAllNotes();
}
