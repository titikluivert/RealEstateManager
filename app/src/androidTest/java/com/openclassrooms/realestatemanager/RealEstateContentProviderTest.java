package com.openclassrooms.realestatemanager;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import androidx.room.Room;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;
import com.openclassrooms.realestatemanager.utils.RealEstateContentProvider;
import com.openclassrooms.realestatemanager.utils.SaveMyRealEstateDatabase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by Maxwell on 01/03/2020.
 */

@RunWith(AndroidJUnit4ClassRunner.class)
public class RealEstateContentProviderTest {

    // FOR DATA
    private ContentResolver mContentResolver;

    // DATA SET FOR TEST
    private static long USER_ID = 1;

    @Before
    public void setUp() {
        Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(),
                SaveMyRealEstateDatabase.class)
                .allowMainThreadQueries()
                .build();
        mContentResolver = InstrumentationRegistry.getInstrumentation().getContext().getContentResolver();
    }

    @Test
    public void deleteInsertedItemBeforeTest() {
        // BEFORE : Adding demo item
        final int userUri = mContentResolver.delete(RealEstateContentProvider.URI_ITEM, null, null);
        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(RealEstateContentProvider.URI_ITEM, USER_ID), null, null, null, null);
        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), is(0));
        assertThat(cursor.moveToFirst(), is(false));
    }



    @Test
    public void getItemsWhenNoItemInserted() {
        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(RealEstateContentProvider.URI_ITEM, USER_ID), null, null, null, null);
        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), is(0));
        cursor.close();
    }

    @Test
    public void getAgent() {
        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(RealEstateContentProvider.URI_ITEM, USER_ID), null, null, null, null);
        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), is(1));
        cursor.close();
    }

    @Test
    public void insertAndGetItem() {
        // BEFORE : Adding demo item
        final Uri userUri = mContentResolver.insert(RealEstateContentProvider.URI_ITEM, generateItem());
        // TEST
        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(RealEstateContentProvider.URI_ITEM, USER_ID), null, null, null, null);
        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), is(1));
        assertThat(cursor.moveToFirst(), is(true));
        assertThat(cursor.getString(cursor.getColumnIndexOrThrow("type")), is("Villa"));
    }

    @Test
    public void deleteInsertedItem() {
        // BEFORE : Adding demo item
        final int userUri = mContentResolver.delete(RealEstateContentProvider.URI_ITEM, null, null);
        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(RealEstateContentProvider.URI_ITEM, USER_ID), null, null, null, null);
        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), is(0));
        assertThat(cursor.moveToFirst(), is(false));
    }


    private ContentValues generateItem(){
        final ContentValues values = new ContentValues();

        values.put("type","Villa");
        values.put("price", "2500000");
        values.put("surface","500");
        values.put("roomNumbers","4");
        values.put("description","Very nice");
        values.put("photos", "");
        values.put("numOfPhotoStored", "0");
        values.put("status","For Sale");
        values.put("dateOfEntrance","01/01/1990");
        values.put("dateOfSale","");
        values.put("poi","school, restaurant");
        values.put("realEstateAgentId", 1);

        return values;
    }
}
