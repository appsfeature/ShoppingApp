package com.appsfeature.global.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appsfeature.global.R;
import com.appsfeature.global.adapter.FilterAdapter;
import com.appsfeature.global.adapter.FilterChildAdapter;
import com.appsfeature.global.adapter.app.AddressAdapter;
import com.appsfeature.global.listeners.GenderType;
import com.appsfeature.global.listeners.SeasonType;
import com.appsfeature.global.model.FilterModel;
import com.appsfeature.global.model.UserModel;
import com.appsfeature.global.network.AppDataManager;
import com.appsfeature.global.util.AppPreference;
import com.appsfeature.global.util.AppSelectionSwitch;
import com.appsfeature.global.util.ClassUtil;
import com.dynamic.listeners.DynamicCallback;
import com.helper.callback.Response;
import com.helper.util.BaseUtil;

import java.util.ArrayList;
import java.util.List;

public class AppDialog {

    public static void openFilterHome(Activity activity, Response.Status<Boolean> callback) {
        try {
            Dialog dialog = new Dialog(activity, R.style.DialogThemeFullScreen);
            dialog.setCancelable(false);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            dialog.setContentView(R.layout.dialog_filter_home);

            View filterFemale = dialog.findViewById(R.id.ll_filter_female);
            View filterMale = dialog.findViewById(R.id.ll_filter_male);
            AppSelectionSwitch swGender = new AppSelectionSwitch(filterFemale, filterMale)
                    .setSelected(AppPreference.getGender() == GenderType.TYPE_GIRL);

            View filterWinter = dialog.findViewById(R.id.ll_filter_winter);
            View filterSummer = dialog.findViewById(R.id.ll_filter_summer);
            AppSelectionSwitch swSeason = new AppSelectionSwitch(filterWinter, filterSummer)
                    .setSelected(AppPreference.getSeason() == SeasonType.TYPE_WINTER);

            swGender.addSwitchChangeListener(new AppSelectionSwitch.Listener() {
                @Override
                public void onSwitchChanged(boolean isFirstChecked) {
                    dialog.getWindow().getDecorView().post(new Runnable() {
                        @Override
                        public void run() {
                            AppPreference.setGender(swGender.isFirstSelected() ? GenderType.TYPE_GIRL : GenderType.TYPE_BOY);
                            dismissDialog(dialog);
                            callback.onSuccess(true);
                        }
                    });
                }
            });
            dialog.findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppPreference.setGender(swGender.isFirstSelected() ? GenderType.TYPE_GIRL : GenderType.TYPE_BOY);
                    AppPreference.setSeason(swSeason.isFirstSelected() ? SeasonType.TYPE_WINTER : SeasonType.TYPE_SUMMER);
                    dismissDialog(dialog);
                    callback.onSuccess(true);
                }
            });

            dialog.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismissDialog(dialog);
                }
            });
            dialog.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismissDialog(dialog);
                }
            });
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openQuantity(Context context, Response.Status<String> callback) {
        try {
            Dialog dialog = new Dialog(context, R.style.DialogThemeFullScreen);
            dialog.setCancelable(false);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            dialog.setContentView(R.layout.dialog_quantity);

            EditText etQuantity = dialog.findViewById(R.id.et_quantity);

            dialog.findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!TextUtils.isEmpty(etQuantity.getText())) {
                        dismissDialog(dialog);
                        callback.onSuccess(etQuantity.getText().toString());
                    }else {
                        BaseUtil.showToast(context, "Quantity is invalid.");
                    }
                }
            });

            dialog.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismissDialog(dialog);
                }
            });
            dialog.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismissDialog(dialog);
                }
            });
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openEmailId(Context context, Response.Status<String> callback) {
        try {
            Dialog dialog = new Dialog(context, R.style.DialogThemeFullScreen);
            dialog.setCancelable(false);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            dialog.setContentView(R.layout.dialog_email_id);

            EditText etEmailId = dialog.findViewById(R.id.et_email_id);
            etEmailId.setText(AppPreference.getEmailId());
            dialog.findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!TextUtils.isEmpty(etEmailId.getText())) {
                        AppPreference.setEmailId(etEmailId.getText().toString());
                        dismissDialog(dialog);
                        callback.onSuccess(etEmailId.getText().toString());
                    }else {
                        BaseUtil.showToast(context, "Email is invalid.");
                    }
                }
            });

            dialog.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismissDialog(dialog);
                }
            });
            dialog.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismissDialog(dialog);
                }
            });
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openSizeChart(Context context, int type) {
        try {
            Dialog dialog = new Dialog(context, R.style.DialogThemeFullScreen);
            dialog.setCancelable(false);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            dialog.setContentView(R.layout.dialog_size_chart);

            ImageView ivImage = dialog.findViewById(R.id.iv_image);

            ivImage.setImageResource(type == 2 ? R.drawable.ic_size_chart_mens : R.drawable.ic_size_chart_womens);

            dialog.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismissDialog(dialog);
                }
            });
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void dismissDialog(Dialog mDialog) {
        try {
            if ((mDialog != null) && mDialog.isShowing()) {
                mDialog.dismiss();
                mDialog = null;
            }
        } catch (final Exception e) {
        }
    }

    public static void openFilterProduct(Activity activity, List<FilterModel> mList, Response.Status<List<FilterModel>> callback) {
        try {
            Dialog dialog = new Dialog(activity, R.style.DialogThemeFullScreen);
            dialog.setCancelable(false);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            dialog.setContentView(R.layout.dialog_filter_list);

            View llNoData = dialog.findViewById(R.id.ll_no_data);
            RecyclerView recyclerView = dialog.findViewById(R.id.recycler_view);
            RadioGroup radioGroup=dialog.findViewById(R.id.cat_rg);
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if(checkedId==R.id.color){
                        FilterChildAdapter adapter = new FilterChildAdapter(0,mList.get(0).getAttributesIds());
                        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                    else if(checkedId==R.id.size){
                        FilterChildAdapter adapter = new FilterChildAdapter(0,mList.get(1).getAttributesIds());
                        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
            });

            /*FilterAdapter adapter = new FilterAdapter(mList);
            recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);*/
            recyclerView.setVisibility(View.VISIBLE);
            if(mList.size() <= 0) {
                AppDataManager.get(activity).getAttributeData(new DynamicCallback.Listener<List<FilterModel>>() {
                    @Override
                    public void onSuccess(List<FilterModel> response) {
                        if (response != null && response.size() > 0) {
                            mList.clear();
                            mList.addAll(response);

                            BaseUtil.showNoData(llNoData, View.GONE);
                        } else {
                            recyclerView.setVisibility(View.GONE);
                            BaseUtil.showNoData(llNoData, View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Exception e) {
                        if (recyclerView.getAdapter() == null) {
                            BaseUtil.showNoData(llNoData, View.VISIBLE);
                        }
                    }
                });
            }else {
                BaseUtil.showNoData(llNoData, View.GONE);
            }

            dialog.findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismissDialog(dialog);
                    callback.onSuccess(mList);
                }
            });

            dialog.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismissDialog(dialog);
                }
            });
            dialog.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismissDialog(dialog);
                }
            });
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openAddressDialog(Activity activity, List<UserModel> mList, Response.Callback<UserModel> callback) {
        try {
            Dialog dialog = new Dialog(activity, R.style.DialogThemeFullScreen);
            dialog.setCancelable(false);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            dialog.setContentView(R.layout.dialog_address_list);

            View llNoData = dialog.findViewById(R.id.ll_no_data);
            RecyclerView recyclerView = dialog.findViewById(R.id.recycler_view);

            AddressAdapter adapter = new AddressAdapter(mList, new Response.OnClickListener<UserModel>() {
                @Override
                public void onItemClicked(View view, UserModel item) {
                    dismissDialog(dialog);
                    callback.onSuccess(item);
                }
            });
            recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
            recyclerView.setVisibility(View.VISIBLE);

            AppDataManager.get(activity).getAddressData(new DynamicCallback.Listener<List<UserModel>>() {
                @Override
                public void onSuccess(List<UserModel> response) {
                    if (response != null && response.size() > 0) {
                        mList.clear();
                        mList.addAll(response);
                        adapter.notifyDataSetChanged();
                        BaseUtil.showNoData(llNoData, View.GONE);
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        BaseUtil.showNoData(llNoData, View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Exception e) {
                    if (recyclerView.getAdapter() == null) {
                        BaseUtil.showNoData(llNoData, View.VISIBLE);
                    }
                }
            });

            dialog.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onFailure(new Exception("Close"));
                    dismissDialog(dialog);
                }
            });
            dialog.findViewById(R.id.btn_add_new).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismissDialog(dialog);
                    ClassUtil.openActivityAddAddress(activity, callback);
                }
            });
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
