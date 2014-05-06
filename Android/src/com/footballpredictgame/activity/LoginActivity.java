package com.footballpredictgame.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.footballpredictgame.R;
import com.footballpredictgame.model.Model;
import com.footballpredictgame.model.UserAccountManager;
import com.footballpredictgame.network.NetworkRequest;
import com.footballpredictgame.network.NetworkRequestListener;
import com.footballpredictgame.utiity.Constant;
import com.footballpredictgame.utiity.Utility;

/**
 * LoginActivity is used to create Login view.
 * 
 * @author Accenture India
 */
public class LoginActivity extends Activity implements NetworkRequestListener {

	// UI references.
	private EditText mUserView;
	private EditText mPasswordView;
	private CheckBox mRememberMeView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setting default screen to login.xml
		setContentView(R.layout.activity_login);

		// Set up the login form.
		mUserView = (EditText) findViewById(R.id.user);

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin(null);
							return true;
						}
						return false;
					}
				});

		// Remember Me
		mRememberMeView = (CheckBox) findViewById(R.id.remember_me);
		mRememberMeView.setSelected(true);

		UserAccountManager uam = new UserAccountManager(this);
		mUserView.setText(uam.getUserDetails(UserAccountManager.KEY_USER));
		mPasswordView.setText(uam
				.getUserDetails(UserAccountManager.KEY_PASSWORD));
		mRememberMeView.setChecked(!TextUtils.isEmpty(uam
				.getUserDetails(UserAccountManager.KEY_USER)));
	}

	/**
	 * Attempts to log in the account specified by the login form. If there are
	 * form errors (missing fields, etc.), the errors are presented and no
	 * actual login attempt is made.
	 * 
	 * @param view
	 *            Login button control
	 */
	public void attemptLogin(View view) {
		// Reset errors.
		mUserView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		String user = mUserView.getText().toString().trim();
		String password = mPasswordView.getText().toString().trim();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid user.
		if (TextUtils.isEmpty(user)) {
			mUserView.setError(getString(R.string.error_field_required));
			if (focusView == null) {
				focusView = mUserView;
				cancel = true;
			}
		} else if (!Utility.isValidPhoneNumber(user)) {
			mUserView.setError(getString(R.string.error_invalid_phone));
			focusView = mUserView;
			cancel = true;
		}

		// Check for a valid password.
		if (TextUtils.isEmpty(password)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			if (focusView == null) {
				focusView = mPasswordView;
				cancel = true;
			}
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Perform actual login operation

			Utility.showBusyDialog(this, R.string.progress_login);
			NetworkRequest.login(user, password, this);
		}
	}

	/**
	 * onForgotPasswordClick is called when 'Forgot Password' text-view is
	 * clicked.
	 * 
	 * @param view
	 *            - 'Forgot Password' text-view.
	 */
	public void onForgotPasswordClick(View view) {
		// Forgot password functionality
		Toast.makeText(this, "Call to 'Forgot Password' activity",
				Toast.LENGTH_LONG).show();
	}

	public void onSignInClicked(View view) {
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
	}

	@Override
	public void onResponse(Object obj) {
		Utility.hideBusyDialog();
		Log.v(Constant.TAG, "Login succes");
		Model.setCurrentUserId((Integer) obj);
		// if login is successful and 'remember me' is selected, store user
		// details in shared preferences
		UserAccountManager uam = new UserAccountManager(this);
		if (mRememberMeView.isChecked()) {
			uam.setUserDetails(UserAccountManager.KEY_USER, mUserView.getText()
					.toString().trim());
			uam.setUserDetails(UserAccountManager.KEY_PASSWORD, mPasswordView
					.getText().toString().trim());
		} else {
			uam.setUserDetails("", "");
		}
		uam.saveAsync();
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
	}

	@Override
	public void onErrorResponse(VolleyError error, Object obj) {
		Utility.hideBusyDialog();
		if (obj == null)
			Utility.showErrorMessage(error, this);
		else {
			Log.v(Constant.TAG, "Login failed");
			Utility.showAlertDialog(this, R.string.title_alert_error,
					R.string.error_invalid_login,
					android.R.drawable.ic_dialog_alert);
		}
	}
}
