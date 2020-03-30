package com.openclassrooms.realestatemanager.utils;

import android.content.ContentValues;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.room.TypeConverter;

import com.google.firebase.firestore.GeoPoint;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.openclassrooms.realestatemanager.model.RealEstateModel;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Philippe on 21/02/2018.
 */

public class mainUtils {

    public static final String REAL_ESTATE = "Real Estate";

    // 1 - FILE PURPOSE
    public static final String FILENAME = "estate_db.txt";
    public static final String FOLDERNAME = "realEstate";

    public static boolean REAL_ESTATE_SALE = false;

    // 1 - Define the authority of the FileProvider
    //public static final String AUTHORITY="com.openclassrooms.realestatemanager.fileprovider";

    /**
     * Conversion d'un prix d'un bien immobilier (Dollars vers Euros)
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     *
     * @param dollars
     * @return
     */
    public static int convertDollarToEuro(int dollars) {
        return (int) Math.round(dollars * 0.812);
    }

    /**
     * Conversion d'un prix d'un bien immobilier (Dollars vers Euros)
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     *
     * @param euro
     * @return
     */
    public static long convertEuroToDollar(long euro) {
        return Math.round(euro / 0.812);
    }

    /**
     * Conversion de la date d'aujourd'hui en un format plus approprié
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     *
     * @return
     */
    public static String getTodayDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormat.format(new Date());
    }

    /**
     * Conversion de la date d'aujourdhui en un format plus approprie
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     *
     * @param date
     * @return
     */
    public static String getConvertDate(String date) {
        String convertDate;
        String[] dateTemp;

        if (date == null) {
            convertDate = "01/01/1970";

        } else {
            dateTemp = date.split("/");

            for (int i = 0; i < dateTemp.length; i++) {
                char[] cArray = dateTemp[i].trim().toCharArray();
                if (cArray.length <= 1) {
                    dateTemp[i] = "0" + dateTemp[i];
                }
            }
            convertDate = dateTemp[2] + "/" + dateTemp[1] + "/" + dateTemp[0];
        }
        return convertDate;
    }

    /**
     * Vérification de la connexion réseau
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     *
     * @param context
     * @return
     */
    /*public static Boolean isInternetAvailable(Context context) {
        WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return wifi.isWifiEnabled();
    }*/
    public static Boolean isInternetAvailable(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return mWifi.isConnected();
    }

    public static class Converter {

        @TypeConverter
        public static List<String> restoreList(String listOfString) {
            return new Gson().fromJson(listOfString, new TypeToken<List<String>>() {
            }.getType());
        }

        @TypeConverter
        public static String saveList(List<String> listOfString) {
            return new Gson().toJson(listOfString);
        }
    }

    public static GeoPoint getLocationFromAddress(Context ctx, String strAddress) throws IOException {

        Geocoder coder = new Geocoder(ctx);
        List<Address> address;
        GeoPoint p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new GeoPoint((double) (location.getLatitude() * 1E6),
                    (double) (location.getLongitude() * 1E6));

            return p1;
        } finally {

        }
    }

    // --- UTILS ---
    public static RealEstateModel fromContentValues(ContentValues values) {

        final RealEstateModel estateModel = new RealEstateModel();

        if (values.containsKey("type")) estateModel.setType((values.getAsString("type")));
        if (values.containsKey("price")) estateModel.setPrice(values.getAsString("price"));
        if (values.containsKey("surface")) estateModel.setSurface(values.getAsString("surface"));
        if (values.containsKey("roomNumbers"))
            estateModel.setRoomNumbers(values.getAsString("roomNumbers"));
        if (values.containsKey("description"))
            estateModel.setDescription(values.getAsString("description"));
        if (values.containsKey("status")) estateModel.setStatus(values.getAsString("status"));
        if (values.containsKey("photos"))
            estateModel.setPhotos(Converter.restoreList(values.getAsString("photos")));
        if (values.containsKey("dateOfEntrance"))
            estateModel.setDateOfEntrance(values.getAsString("dateOfEntrance"));
        if (values.containsKey("dateOfSale"))
            estateModel.setDateOfSale(values.getAsString("dateOfSale"));
        if (values.containsKey("realEstateAgentId"))
            estateModel.setRealEstateAgentId(values.getAsLong("realEstateAgentId"));


        return estateModel;
    }
}

