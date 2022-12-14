package com.appsfeature.global.network;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.appsfeature.global.listeners.FilterType;
import com.appsfeature.global.model.AppBaseModel;
import com.appsfeature.global.model.CartModel;
import com.appsfeature.global.model.CategoryModel;
import com.appsfeature.global.model.CommonModel;
import com.appsfeature.global.model.ContentModel;
import com.appsfeature.global.model.FilterModel;
import com.appsfeature.global.model.OrderResponseEntity;
import com.appsfeature.global.model.RelativeModelEntity;
import com.appsfeature.global.model.UserModel;
import com.appsfeature.global.util.AppPreference;
import com.appsfeature.global.util.ListMaintainer;
import com.dynamic.DynamicModule;
import com.dynamic.listeners.ApiHost;
import com.dynamic.listeners.ApiRequestType;
import com.dynamic.listeners.DynamicCallback;
import com.dynamic.network.DMNetworkManager;
import com.dynamic.network.NetworkCallback;
import com.dynamic.network.NetworkModel;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.helper.callback.Response;
import com.helper.task.TaskRunner;
import com.helper.util.BaseConstants;
import com.helper.util.GsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import retrofit2.Call;

public class NetworkManager extends DMNetworkManager {
    private static NetworkManager instance;

    public NetworkManager(Context context) {
        super(context);
    }

    public static NetworkManager getInstance(Context context) {
        if (instance == null) {
            instance = new NetworkManager(context);
        }
        return instance;
    }

    public void getCommonData(String userId, final Response.Callback<List<CommonModel>> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        configManager.getData(ApiRequestType.GET, ApiEndPoint.DEFAULT, params, new NetworkCallback.Response<NetworkModel>() {
            @Override
            public void onComplete(boolean status, NetworkModel data) {
                try {
                    if(status && !TextUtils.isEmpty(data.getData())) {
                        List<CommonModel> list = data.getData(new TypeToken<List<CommonModel>>() {
                        });
                        if (list != null && list.size() > 0) {
                            callback.onSuccess(list);
                        } else {
                            callback.onFailure(new Exception(BaseConstants.NO_DATA));
                        }
                    }else {
                        callback.onFailure(new Exception(data != null ? data.getMessage() : BaseConstants.NO_DATA));
                    }
                } catch (JsonSyntaxException e) {
                    callback.onFailure(e);
                }
            }

            @Override
            public void onFailure(Call<NetworkModel> call, Exception e) {
                callback.onFailure(e);
            }
        });
    }
    public void getAppRelativeproduct(int subCatId,final DynamicCallback.Listener<RelativeModelEntity> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("subcategory_id", subCatId + "");
        configManager.getData(ApiRequestType.POST_FORM, ApiHost.HOST_MAIN, ApiEndPoint.GET_APP_Relative_Product, params, new NetworkCallback.Response<NetworkModel>() {
            @Override
            public void onComplete(boolean status, NetworkModel data) {
                try {
                    if (status && !TextUtils.isEmpty(data.getData())) {
                        RelativeModelEntity dat= data.getData(RelativeModelEntity.class);
                        if (dat != null && !dat.getUserData().isEmpty()) {
                            callback.onSuccess(dat);
                        } else {
                            callback.onFailure(new Exception(BaseConstants.NO_DATA));
                        }

                    }else {
                        callback.onFailure(new Exception(data != null ? data.getMessage() : BaseConstants.NO_DATA));
                    }
                } catch (JsonSyntaxException e) {
                    callback.onFailure(e);
                }
            }

            @Override
            public void onFailure(Call<NetworkModel> call, Exception e) {
                callback.onFailure(e);
            }

            @Override
            public void onRequestCompleted() {
                callback.onRequestCompleted();
            }
        });
    }


