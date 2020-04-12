package com.openclassrooms.realestatemanager.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.room.TypeConverter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.RealEstateAgent;
import com.openclassrooms.realestatemanager.model.RealEstateModel;
import com.openclassrooms.realestatemanager.model.RealEstateModelPref;
import com.openclassrooms.realestatemanager.model.UploadImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Created by Philippe on 21/02/2018.
 */

public class mainUtils {

    public static final int AGENT_ID = 1;

    public static final float ZOOM_LEVEL = 15; //This goes up to 21

    public static final String REAL_ESTATE = "Real Estate";

    public static final int PROXIMITY_RADIUS = 1000;

    public static final int SET_INTERVAL = 120000;

    private static final double r2d = 180.0D / 3.141592653589793D;
    private static final double d2r = 3.141592653589793D / 180.0D;
    private static final double d2km = 111189.57696D * r2d;

    public static final String EXTRA_MAP_TO_SECOND = "MAP_TO_SECOND";
    public static final String DATA_SECOND2MAIN_ACTIVITY = "EXTRA_MODIFY_REAL_ESTATE";

    public static final String ApiKeyGoogleID = "AIzaSyAEwrcgezK5dT7YYVWph2Z3K6CJB497Sa4";

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
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
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
            convertDate = null;

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

    public static String changeStringToDate(String input) {
        if (input == null || input.trim().equals("")) {
            return "";
        } else {
            String a = input.trim().substring(0, 4);
            String temp = input.substring(input.length() - 4);
            String b = temp.trim().substring(0, 2);
            String c = temp.trim().substring(temp.length() - 2);
            return c + "/" + b + "/" + a;
        }

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
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
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

    public static double getDistanceInMeters(double lt1, double ln1, double lt2, double ln2) {
        final double x = lt1 * d2r;
        final double y = lt2 * d2r;
        return Math.acos(Math.sin(x) * Math.sin(y) + Math.cos(x) * Math.cos(y) * Math.cos(d2r * (ln1 - ln2))) * d2km;

    }

    public static double[] getLocationFromAddress(Context ctx, String strAddress) throws IOException {

        Geocoder coder = new Geocoder(ctx);
        List<Address> address;
        double[] p1 = {0.0, 0.0};

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            p1[0] = location.getLatitude();
            p1[1] = location.getLongitude();


            return p1;
        } finally {

        }
    }

    // --- UTILS ---
    public static RealEstateModel fromContentValues(ContentValues values) {

        final RealEstateModel estateModel = new RealEstateModel();

        if (values.containsKey("type")) estateModel.setType((values.getAsString("type")));
        if (values.containsKey("price")) estateModel.setPrice(values.getAsDouble("price"));
        if (values.containsKey("surface")) estateModel.setSurface(values.getAsFloat("surface"));
        if (values.containsKey("roomNumbers"))
            estateModel.setRoomNumbers(values.getAsInteger("roomNumbers"));
        if (values.containsKey("description"))
            estateModel.setDescription(values.getAsString("description"));
        if (values.containsKey("status")) estateModel.setStatus(values.getAsBoolean("status"));
        if (values.containsKey("photos"))
            estateModel.setPhotos(Converter.restoreList(values.getAsString("photos")));
        if (values.containsKey("dateOfEntrance"))
            estateModel.setDateOfEntrance(DateConverters.fromTimestamp(values.getAsString("dateOfEntrance")));
        if (values.containsKey("dateOfSale"))
            estateModel.setDateOfSale(DateConverters.fromTimestamp(values.getAsString("dateOfSale")));
        if (values.containsKey("realEstateAgentId"))
            estateModel.setRealEstateAgentId(values.getAsLong("realEstateAgentId"));

        return estateModel;
    }

    // example converter for java.util.Date
    public static class DateConverters {

        static DateFormat df = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());

        @TypeConverter
        public static Date fromTimestamp(String value) {
            if (value != null) {
                try {
                    return df.parse(value);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return null;
            } else {
                return null;
            }
        }

        @TypeConverter
        public static String dateToTimestamp(Date date) {
            return date == null ? null : df.format(date);
        }

    }

    public static boolean saveImageToInternalStorage(final Context context, final Bitmap image, String fileName) {

        try {
            final FileOutputStream fos = context.openFileOutput(fileName + ".png", Context.MODE_PRIVATE);
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();

            return true;
        } catch (Exception e) {

            return false;
        }
    }

    public static Bitmap loadImageBitmap(final Context context, String name) {

        //final FileInputStream fileInputStream;
        Bitmap bitmap = null;
        try {
            //fileInputStream = context.openFileInput(name+".png");
            FileInputStream fis = new FileInputStream(new File(name + ".png"));

            bitmap = BitmapFactory.decodeStream(fis);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static boolean fileExist(final Context context, String name) {
        return context.getFileStreamPath(name).exists();

    }


    private static String getFileExtension(Uri uri, Context ctx) {
        ContentResolver cR = Objects.requireNonNull(ctx).getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public static void uploadFile(Context context, Uri mImageUri, StorageReference mStorageRef, DatabaseReference mDatabaseRef, String uid, RealEstateModelPref user) {


        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(uid).child(user.getAddress()).child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri, context));

            fileReference.putFile(mImageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        Toast.makeText(context, "Upload successful", Toast.LENGTH_SHORT).show();
                        UploadImage upload = new UploadImage("Image", taskSnapshot.getStorage().getDownloadUrl().toString());
                        String uploadId = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(REAL_ESTATE).child(uid).child(user.getAddress()).child("Images").child(Objects.requireNonNull(uploadId)).setValue(upload);
                    })
                    .addOnFailureListener(e -> Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show())
                    .addOnProgressListener(taskSnapshot -> {
                        //double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        //mProgressBar.setProgress((int) progress);
                    });
        } else {
            Toast.makeText(context, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    public static void saveRealEstateAgent(Context context, RealEstateAgent notifyParam) {

        Gson gson = new Gson();
        String jsonCategoryList = gson.toJson(notifyParam);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.realEstateAgentProfile), jsonCategoryList);
        editor.apply();
    }

    public static RealEstateAgent getRealEstateAgent(Context context) {

        Gson gson = new Gson();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String json = sharedPreferences.getString(context.getString(R.string.realEstateAgentProfile), null);
        Type type = new TypeToken<RealEstateAgent>() {
        }.getType();
        return gson.fromJson(json, type);
    }
}


