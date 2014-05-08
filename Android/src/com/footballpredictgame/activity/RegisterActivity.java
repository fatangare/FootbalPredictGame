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

public class RegisterActivity extends Activity implements
		NetworkRequestListener {
	private EditText mTxtMobile;
	private EditText mTxtEmail;
	private EditText mTxtPwd;
	private EditText mTxtRePwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		mTxtMobile = (EditText) findViewById(R.id.txtMobile);
		mTxtEmail = (EditText) findViewById(R.id.txtEmail);
		mTxtPwd = (EditText) findViewById(R.id.txtPwd);
		mTxtRePwd = (EditText) findViewById(R.id.txtRePwd);
	}

	public void onRegisterClicked(View v) {
		String mobile = mTxtMobile.getText().toString().trim();
		String email = mTxtEmail.getText().toString().trim();
		String pwd = mTxtPwd.getText().toString().trim();
		String rePwd = mTxtRePwd.getText().toString().trim();

		boolean cancel = false;
		View focusView = null;

		// Reset errors.
		mTxtMobile.setError(null);
		mTxtEmail.setError(null);
		mTxtPwd.setError(null);
		mTxtRePwd.setError(null);

		// Check for a valid mobile number.
		if (TextUtils.isEmpty(mobile)) {
			mTxtMobile.setError(getString(R.string.error_field_required));
			focusView = mTxtMobile;
			cancel = true;
		} else if (!Utility.isValidPhoneNumber(mobile)) {
			mTxtMobile.setError(getString(R.string.error_invalid_phone));
			focusView = mTxtMobile;
			cancel = true;
		}

		// Check for a valid email.
		if (TextUtils.isEmpty(email)) {
			mTxtEmail.setError(getString(R.string.error_field_required));
			if (focusView == null) {
				focusView = mTxtEmail;
				cancel = true;
			}
		} else if (!Utility.isValidEmail(email)) {
			mTxtEmail.setError(getString(R.string.error_invalid_email));
			if (focusView == null) {
				focusView = mTxtEmail;
				cancel = true;
			}
		}

		// Check for a valid password.
		if (TextUtils.isEmpty(pwd)) {
			mTxtPwd.setError(getString(R.string.error_field_required));
			if (focusView == null) {
				focusView = mTxtPwd;
				cancel = true;
			}
		}

		// Check for a valid password.
		if (TextUtils.isEmpty(rePwd)) {
			mTxtRePwd.setError(getString(R.string.error_field_required));
			if (focusView == null) {
				focusView = mTxtRePwd;
				cancel = true;
			}
		} else if (!pwd.equals(rePwd)) {
			mTxtRePwd.setError(getString(R.string.error_pwd_mismatch));
			if (focusView == null) {
				focusView = mTxtRePwd;
				cancel = true;
			}
		}
		if (cancel) {
			// There was an error; don't attempt to register and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Perform actual signin operation

			Utility.showBusyDialog(this, R.string.progress_login);
			NetworkRequest.register(mobile, email, pwd, this);
		}

	}

	/**
	 * TODO Modify comments for onResponse overloading method Why are you
	 * overriding this method and what are you achieving here?
	 */
	@Override
	public void onResponse(Object obj) {
		Utility.hideBusyDialog();
		Log.v(Constant.TAG, "Register success");
		Model.setCurrentUserId((String) obj);
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
			Log.v(Constant.TAG, "Register failed");
	}

}
