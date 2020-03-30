
package com.openclassrooms.realestatemanager.controler.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Switch;

import androidx.appcompat.app.ActionBar;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.utils.mainUtils;

import java.util.Calendar;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SearchActivity extends BaseActivity {

    public static final String SEARCH_RESULT = "com.openclassrooms.realestatemanager.controler.activities.SEARCH_RESULT";

    private String filterType;

    // enablers
    @BindView(R.id.enableSearchBySurface)
    Switch enableSearchBySurface;

    @BindView(R.id.enableSearchByOnlineSince)
    Switch enableSearchByOnlineSince;

    @BindView(R.id.enableSearchByPrice)
    Switch enableSearchByPrice;

    @BindView(R.id.enableSearchBySaleSince)
    Switch enableSearchBySaleSince;

    @BindView(R.id.enableadresse)
    Switch enableAddress;

    @BindView(R.id.enablePhoto)
    Switch enablePhoto;

    // text

    @BindView(R.id.enddateTextOnlineSince)
    EditText enddateTextOnlineSince;

    @BindView(R.id.enddateTextSaleSince)
    EditText enddateTextSaleSince;

    @BindView(R.id.bigindateTextSurface)
    EditText bigindateTextSurface;

    @BindView(R.id.enddateTextSurface)
    EditText enddateTextSurface;

    @BindView(R.id.bigindateTextprice)
    EditText bigindateTextprice;

    @BindView(R.id.enddateTextPrice)
    EditText enddateTextPrice;


    @BindView(R.id.searchTextCity)
    EditText searchTextCity;

    @BindView(R.id.numberTextPhoto)
    EditText numberTextPhoto;

    private String[] filterTypeArray = {"BY_SURFACE", "BY_PRICE", "BY_AVAILABILITY", "BY_SALE", "BY_ADDRESS", "BY_PHOTOS"};
    final String tableIndex = "012345";

    private  int dateToday;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_notif_seach);
        ButterKnife.bind(this);
        Switch[] AllEnabler = {enableSearchBySurface, enableSearchByPrice, enableSearchByOnlineSince,
                               enableSearchBySaleSince, enableAddress, enablePhoto};
        this.configureToolbar();
        this.getFilterType(AllEnabler);
    }

    @OnClick(R.id.search_button)
    public void searchFcn() {
        this.searchButtonMethod();

    }

    // search by availability
    @OnClick(R.id.enddatepickerOnlineSince)
    public void availability() {
        this.datePickerMethod(enddateTextOnlineSince);
    }

    // search by sale since
    @OnClick(R.id.enddatepickerSaleSince)
    public void saleSince() {
        this.datePickerMethod(enddateTextSaleSince);
    }

    private void searchButtonMethod() {

        if (this.filterType != null) {

            switch (this.filterType) {

                case "BY_SURFACE":
                    String sqmBigin, sqmEnd;
                    if (bigindateTextSurface.getText().toString().trim().equals("") || bigindateTextSurface.getText() == null || enddateTextSurface.getText().toString().trim().equals("") || enddateTextSurface.getText() == null
                            || Float.parseFloat(bigindateTextSurface.getText().toString().trim()) > Float.parseFloat(enddateTextSurface.getText().toString().trim())) {
                        showToast("both surface must be selected Or first square meter should be greater than the second one");
                    } else {
                        sqmBigin = bigindateTextSurface.getText().toString();
                        sqmEnd = enddateTextSurface.getText().toString();
                    }
                    break;
                case "BY_PRICE":
                    String startPrice, endPrice;
                    if (bigindateTextprice.getText().toString().trim().equals("") || bigindateTextprice.getText() == null || enddateTextPrice.getText().toString().trim().equals("") || enddateTextPrice.getText() == null
                            || Float.parseFloat(bigindateTextprice.getText().toString().trim()) > Float.parseFloat(enddateTextPrice.getText().toString().trim())) {
                        showToast("both price must be entered Or end price should be greater than the start one");
                    } else {
                        startPrice = bigindateTextSurface.getText().toString();
                        endPrice = enddateTextSurface.getText().toString();
                    }
                    break;
                case "BY_AVAILABILITY":
                    String onlineSince;
                    dateToday = Integer.parseInt(mainUtils.getTodayDate().replace("/", "").trim());
                    if (enddateTextOnlineSince.getText().toString().trim().equals("") || enddateTextOnlineSince.getText() == null
                            || dateToday < Integer.parseInt(mainUtils.getConvertDate(enddateTextOnlineSince.getText().toString().trim()).replace("/", ""))) {
                        showToast("date must be entered Or date should not be in the future");
                    } else {
                        onlineSince = enddateTextOnlineSince.getText().toString();
                    }
                    break;
                case "BY_SALE":
                    String saleSince;
                    dateToday = Integer.parseInt(mainUtils.getTodayDate().replace("/", "").trim());
                    if (enableSearchBySaleSince.getText().toString().trim().equals("") || enableSearchBySaleSince.getText() == null
                            || dateToday < Integer.parseInt(mainUtils.getConvertDate(enddateTextOnlineSince.getText().toString().trim()).replace("/", ""))) {
                        showToast("date must be entered Or date should not be in the future");
                    } else {
                        saleSince = enableSearchBySaleSince.getText().toString();
                    }
                    break;
                case "BY_ADDRESS":
                    String address;
                    if (searchTextCity.getText().toString().trim().equals("") || searchTextCity.getText() == null) {
                        showToast("you must enter an address");
                    } else {
                        address = searchTextCity.getText().toString();
                    }
                    break;

                case "BY_PHOTOS":
                    String numOfPhotos;
                    if (numberTextPhoto.getText().toString().trim().equals("") || numberTextPhoto.getText() == null
                            || Integer.parseInt(numberTextPhoto.getText().toString().trim()) < 1) {
                        showToast("you must enter a number Or the number must be greater than zero");
                    } else {
                        numOfPhotos = numberTextPhoto.getText().toString();
                    }
                    break;

                default:
                    String result = "";
                    finishResult(result);
                    break;
            }
        }
        else {

            showToast("please first select a filter mode");
        }

    }

    private void finishResult(String param_config) {

        Intent resultIntent = new Intent();
        resultIntent.putExtra(SEARCH_RESULT, param_config);
        setResult(RESULT_OK, resultIntent);
        finish();

    }

    private void datePickerMethod(final EditText date_view) {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePickerDialog = new DatePickerDialog(SearchActivity.this, (view, myYear, myMonth, dayOfMonth) -> date_view.setText(dayOfMonth + "/" + (myMonth + 1) + "/" + myYear), year, month, day);
        mDatePickerDialog.show();
    }

    private void getFilterType(Switch[] allEnabler) {

        for (int i = 0; i < allEnabler.length; i++) {
            int finalI = i;
            allEnabler[i].setOnCheckedChangeListener((buttonView, isChecked) -> {
                String iString = String.valueOf(finalI);
                String tableIdx = tableIndex.replace(iString, "");
                char[] cArray = tableIdx.toCharArray();
                if (isChecked) {
                    this.filterType = filterTypeArray[finalI];
                    for (char c : cArray) {
                        allEnabler[Character.getNumericValue(c)].setChecked(false);
                    }
                }
            });
        }

    }

    private void configureToolbar() {
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        Objects.requireNonNull(ab).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}