    public void login(String phone, final Response.Callback<NetworkModel> callback) {
        callback.onProgressUpdate(true);
        Map<String, String> params = new HashMap<>();
        params.put("phone", phone);
        configManager.getData(ApiRequestType.POST_FORM, ApiHost.HOST_MAIN, ApiEndPoint.GET_APP_USER_SIGNUP, params, new NetworkCallback.Response<NetworkModel>() {
            @Override
            public void onComplete(boolean status, NetworkModel data) {
                try {
                    if(status) {
                        callback.onSuccess(data);
                    }else {
                        callback.onFailure(new Exception(data != null ? data.getMessage() : BaseConstants.NO_DATA));
                    }
                } catch (JsonSyntaxException e) {
                    callback.onFailure(e);
                }
            }

            @Override
            public void onFailure(Call<NetworkModel> call, Exception e) {
                callback.onFailure(e);
            }
        });
    }

    public void verifyOtp(String phone, String otp, final Response.Callback<UserModel> callback) {
        callback.onProgressUpdate(true);
        Map<String, String> params = new HashMap<>();
        params.put("phone", phone);
        params.put("otp", otp);
        configManager.getData(ApiRequestType.POST_FORM, ApiHost.HOST_MAIN, ApiEndPoint.USER_MATCH_OTP, params, new NetworkCallback.Response<NetworkModel>() {
            @Override
            public void onComplete(boolean status, NetworkModel data) {
                try {
                    if (status && !TextUtils.isEmpty(data.getData())) {
                        AppBaseModel entity = data.getData(new TypeToken<AppBaseModel>() {
                        });
                        if (entity != null && entity.getUserData() != null && entity.getUserData().size() > 0) {
                            AppPreference.setProfile(entity.getUserData().get(0).toJson());
                            callback.onSuccess(entity.getUserData().get(0));
                        } else {
                            callback.onFailure(new Exception(BaseConstants.NO_DATA));
                        }
                    } else {
                        callback.onFailure(new Exception(data != null ? data.getMessage() : BaseConstants.NO_DATA));
                    }
                } catch (JsonSyntaxException e) {
                    callback.onFailure(e);
                }
            }

            @Override
            public void onFailure(Call<NetworkModel> call, Exception e) {
                callback.onFailure(e);
            }
        });
    }

    public void getAppDataUser(int genderId, int seasonId, DynamicCallback.Listener<List<CategoryModel>> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("category_id", genderId + "");
//        params.put("season_id", seasonId + "");
        configManager.getData(ApiRequestType.POST_FORM, ApiHost.HOST_MAIN, ApiEndPoint.GET_APP_DATA_USER, params, new NetworkCallback.Response<NetworkModel>() {
            @Override
            public void onComplete(boolean status, NetworkModel data) {
                try {
                    if (status && !TextUtils.isEmpty(data.getData())) {
                        AppBaseModel entity = data.getData(new TypeToken<AppBaseModel>() {
                        });
                        if (entity != null && entity.getList() != null && entity.getList().size() > 0) {
                            AppPreference.setImageUrl(entity.getImageUrl());
                            DynamicModule.getInstance().setImageBaseUrl(context, ApiHost.HOST_DEFAULT, entity.getImageUrl());
                            callback.onSuccess(entity.getList());
                        } else {
                            callback.onFailure(new Exception(BaseConstants.NO_DATA));
                        }
                    } else {
                        callback.onFailure(new Exception(data != null ? data.getMessage() : BaseConstants.NO_DATA));
                    }
                } catch (JsonSyntaxException e) {
                    callback.onFailure(e);
                }
            }

            @Override
            public void onFailure(Call<NetworkModel> call, Exception e) {
                callback.onFailure(e);
            }

            @Override
            public void onRequestCompleted() {
                callback.onRequestCompleted();
            }
        });
    }

