package com.footballpredictgame.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.android.volley.VolleyError;
import com.footballpredictgame.R;
import com.footballpredictgame.model.Model;
import com.footballpredictgame.model.UserAccountManager;
import com.footballpredictgame.network.NetworkRequest;
import com.footballpredictgame.network.NetworkRequestListener;

/**
 * Splash-screen Activity.
 * 
 */
public class SplashscreenActivity extends Activity implements
		NetworkRequestListener {

	/**
	 * The number of milliseconds to wait before hiding the activity.
	 */
	private static final int SPLASH_TIME_OUT = 1000;
	private boolean mTimedOut;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_splashscreen);
		mTimedOut = false;
		if (isAlreadyLoggedIn()) {
			UserAccountManager uam = new UserAccountManager(this);
			NetworkRequest.login(
					uam.getUserDetails(UserAccountManager.KEY_USER),
					uam.getUserDetails(UserAccountManager.KEY_PASSWORD), this);
		}
		new Handler().postDelayed(new Runnable() {

			/*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

			@Override
			public void run() {
				// This method will be executed once the timer is over
				// Start your app main activity
				Intent i = null;
				if (isAlreadyLoggedIn()) {
					/*
					 * if(mTimedOut){ i = new Intent(SplashscreenActivity.this,
					 * LoginActivity.class); startActivity(i); } else
					 * mTimedOut=true;
					 */
				} else {
					i = new Intent(SplashscreenActivity.this,
							LoginActivity.class);
					startActivity(i);
				}

				// close this activity
				finish();
			}
		}, SPLASH_TIME_OUT);
	}

	private boolean isAlreadyLoggedIn() {
		UserAccountManager uam = new UserAccountManager(this);
		String user = uam.getUserDetails(UserAccountManager.KEY_USER);
		if (user == null || user.length() == 0)
			return false;
		else
			return true;

	}

	@Override
	public void onResponse(Object obj) {
		Model.setCurrentUserId((Integer) obj);
		Intent i = new Intent(SplashscreenActivity.this, MainActivity.class);
		startActivity(i);

	}

	@Override
	public void onErrorResponse(VolleyError error, Object obj) {
		Intent i = new Intent(SplashscreenActivity.this, LoginActivity.class);
		startActivity(i);

	}

}
