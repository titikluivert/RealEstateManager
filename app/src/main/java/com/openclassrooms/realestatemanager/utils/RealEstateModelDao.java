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


    @Query("SELECT * FROM Real_Estate WHERE realEstateAgentId = :realEstateAgentId")
    Cursor getItemsWithCursor(long realEstateAgentId);

    // only for search purpose
    //_____________________________________________________________________________________________
   // @Query("select * from user where date_of_birth=Date(:date)")

    @Query("SELECT * FROM Real_Estate WHERE surface BETWEEN :minSurface AND :maxSurface")
    LiveData<List<RealEstateModel>> SearchBySurface(float minSurface, float maxSurface);

    @Query("SELECT * FROM Real_Estate WHERE price BETWEEN :minPrice AND :maxPrice")
    LiveData<List<RealEstateModel>> SearchByPrice(int minPrice, int maxPrice);

    @Query("SELECT * FROM Real_Estate WHERE dateOfEntrance > :dateOfEntrance")
    LiveData<List<RealEstateModel>> SearchByOnlineSince(String dateOfEntrance);

    @Query("SELECT * FROM Real_Estate WHERE dateOfSale > :dateOfSale")
    LiveData<List<RealEstateModel>> SearchBySaleSince(String dateOfSale);

    @Query("SELECT * FROM Real_Estate WHERE address LIKE '%' || :address  || '%'")
    LiveData<List<RealEstateModel>> SearchByAddress(String address);

    @Query("SELECT * FROM Real_Estate WHERE :numPhoto <= numOfPhotoStored")
    LiveData<List<RealEstateModel>> SearchByPhotos(int numPhoto);

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
