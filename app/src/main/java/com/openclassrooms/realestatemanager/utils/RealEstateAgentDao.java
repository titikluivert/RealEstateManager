package com.openclassrooms.realestatemanager.utils;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.openclassrooms.realestatemanager.model.RealEstateAgent;

import java.util.List;

@Dao
public interface RealEstateAgentDao {


        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void createUser(RealEstateAgent user);


        @Query("SELECT * FROM RealEstateAgent ORDER BY id DESC")
        LiveData<List<RealEstateAgent>> getUser();
}


