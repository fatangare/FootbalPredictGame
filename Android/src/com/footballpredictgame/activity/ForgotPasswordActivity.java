package com.footballpredictgame.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.footballpredictgame.R;
import com.footballpredictgame.model.Model;
import com.footballpredictgame.network.NetworkRequest;
import com.footballpredictgame.network.NetworkRequestListener;
import com.footballpredictgame.utiity.Constant;
import com.footballpredictgame.utiity.Utility;

public class ForgotPasswordActivity extends Activity implements
		NetworkRequestListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot_password);
	}

	public void onSubmitClicked(View v) {
		EditText txtMobile = (EditText) findViewById(R.id.txtMobile);
		EditText txtEmail = (EditText) findViewById(R.id.txtEmail);

		String mobile = txtMobile.getText().toString().trim();
		String email = txtEmail.getText().toString().trim();

		boolean cancel = false;
		View focusView = null;

		// Reset errors.
		txtMobile.setError(null);
		txtEmail.setError(null);

		// Check for a valid mobile number.
		if (TextUtils.isEmpty(mobile)) {
			txtMobile.setError(getString(R.string.error_field_required));
			focusView = txtMobile;
			cancel = true;
		} else if (!Utility.isValidPhoneNumber(mobile)) {
			txtMobile.setError(getString(R.string.error_invalid_phone));
			focusView = txtMobile;
			cancel = true;
		}

		// Check for a valid email.
		if (TextUtils.isEmpty(email)) {
			txtEmail.setError(getString(R.string.error_field_required));
			if (focusView == null) {
				focusView = txtEmail;
				cancel = true;
			}
		} else if (!Utility.isValidEmail(email)) {
			txtEmail.setError(getString(R.string.error_invalid_email));
			if (focusView == null) {
				focusView = txtEmail;
				cancel = true;
			}
		}

		if (cancel) {
			// There was an error; focus the first form field with an error.
			focusView.requestFocus();
		} else {
			// Perform actual forgotpwd operation
			Utility.showBusyDialog(this, R.string.progress_login);
			NetworkRequest.forgotPassword(mobile, email, this);
		}

	}

	/**
	 * TODO Modify comments for onResponse overloading method Why are you
	 * overriding this method and what are you achieving here?
	 */
	@Override
	public void onResponse(Object obj) {
		Utility.hideBusyDialog();
		Log.v(Constant.TAG, "Forgot pwd success");
		Model.setCurrentUserId((Integer) obj);
	}

	/**
	 * TODO Modify comments for onErrorResponse overloading method Why are you
	 * overriding this method and what are you achieving here?
	 */
	@Override
	public void onErrorResponse(VolleyError error, Object obj) {
		Utility.hideBusyDialog();
		if (obj == null)
			Utility.showErrorMessage(error, this);
		else
			Log.v(Constant.TAG, "Forgot pwd failed");
	}

}
