package com.appsfeature.global.activity;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.appsfeature.global.AppApplication;
import com.appsfeature.global.R;
import com.appsfeature.global.model.PresenterModel;
import com.appsfeature.global.model.UserModel;
import com.appsfeature.global.network.NetworkManager;
import com.appsfeature.global.util.AppPreference;
import com.appsfeature.global.util.SupportUtil;
import com.appsfeature.global.util.WebViewHelper;
import com.dynamic.listeners.DynamicCallback;
import com.formbuilder.util.FieldValidation;
import com.helper.util.BaseConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NewAddressActivity extends BaseActivity {

    private EditText etInputName, etInputPhone, etInputPhoneAlternate, etInputAddress, etInputLocality, etInputCity
            , etInputState, etInputLandmark, etInputPinCode;

    private Button btnSubmit;
    private ProgressBar pbProgress;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_address);

        onInitializeUI();
        setUpToolBar(property.getTitle());
        loadData();
    }

    private void loadData() {


    }

    public void onInitializeUI() {
        etInputName = findViewById(R.id.et_input_name);
        etInputPhone = findViewById(R.id.et_input_phone);
        etInputPhoneAlternate = findViewById(R.id.et_input_phone_alternate);
        etInputAddress = findViewById(R.id.et_input_address);
        etInputLocality = findViewById(R.id.et_input_locality);
        etInputCity = findViewById(R.id.et_input_city);
        etInputState = findViewById(R.id.et_input_state);
        etInputLandmark = findViewById(R.id.et_input_landmark);
        etInputPinCode = findViewById(R.id.et_input_pincode);

        pbProgress = findViewById(R.id.pb_progress);

        btnSubmit = findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!FieldValidation.isEmpty(view.getContext(), etInputName)){
                    return;
                }
                if(!FieldValidation.isEmpty(view.getContext(), etInputPhone)){
                    return;
                }
                if(!FieldValidation.isEmpty(view.getContext(), etInputAddress)){
                    return;
                }
                if(!FieldValidation.isEmpty(view.getContext(), etInputPinCode)){
                    return;
                }
                submitAddress();
            }
        });
    }

    private void submitAddress() {
        onStartProgressBar();
        Map<String, String> params = new HashMap<>();
        params.put("user_id", AppPreference.getCustomerId() + "");
        params.put("customer_id", AppPreference.getCustomerId() + "");
        params.put("name",  etInputName.getText().toString());
        params.put("phone",  etInputPhone.getText().toString());
        params.put("alternate_number",  etInputPhoneAlternate.getText().toString());
        params.put("address",  etInputAddress.getText().toString());
        params.put("locality",  etInputLocality.getText().toString());
        params.put("city_id",  etInputCity.getText().toString());
        params.put("state_id",  etInputState.getText().toString());
        params.put("landmark",  etInputLandmark.getText().toString());
        params.put("pincode",  etInputPinCode.getText().toString());

        NetworkManager.getInstance(this).addNewAddressData(params, new DynamicCallback.Listener<UserModel>() {
            @Override
            public void onSuccess(UserModel response) {
                finish();
                AppApplication.getInstance().pushAddressUpdateCallbacks(response);
            }

            @Override
            public void onFailure(Exception e) {
                onStopProgressBar();
            }
        });
    }

    @Override
    public void onUpdateUI(PresenterModel response) {

    }

    @Override
    public void onErrorOccurred(Exception e) {

    }

    @Override
    public void onStartProgressBar() {
        btnSubmit.setVisibility(View.GONE);
        pbProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStopProgressBar() {
        btnSubmit.setVisibility(View.VISIBLE);
        pbProgress.setVisibility(View.GONE);
    }
}
