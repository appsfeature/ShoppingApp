package com.appsfeature.global.network;

import com.dynamic.network.NetworkCallback;

public interface AppCallback {

    interface Callback<T> {
        void onSuccess(T response);
        void onFailure(Exception e);
        default void onProgressUpdate(Boolean isShow){}
        default void onRetry(NetworkCallback.Retry retryCallback){}
    }
}
