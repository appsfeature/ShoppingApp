package com.appsfeature.global.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.appsfeature.global.R;
import com.appsfeature.global.login.LoginListener;
import com.appsfeature.global.util.AppPreference;
import com.appsfeature.global.util.ClassUtil;
import com.helper.util.DayNightPreference;


public class SplashScreen extends Activity {

	private static final int SPLASH_TIME_OUT = 500;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		DayNightPreference.setNightMode(this, false);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		onLoginOpen();
	}

	public void onLoginOpen() {
		if(!AppPreference.isLoginCompleted()){
			ClassUtil.openLoginActivity(this, new LoginListener() {
				@Override
				public void onLoginComplete() {
					startMainActivity();
				}
			});
			finishAll();
		}else {
			startMainActivity();
		}
	}

	private void startMainActivity() {
		ClassUtil.openHomeActivity(this);
		finishAll();
	}

	private void finishAll() {
		new Handler(Looper.myLooper()).postDelayed(new Runnable() {
			@Override
			public void run() {
				try {
					finishAffinity();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, SPLASH_TIME_OUT);
	}
}
