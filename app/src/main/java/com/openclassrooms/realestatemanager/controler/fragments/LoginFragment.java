package com.openclassrooms.realestatemanager.controler.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.openclassrooms.realestatemanager.R;

import java.util.Collections;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class LoginFragment extends BaseFragment {

    // 1 - Identifier for Sign-In Activity
    private static final int RC_SIGN_IN = 123;

    @BindView(R.id.phone_number_SignIn)
    EditText phoneNumber;

    @BindView(R.id.phoneBar)
    ProgressBar phoneBar;

    public LoginFragment() {
        // Required empty public constructor
    }

    private String TAG = "Test SMS Phone verification";
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        //this.firebaseUserSearch();
        return view;
    }


    @OnClick(R.id.googleSign)
    void setGoogleSignIn() {
        startSignInActivity(new AuthUI.IdpConfig.GoogleBuilder().build(), "Signing in with Google ...");
    }

    @OnClick(R.id.signInBtn)
    void setLoginButton() {

    }

    @OnClick(R.id.facebookSign)
    void setRegisterButton() {
        startSignInActivity(new AuthUI.IdpConfig.FacebookBuilder().build(), "Signing in with Facebook ...");
    }

    // 2 - Launch Sign-In Activity
    private void startSignInActivity(AuthUI.IdpConfig providers, String msgSign) {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Collections.singletonList(providers)) // SUPPORT GOOGLE AND FACEBOOK
                        .setIsSmartLockEnabled(false, true)
                        .build(),
                RC_SIGN_IN);
        showProgress(getContext(), msgSign);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 4 - Handle SignIn Activity response on activity result
        this.handleResponseAfterSignIn(requestCode, resultCode, data);
    }

    // 3 - Method that handles response after SignIn Activity close

    private void handleResponseAfterSignIn(int requestCode, int resultCode, Intent data) {

        IdpResponse response = IdpResponse.fromResultIntent(data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) { // SUCCESS
                //showSnackBar(getWindow().getDecorView().getRootView(), getString(R.string.connection_succeed));
                this.onLoginSuccess();
            } else { // ERRORS
                if (response == null) {
                    // showSnackBar(getWindow().getDecorView().getRootView(), getString(R.string.error_authentication_canceled));
                } else if (Objects.requireNonNull(response.getError()).getErrorCode() == ErrorCodes.NO_NETWORK) {
                    // showSnackBar(getWindow().getDecorView().getRootView(), getString(R.string.error_no_internet));
                } else if (response.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    //showSnackBar(getWindow().getDecorView().getRootView(), getString(R.string.error_unknown_error));
                }
            }
        }
    }

    private void onLoginSuccess() {
        this.replaceFragment(new EstateFragment());

    }

    private void showProgress(Context ctx, String msg) {


        int llPadding = 50;
        LinearLayout ll = new LinearLayout(ctx);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.setPadding(llPadding, llPadding, llPadding, llPadding);
        ll.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams llParam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        llParam.gravity = Gravity.CENTER;
        ll.setLayoutParams(llParam);

        ProgressBar progressBar = new ProgressBar(ctx);
        progressBar.setIndeterminate(true);
        progressBar.setPadding(0, 0, llPadding, 0);
        progressBar.setLayoutParams(llParam);

        llParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        llParam.gravity = Gravity.CENTER;
        TextView tvText = new TextView(ctx);
        tvText.setText(msg);
        tvText.setTextColor(Color.parseColor("#000000"));
        tvText.setTextSize(14);
        tvText.setLayoutParams(llParam);

        ll.addView(progressBar);
        ll.addView(tvText);

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setCancelable(true);
        builder.setView(ll);

        AlertDialog dialog = builder.create();
        dialog.show();
        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(dialog.getWindow().getAttributes());
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setAttributes(layoutParams);

        }



    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
    }

    //When you press verify code button
    public void verifyFunction(String code) {

        //codeBar.setVisibility(View.VISIBLE);
        //String code = codeText.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        signInWithPhoneAuthCredential(credential);

    }

}
