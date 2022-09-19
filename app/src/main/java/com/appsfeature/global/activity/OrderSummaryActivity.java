package com.appsfeature.global.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.appsfeature.global.AppApplication;
import com.appsfeature.global.R;
import com.appsfeature.global.dialog.AppDialog;
import com.appsfeature.global.model.CartModel;
import com.appsfeature.global.model.ErrorModel;
import com.appsfeature.global.model.PaymentModel;
import com.appsfeature.global.model.PresenterModel;
import com.appsfeature.global.model.UserModel;
import com.appsfeature.global.razorpay.PaymentStatus;
import com.appsfeature.global.razorpay.ShippingMethod;
import com.appsfeature.global.util.AppPreference;
import com.appsfeature.global.util.ClassUtil;
import com.helper.callback.Response;
import com.helper.task.TaskRunner;
import com.helper.util.BaseUtil;
import com.helper.util.GsonParser;
import com.razorpay.Checkout;
import com.razorpay.Order;
import com.razorpay.PayloadHelper;
import com.razorpay.PaymentResultListener;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class OrderSummaryActivity extends BaseActivity implements View.OnClickListener, PaymentResultListener {

    private static final String RAZOR_API_ID = "rzp_live_yt6vJlAj5GFsYq";
    private static final String RAZOR_SECRET = "rlGcXWddvpZwhzwT3nmwg91J";
    private View llNoData;

    private final List<UserModel> mAddressList = new ArrayList<>();
    private CartModel mCartModel;
    @ShippingMethod
    private String mShippingMethod;
    private String mBillingMethod;
    private TextView tvEmailId, tvShipTo;
    private RadioButton rbSameAsShipping;
    private UserModel mDiffBillingAddress;
    private Button btnComplete;
    private UserModel mShippingAddress;

    private final Checkout checkout = new Checkout();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
        Checkout.clearUserData(getApplicationContext());
        checkout.setKeyID(RAZOR_API_ID);

        initUi();
        loadUi(property.getCartModel());
    }

    private void initUi() {
        llNoData = findViewById(R.id.ll_no_data);
        tvEmailId = findViewById(R.id.tv_email_id);
        tvShipTo = findViewById(R.id.tv_ship_to);
        llNoData.setVisibility(View.GONE);
        RadioGroup rgBilling = findViewById(R.id.rg_billing_address);
        RadioButton rbCOD = findViewById(R.id.rb_cash_on_delivery);
        RadioButton rbPrepaid = findViewById(R.id.rb_prepaid);
        rbSameAsShipping = findViewById(R.id.rb_same_as_shipping);
        btnComplete = findViewById(R.id.btn_complete);

        mShippingAddress = AppPreference.getShippingAddress();
        updateAddress(mShippingAddress);

        if(!TextUtils.isEmpty(AppPreference.getEmailId())) {
            tvEmailId.setText(AppPreference.getEmailId());
        }

        rbCOD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                rbPrepaid.setChecked(!isChecked);
                if(isChecked){
                    mShippingMethod = ShippingMethod.COD;
                }
            }
        });
        rbPrepaid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                rbCOD.setChecked(!isChecked);
                if(isChecked){
                    mShippingMethod = ShippingMethod.PREPAID;
                }
            }
        });
        mBillingMethod = "SAME_AS_SHIPPING";
        rgBilling.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if(checkedId == R.id.rb_same_as_shipping){
                    mBillingMethod = "SAME_AS_SHIPPING";
                }else if(checkedId == R.id.rb_diff_bill_address){
                    mBillingMethod = "DIFF_BILL_ADDRESS";
                    AppDialog.openAddressDialog(OrderSummaryActivity.this, mAddressList, new Response.Callback<UserModel>() {
                        @Override
                        public void onSuccess(UserModel response) {
                            mDiffBillingAddress = response;
                        }

                        @Override
                        public void onFailure(Exception e) {
                            rbSameAsShipping.setChecked(true);
                        }
                    });
                }
            }
        });
        (findViewById(R.id.btn_complete)).setOnClickListener(this);
        (findViewById(R.id.btn_change_contact)).setOnClickListener(this);
        (findViewById(R.id.btn_change_shipping_address)).setOnClickListener(this);

    }



    private void loadUi(CartModel response) {
        this.mCartModel = response;
        if (mCartModel != null) {
            btnComplete.setText(getString(R.string.complete_your_order, mCartModel.getTotal()));
        }
    }

    @Override
    public void onUpdateUI(PresenterModel response) {

    }

    @Override
    public void onErrorOccurred(Exception e) {

    }

    @Override
    public void onStartProgressBar() {

    }

    @Override
    public void onStopProgressBar() {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_complete) {
            proceedToPay();
        } else if (view.getId() == R.id.btn_change_contact) {
//            if (mContentDetail != null) {
//                AppDialog.openSizeChart(this, mContentDetail.categoryId);
//            }
            AppDialog.openEmailId(this, new Response.Status<String>() {
                @Override
                public void onSuccess(String response) {
                    tvEmailId.setText(response);
                }
            });
        } else if (view.getId() == R.id.btn_change_shipping_address) {
            AppDialog.openAddressDialog(OrderSummaryActivity.this, mAddressList, new Response.Callback<UserModel>() {
                @Override
                public void onSuccess(UserModel response) {
                    AppPreference.setShippingAddress(response);
                    mShippingAddress = response;
                    updateAddress(response);
                }

                @Override
                public void onFailure(Exception e) {

                }
            });
        }
    }

    private void updateAddress(UserModel response) {
        if (tvShipTo != null && response != null) {
            tvShipTo.setText(response.getTitle() + "\nAddress : " + response.getAddress());
        }
    }

    private void proceedToPay() {
        if(TextUtils.isEmpty(AppPreference.getEmailId())){
            BaseUtil.showToast(this, "Please input your email id.");
            return;
        }
        if(mShippingAddress == null){
            BaseUtil.showToast(this, "Please select shipping address first.");
            return;
        }
        if(mCartModel == null){
            BaseUtil.showToast(this, "Invalid cart!");
            return;
        }
        if(TextUtils.isEmpty(mShippingMethod)){
            BaseUtil.showToast(this, "Please choose Shipping method.");
            return;
        }
        AppPreference.setBillingAddress(mDiffBillingAddress);
        String receiptNo = generateReceipt();
        if(mShippingMethod.equals(ShippingMethod.COD)){
            startCODProcess(receiptNo);
        }else {
            generateOrderId(receiptNo, new Response.Callback<Order>() {
                @Override
                public void onSuccess(Order response) {
                    openPaymentGateWay(mShippingAddress, response);
                }

                @Override
                public void onFailure(Exception e) {

                }
            });
        }
    }

    private void startCODProcess(String receiptNo) {
        PaymentModel paymentResponse = new PaymentModel();
        paymentResponse.setStatus(PaymentStatus.CASH_ON_DELIVERY);
        paymentResponse.setCreatedAt(System.currentTimeMillis());
        CartModel cartModel = mCartModel.getClone();
        cartModel.setStatus(PaymentStatus.CASH_ON_DELIVERY);
        cartModel.setReceipt(receiptNo);
        cartModel.setPaymentResponse(paymentResponse);
        ClassUtil.openActivityPaymentSuccess(this, property, cartModel);
    }

    private String generateReceipt() {
        return "SaintG"+ System.currentTimeMillis();
    }

    private void generateOrderId(String receiptNo, Response.Callback<Order> callback) {
        TaskRunner.getInstance().executeAsync(new Callable<Order>() {
            @Override
            public Order call() throws Exception {
                Order order = null;
                try {
                    RazorpayClient razorpay = new RazorpayClient(RAZOR_API_ID, RAZOR_SECRET);
                    JSONObject orderRequest = new JSONObject();
                    int amount = Math.round(mCartModel.getTotal() * 100);
                    orderRequest.put("amount", amount);
                    orderRequest.put("currency", "INR");
                    orderRequest.put("receipt", receiptNo);

                    order = razorpay.orders.create(orderRequest);
                } catch (RazorpayException | JSONException e) {
                    e.printStackTrace();
                    Log.e("@PaymentSuccess", e.toString());
                }
                return order;
            }
        }, new TaskRunner.Callback<Order>() {
            @Override
            public void onComplete(Order result) {
                callback.onSuccess(result);
            }
        });
    }


    private void openPaymentGateWay(UserModel shippingAddress, Order response) {
        try {
            PayloadHelper payloadHelper = new PayloadHelper("INR", response.get("amount"), response.get("id"));
            payloadHelper.setName(shippingAddress.getTitle());
            payloadHelper.setDescription(response.get("receipt"));
            payloadHelper.setColor("#000000");
            payloadHelper.setRetryEnabled(true);
            payloadHelper.setRetryMaxCount(1);
            payloadHelper.setSendSmsHash(true);
            payloadHelper.setPrefillName(shippingAddress.getTitle());
            payloadHelper.setPrefillContact(shippingAddress.getPhone());
            payloadHelper.setPrefillEmail(AppPreference.getEmailId());
            JSONObject options = payloadHelper.getJson();

            if (mCartModel != null) {
                mCartModel.setOrderId(response.get("id"));
                mCartModel.setReceipt(response.get("receipt"));
            }

            checkout.setImage(R.drawable.ic_logo);
            checkout.open(this, options);
        } catch (Exception e) {
            if(AppApplication.getInstance().isDebugMode()) {
                Log.e("@PaymentSuccess", e.toString());
            }
        }
    }
    @Override
    public void onPaymentSuccess(String s) {
        if(AppApplication.getInstance().isDebugMode()) {
            Log.e("@PaymentSuccess", s);
        }
        try {
            PaymentModel paymentResponse = GsonParser.getGson().fromJson(s, PaymentModel.class);
            if(paymentResponse != null) {
                CartModel cartModel = mCartModel.getClone();
                cartModel.setPaymentResponse(paymentResponse);
                ClassUtil.openActivityPaymentSuccess(this, property, cartModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPaymentError(int i, String s) {
        if(AppApplication.getInstance().isDebugMode()) {
            Log.e("@PaymentError", "ErrorCode: " + i + "  ErrorMsg: " + s);
        }
        String message = s;
        try {
            ErrorModel errorModel = GsonParser.fromJsonAll(s, ErrorModel.class);
            if(errorModel != null && errorModel.getError() != null) {
                message = errorModel.getError().getDescription();
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = e.toString();
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if( id == android.R.id.home ){
            onBackPressed();
            return true ;
        }
        return super.onOptionsItemSelected(item);
    }
}
