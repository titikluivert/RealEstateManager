package com.openclassrooms.realestatemanager.controler.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.utils.mainUtils;

import java.io.File;

//import static com.openclassrooms.realestatemanager.utils.mainUtils.AUTHORITY;
import static com.openclassrooms.realestatemanager.utils.mainUtils.FILENAME;
import static com.openclassrooms.realestatemanager.utils.mainUtils.FOLDERNAME;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    protected Boolean isCurrentUserLogged() {
        return (this.getCurrentUser() != null);
    }

    protected OnFailureListener onFailureListener() {
        return e -> BaseActivity.this.showToast("error");
    }

    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    // ----------------------------------
    // SHARE FILE
    // ----------------------------------
   /* protected void shareFile() {
        File internalFile = mainUtils.getFileFromStorage(this.getFilesDir(), this, FILENAME, FOLDERNAME);
//        Uri contentUri = FileProvider.getUriForFile(this, AUTHORITY, internalFile);

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/*");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
        startActivity(Intent.createChooser(sharingIntent, getString(R.string.real_estate_info_share)));
    }*/
}