    public void getAppProductBySubCategory(int catId, int subCatId, int pageId, Map<Integer, String> filterMap, DynamicCallback.Listener<AppBaseModel> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("category_id", catId + "");
        params.put("subcategory_id", subCatId + "");
        params.put("page_id", pageId + "");
        String colors = filterMap.get(FilterType.TYPE_COLOR);
        params.put("color_id", colors != null ? colors : "");

        String sizes = filterMap.get(FilterType.TYPE_SIZE);
        params.put("size_id", sizes != null ? sizes : "");

        configManager.getData(ApiRequestType.POST_FORM, ApiHost.HOST_MAIN, ApiEndPoint.GET_APP_PRODUCT_BY_SUBCATEGORY, params, new NetworkCallback.Response<NetworkModel>() {
            @Override
            public void onComplete(boolean status, NetworkModel data) {
                try {
                    if (status && !TextUtils.isEmpty(data.getData())) {
                        AppBaseModel entity = data.getData(new TypeToken<AppBaseModel>() {
                        });
                        if (entity != null && entity.getProductList() != null && entity.getProductList().size() > 0) {
                            AppPreference.setImageUrl(entity.getImageUrl());
                            DynamicModule.getInstance().setImageBaseUrl(context, ApiHost.HOST_DEFAULT, entity.getImageUrl());
                            callback.onSuccess(entity);
                        } else {
                            callback.onFailure(new Exception(BaseConstants.NO_DATA));
                        }
                    } else {
                        callback.onFailure(new Exception(data != null ? data.getMessage() : BaseConstants.NO_DATA));
                    }
                } catch (JsonSyntaxException e) {
                    callback.onFailure(e);
                }
            }

            @Override
            public void onFailure(Call<NetworkModel> call, Exception e) {
                callback.onFailure(e);
            }

            @Override
            public void onRequestCompleted() {
                callback.onRequestCompleted();
            }
        });
    }


    public void getAppProductDetails(int productId, DynamicCallback.Listener<ContentModel> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("product_id", productId + "");
        configManager.getData(ApiRequestType.POST_FORM, ApiHost.HOST_MAIN, ApiEndPoint.GET_APP_PRODUCT_DETAILS, params, new NetworkCallback.Response<NetworkModel>() {
            @Override
            public void onComplete(boolean status, NetworkModel data) {
                try {
                    if (status && !TextUtils.isEmpty(data.getData())) {
                        AppBaseModel entity = data.getData(new TypeToken<AppBaseModel>() {
                        });
                        if (entity != null && entity.getProductView() != null) {
                            AppPreference.setImageUrl(entity.getImageUrl());
                            DynamicModule.getInstance().setImageBaseUrl(context, ApiHost.HOST_DEFAULT, entity.getImageUrl());
                            callback.onSuccess(entity.getProductView());
                        } else {
                            callback.onFailure(new Exception(BaseConstants.NO_DATA));
                        }
                    } else {
                        callback.onFailure(new Exception(data != null ? data.getMessage() : BaseConstants.NO_DATA));
                    }
                } catch (JsonSyntaxException e) {
                    callback.onFailure(e);
                }
            }

            @Override
            public void onFailure(Call<NetworkModel> call, Exception e) {
                callback.onFailure(e);
            }

            @Override
            public void onRequestCompleted() {
                callback.onRequestCompleted();
            }
        });
    }

    public void getCountryCodes(DynamicCallback.Listener<List<CommonModel>> callback) {
        Map<String, String> params = new HashMap<>();
        configManager.getData(ApiRequestType.GET, ApiHost.HOST_MAIN, ApiEndPoint.GET_APP_COUNTRY_VIEW, params, new NetworkCallback.Response<NetworkModel>() {
            @Override
            public void onComplete(boolean status, NetworkModel data) {
                try {
                    if (status && !TextUtils.isEmpty(data.getData())) {
                        AppBaseModel entity = data.getData(new TypeToken<AppBaseModel>() {
                        });
                        if (entity != null && entity.getCountry() != null && entity.getCountry().size() > 0) {
                            callback.onSuccess(entity.getCountry());
                        } else {
                            callback.onFailure(new Exception(BaseConstants.NO_DATA));
                        }
                    } else {
                        callback.onFailure(new Exception(data != null ? data.getMessage() : BaseConstants.NO_DATA));
                    }
                } catch (JsonSyntaxException e) {
                    callback.onFailure(e);
                }
            }

            @Override
            public void onFailure(Call<NetworkModel> call, Exception e) {
                callback.onFailure(e);
            }

            @Override
            public void onRequestCompleted() {
                callback.onRequestCompleted();
            }
        });
    }

