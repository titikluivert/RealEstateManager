package com.openclassrooms.realestatemanager.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.RealEstateModelPref;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Philippe on 21/02/2018.
 */

public class mainUtils {

    public static final String REAL_ESTATE = "Real Estate";

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
    public static Boolean isInternetAvailable(Context context) {
        WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return wifi.isWifiEnabled();
    }



    // ---
    private String convertDateToHour(Date date){
        DateFormat dfTime = new SimpleDateFormat("HH:mm");
        return dfTime.format(date);
    }



    public static  void saveParamEstateInfo (Context context, String realEstateType, String realEstatePrice, String realEstateSurface, String realEstateNumOfRooms, String realEstateDescription, String realEstateAddress)
    {


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.typePref), realEstateType);
        editor.putString(context.getString(R.string.pricePref), realEstatePrice);
        editor.putString(context.getString(R.string.surfacePref), realEstateSurface);
        editor.putString(context.getString(R.string.numbeOfRoomsPref), realEstateNumOfRooms);
        editor.putString(context.getString(R.string.descriptionPref), realEstateDescription);
        editor.putString(context.getString(R.string.addressPref), realEstateAddress);
        editor.apply();
    }


    public static RealEstateModelPref getParamEstateInfo(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        return new RealEstateModelPref(sharedPreferences.getString(context.getString(R.string.typePref), ""),
                sharedPreferences.getString(context.getString(R.string.pricePref), ""),
                sharedPreferences.getString(context.getString(R.string.surfacePref), ""),
                sharedPreferences.getString(context.getString(R.string.numbeOfRoomsPref), ""),
                sharedPreferences.getString(context.getString(R.string.descriptionPref), ""),
                sharedPreferences.getString(context.getString(R.string.addressPref), "")

        );

    }
}
