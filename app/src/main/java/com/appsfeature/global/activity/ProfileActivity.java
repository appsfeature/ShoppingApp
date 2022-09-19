package com.appsfeature.global.activity;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.appsfeature.global.AppApplication;
import com.appsfeature.global.R;
import com.appsfeature.global.dialog.AppDialog;
import com.appsfeature.global.model.PresenterModel;
import com.appsfeature.global.model.UserModel;
import com.appsfeature.global.network.NetworkManager;
import com.appsfeature.global.util.AppPreference;
import com.dynamic.listeners.DynamicCallback;
import com.formbuilder.util.FieldValidation;
import com.helper.callback.Response;
import com.helper.util.BaseUtil;

import java.util.HashMap;
import java.util.Map;


public class ProfileActivity extends AppCompatActivity {

    private EditText etInputName, etInputPhone, etInputEmailId;

    private Button btnSubmit;
    private ProgressBar pbProgress;
    private String mGender;
    private RadioButton rbMale, rbFemale;
    private View bottomBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        onInitializeUI();
        setUpToolBar("Profile");
        loadData();
    }

    private void loadData() {
        UserModel userModel = AppPreference.getProfileModel();
        if(userModel != null){
            etInputName.setText(userModel.getTitle());
            etInputPhone.setText(userModel.getPhone());
            etInputEmailId.setText(userModel.getEmail());
            if(!TextUtils.isEmpty(userModel.getGender())){
                if(userModel.getGender().equalsIgnoreCase("female")){
                    rbFemale.setChecked(true);
                }else {
                    rbMale.setChecked(true);
                }
            }
            enableEditOption(false);
        }else {
            enableEditOption(true);
        }
    }

    public void onInitializeUI() {
        etInputName = findViewById(R.id.et_input_name);
        etInputPhone = findViewById(R.id.et_input_phone);
        etInputEmailId = findViewById(R.id.et_input_email);
        bottomBar = findViewById(R.id.bottom_bar);

        RadioGroup rgGender = findViewById(R.id.rg_gender);
        rbMale = findViewById(R.id.rb_male);
        rbFemale = findViewById(R.id.rb_female);
        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if(checkedId == R.id.rb_male){
                    mGender = "male";
                }else if(checkedId == R.id.rb_female){
                    mGender = "female";
                }
            }
        });

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
                if(!FieldValidation.isEmpty(view.getContext(), etInputEmailId)){
                    return;
                }
                if(!FieldValidation.isEmpty(view.getContext(), mGender, "Please select gender")){
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
        params.put("email_id",  etInputEmailId.getText().toString());
        params.put("email_verify",  "");
        params.put("gender",  mGender);

        NetworkManager.getInstance(this).updateProfile(params, new DynamicCallback.Listener<UserModel>() {
            @Override
            public void onSuccess(UserModel response) {
                AppPreference.setEmailId(etInputEmailId.getText().toString());
                enableEditOption(false);
                BaseUtil.showToast(ProfileActivity.this, "Profile updated successfully!");
            }

            @Override
            public void onFailure(Exception e) {
                onStopProgressBar();
            }
        });
    }


    public void onStartProgressBar() {
        btnSubmit.setVisibility(View.GONE);
        pbProgress.setVisibility(View.VISIBLE);
    }

    public void onStopProgressBar() {
        btnSubmit.setVisibility(View.VISIBLE);
        pbProgress.setVisibility(View.GONE);
    }


    protected void setUpToolBar(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            if (!TextUtils.isEmpty(title)) {
                actionBar.setTitle(title);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (id == R.id.menu_edit) {
            enableEditOption(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void enableEditOption(boolean isEditable) {
        etInputPhone.setEnabled(false);
        etInputPhone.setAlpha(0.5f);
        if(isEditable) {
            etInputName.setEnabled(true);
            etInputEmailId.setEnabled(true);
            rbMale.setEnabled(true);
            rbFemale.setEnabled(true);
            bottomBar.setVisibility(View.VISIBLE);
            btnSubmit.setText("Update");
        }else {
            etInputName.setEnabled(false);
            etInputEmailId.setEnabled(false);
            rbMale.setEnabled(false);
            rbFemale.setEnabled(false);

            bottomBar.setVisibility(View.GONE);
        }
    }
}