    public void getAttributeData(DynamicCallback.Listener<List<FilterModel>> callback) {
        Map<String, String> params = new HashMap<>();
        configManager.getData(ApiRequestType.GET, ApiHost.HOST_MAIN, ApiEndPoint.GET_APP_ATTRIBUTES, params, new NetworkCallback.Response<NetworkModel>() {
            @Override
            public void onComplete(boolean status, NetworkModel data) {
                try {
                    if (status && !TextUtils.isEmpty(data.getData())) {
                        AppBaseModel entity = data.getData(new TypeToken<AppBaseModel>() {
                        });
                        if (entity != null && entity.getAttributes() != null && entity.getAttributes().size() > 0) {
                            ListMaintainer.saveList(context, AppPreference.ATTRIBUTES, entity.getAttributes());
                            callback.onSuccess(entity.getAttributes());
                        } else {
                            callback.onFailure(new Exception(BaseConstants.NO_DATA));
                        }
                    } else {
                        callback.onFailure(new Exception(data != null ? data.getMessage() : BaseConstants.NO_DATA));
                    }
                } catch (JsonSyntaxException e) {
                    callback.onFailure(e);
                }
            }

            @Override
            public void onFailure(Call<NetworkModel> call, Exception e) {
                callback.onFailure(e);
            }

            @Override
            public void onRequestCompleted() {
                callback.onRequestCompleted();
            }
        });
    }

    public void getAddressData(DynamicCallback.Listener<List<UserModel>> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("customer_id", AppPreference.getCustomerId() + "");
        configManager.getData(ApiRequestType.POST_FORM, ApiHost.HOST_MAIN, ApiEndPoint.GET_ADDRESS, params, new NetworkCallback.Response<NetworkModel>() {
            @Override
            public void onComplete(boolean status, NetworkModel data) {
                try {
                    if (status && !TextUtils.isEmpty(data.getData())) {
                        AppBaseModel entity = data.getData(new TypeToken<AppBaseModel>() {
                        });
                        if (entity != null && entity.getUserData() != null && entity.getUserData().size() > 0) {
                            ListMaintainer.saveList(context, AppPreference.ATTRIBUTES, entity.getUserData());
                            callback.onSuccess(entity.getUserData());
                        } else {
                            callback.onFailure(new Exception(BaseConstants.NO_DATA));
                        }
                    } else {
                        callback.onFailure(new Exception(data != null ? data.getMessage() : BaseConstants.NO_DATA));
                    }
                } catch (JsonSyntaxException e) {
                    callback.onFailure(e);
                }
            }

            @Override
            public void onFailure(Call<NetworkModel> call, Exception e) {
                callback.onFailure(e);
            }

            @Override
            public void onRequestCompleted() {
                callback.onRequestCompleted();
            }
        });
    }

    public void addNewAddressData(Map<String, String> params, DynamicCallback.Listener<UserModel> callback) {
        configManager.getData(ApiRequestType.POST_FORM, ApiHost.HOST_MAIN, ApiEndPoint.ADDRESS_INSERT, params, new NetworkCallback.Response<NetworkModel>() {
            @Override
            public void onComplete(boolean status, NetworkModel data) {
                try {
                    if (status && !TextUtils.isEmpty(data.getData())) {
                        UserModel entity = data.getData(new TypeToken<UserModel>() {
                        });
                        if (entity != null) {
                            callback.onSuccess(entity);
                        } else {
                            callback.onFailure(new Exception(BaseConstants.NO_DATA));
                        }
                    } else {
                        callback.onFailure(new Exception(data != null ? data.getMessage() : BaseConstants.NO_DATA));
                    }
                } catch (JsonSyntaxException e) {
                    callback.onFailure(e);
                }
            }

            @Override
            public void onFailure(Call<NetworkModel> call, Exception e) {
                callback.onFailure(e);
            }

            @Override
            public void onRequestCompleted() {
                callback.onRequestCompleted();
            }
        });
    }


