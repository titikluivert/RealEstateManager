
package com.openclassrooms.realestatemanager.controler.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Switch;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.utils.Utils;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SearchActivity extends BaseActivity {

    public static final String SEARCH_RESULT = "com.openclassrooms.realestatemanager.controler.activities.SEARCH_RESULT";
    public static final String regexS = "@";

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

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_notif_seach);
        ButterKnife.bind(this);
        Switch[] AllEnabler = {enableSearchBySurface, enableSearchByPrice, enableSearchByOnlineSince,
                enableSearchBySaleSince, enableAddress, enablePhoto};
        this.getFilterType(AllEnabler);
    }

    @OnClick(R.id.closeFormSearch)
    public void backToMain() {
        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
        startActivity(intent);
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
                        finishResult("BY_SURFACE" + regexS + sqmBigin + regexS + sqmEnd);
                    }
                    break;
                case "BY_PRICE":
                    String startPrice, endPrice;
                    if (bigindateTextprice.getText().toString().trim().equals("") || bigindateTextprice.getText() == null || enddateTextPrice.getText().toString().trim().equals("") || enddateTextPrice.getText() == null
                            || Float.parseFloat(bigindateTextprice.getText().toString().trim()) > Float.parseFloat(enddateTextPrice.getText().toString().trim())) {
                        showToast("both price must be entered Or end price should be greater than the start one");
                    } else {
                        startPrice = bigindateTextprice.getText().toString();
                        endPrice = bigindateTextprice.getText().toString();
                        finishResult("BY_PRICE" + regexS + startPrice + regexS + endPrice);
                    }
                    break;
                case "BY_AVAILABILITY":
                    String onlineSince;
                    int dateToday = Integer.parseInt(Utils.getTodayDate().replace("/", "").trim());
                    if (enddateTextOnlineSince.getText().toString().trim().equals("") || enddateTextOnlineSince.getText() == null
                            || dateToday < Integer.parseInt(Utils.getConvertDate(enddateTextOnlineSince.getText().toString().trim()).replace("/", ""))) {
                        showToast("date must be entered Or date should not be in the future");
                    } else {
                        onlineSince = "BY_AVAILABILITY" + regexS + Utils.getConvertDate(Utils.getConvertDate(enddateTextOnlineSince.getText().toString()));
                        finishResult(onlineSince);
                    }
                    break;
                case "BY_SALE":
                    String saleSince;
                    dateToday = Integer.parseInt(Utils.getTodayDate().replace("/", "").trim());
                    if (enddateTextSaleSince.getText().toString().trim().equals("") || enddateTextSaleSince.getText() == null
                            || dateToday < Integer.parseInt(Utils.getConvertDate(enddateTextSaleSince.getText().toString().trim()).replace("/", ""))) {
                        showToast("date must be entered Or date should not be in the future");
                    } else {
                        saleSince = "BY_SALE" + regexS + Utils.getConvertDate(Utils.getConvertDate(enddateTextSaleSince.getText().toString()));
                        finishResult(saleSince);
                    }
                    break;
                case "BY_ADDRESS":
                    String address;
                    if (searchTextCity.getText().toString().trim().equals("") || searchTextCity.getText() == null) {
                        showToast("you must enter an address");
                    } else {
                        address = "BY_ADDRESS" + regexS + searchTextCity.getText().toString();
                        finishResult(address);
                    }
                    break;

                case "BY_PHOTOS":
                    String numOfPhotos;
                    if (numberTextPhoto.getText().toString().trim().equals("") || numberTextPhoto.getText() == null
                            || Integer.parseInt(numberTextPhoto.getText().toString().trim()) < 1) {
                        showToast("you must enter a number Or the number must be greater than zero");
                    } else {
                        numOfPhotos = "BY_PHOTOS" + regexS + numberTextPhoto.getText().toString();
                        finishResult(numOfPhotos);
                    }

                    break;

                default:
                    String result = "";
                    finishResult(result);
                    break;
            }
        } else {

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


}