    public void updateProfile(Map<String, String> params, DynamicCallback.Listener<UserModel> callback) {
        configManager.getData(ApiRequestType.POST_FORM, ApiHost.HOST_MAIN, ApiEndPoint.EDIT_PROFILE, params, new NetworkCallback.Response<NetworkModel>() {
            @Override
            public void onComplete(boolean status, NetworkModel data) {
                try {
                    if (status && !TextUtils.isEmpty(data.getData())) {
                        AppBaseModel entity = data.getData(new TypeToken<AppBaseModel>() {
                        });
                        if (entity != null && entity.getUserData() != null && entity.getUserData().size() > 0) {
                            AppPreference.setProfile(entity.getUserData().get(0).toJson());
                            callback.onSuccess(entity.getUserData().get(0));
                        } else {
                            callback.onFailure(new Exception(BaseConstants.NO_DATA));
                        }
                    } else {
                        callback.onFailure(new Exception(data != null ? data.getMessage() : BaseConstants.NO_DATA));
                    }
                } catch (JsonSyntaxException e) {
                    callback.onFailure(e);
                }
            }

            @Override
            public void onFailure(Call<NetworkModel> call, Exception e) {
                callback.onFailure(e);
            }

            @Override
            public void onRequestCompleted() {
                callback.onRequestCompleted();
            }
        });
    }

    public void submitOrder(CartModel cartModel, AppCallback.Callback<Boolean> callback) {
        String jsonData = GsonParser.toJsonAll(cartModel, CartModel.class);
        if(TextUtils.isEmpty(jsonData)){
            callback.onFailure(new Exception("Empty jsonData"));
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("user_id", AppPreference.getCustomerId() + "");
        params.put("jsondata", jsonData);
        configManager.getData(ApiRequestType.POST_FORM, ApiHost.HOST_MAIN, ApiEndPoint.ORDER_SUBMIT, params, new NetworkCallback.Response<NetworkModel>() {
            @Override
            public void onComplete(boolean status, NetworkModel data) {
                try {
                    if (status) {
                        callback.onSuccess(status);
                    } else {
                        callback.onFailure(new Exception(data != null ? data.getMessage() : BaseConstants.NO_DATA));
                    }
                } catch (JsonSyntaxException e) {
                    callback.onFailure(e);
                }
            }

            @Override
            public void onFailure(Call<NetworkModel> call, Exception e) {
                callback.onFailure(e);
            }

            @Override
            public void onRetry(NetworkCallback.Retry retryCallback, Exception e) {
                callback.onRetry(retryCallback);
            }
        });
    }

    public void getOrderHistory(AppCallback.Callback<List<CartModel>> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("user_id", AppPreference.getCustomerId() + "");
        configManager.getData(ApiRequestType.POST_FORM, ApiHost.HOST_MAIN, ApiEndPoint.MY_ORDER, params, new NetworkCallback.Response<NetworkModel>() {
            @Override
            public void onComplete(boolean status, NetworkModel data) {
                try {
                    if (status && !TextUtils.isEmpty(data.getData())) {
                        List<OrderResponseEntity> entity = data.getData(new TypeToken<List<OrderResponseEntity>>() {
                        });
                        TaskRunner.getInstance().executeAsync(new Callable<List<CartModel>>() {
                            @Override
                            public List<CartModel> call() throws Exception {
                                List<CartModel> orders = new ArrayList<>();
                                if (entity != null && entity.size() > 0) {
                                    for (OrderResponseEntity item : entity){
                                        if(item.getOrderDetail() != null){
                                            orders.add(item.getOrderDetail());
                                        }
                                    }
                                }
                                return orders;
                            }
                        }, new TaskRunner.Callback<List<CartModel>>() {
                            @Override
                            public void onComplete(List<CartModel> result) {
                                if (result != null && result.size() > 0) {
                                    callback.onSuccess(result);
                                } else {
                                    callback.onFailure(new Exception(BaseConstants.NO_DATA));
                                }
                            }
                        });
                    } else {
                        callback.onFailure(new Exception(data != null ? data.getMessage() : BaseConstants.NO_DATA));
                    }
                } catch (JsonSyntaxException e) {
                    callback.onFailure(e);
                }
            }

            @Override
            public void onFailure(Call<NetworkModel> call, Exception e) {
                callback.onFailure(e);
            }

            @Override
            public void onRetry(NetworkCallback.Retry retryCallback, Exception e) {
                callback.onRetry(retryCallback);
            }
        });
    }
}
