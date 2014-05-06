package com.footballpredictgame.utiity;

import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Patterns;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.footballpredictgame.R;

public final class Utility {
	private static ProgressDialog mBusyDialog = null;

	private Utility() {

	}

	public static void showAlertDialog(Context context, int titleId,
			int messageId, int iconId) {
		hideBusyDialog();
		new AlertDialog.Builder(context)
				.setTitle(context.getString(titleId))
				.setMessage(context.getString(messageId))
				.setPositiveButton(android.R.string.yes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).setIcon(iconId).show();
	}

	public static void showAlertDialog(Context context, int titleId,
			int messageId, String premsg, int iconId) {
		hideBusyDialog();
		new AlertDialog.Builder(context)
				.setTitle(context.getString(titleId))
				.setMessage(
						premsg
								+ " : "
								+ (messageId == 0 ? "" : context
										.getString(messageId)))
				.setPositiveButton(android.R.string.yes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).setIcon(iconId).show();
	}

	public static void showBusyDialog(Context context, int msgId) {
		if (mBusyDialog != null) {
			mBusyDialog.dismiss();
			mBusyDialog = null;
		}

		// create a ProgressDialog instance, with a specified theme:
		mBusyDialog = new ProgressDialog(context,
				ProgressDialog.THEME_TRADITIONAL);
		// set indeterminate style
		mBusyDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		// set message
		mBusyDialog.setMessage(context.getString(msgId));
		// set it modal dialog
		mBusyDialog.setCanceledOnTouchOutside(false);
		mBusyDialog.setCancelable(false);
		// and show it
		mBusyDialog.show();

	}

	public static void hideBusyDialog() {
		if (mBusyDialog != null) {
			mBusyDialog.dismiss();
			mBusyDialog = null;
		}
	}

	public static AlertDialog confirmationDialog(Context context, String title,
			String msg, DialogInterface.OnClickListener l) {
		AlertDialog confirmationDialog = new AlertDialog.Builder(context)
				// set message, title, and icon
				.setTitle(title).setMessage(msg)
				.setPositiveButton(android.R.string.yes, l)
				.setNegativeButton(android.R.string.no, l).create();
		return confirmationDialog;

	}

	public static boolean showErrorMessage(VolleyError error, Context context) {
		if ((error instanceof NetworkError)
				|| (error instanceof NoConnectionError)) {
			Utility.showAlertDialog(context, R.string.title_alert_error,
					R.string.err_no_connection,
					android.R.drawable.ic_dialog_alert);
			return true;
		}

		if (error instanceof TimeoutError) {
			Utility.showAlertDialog(context, R.string.title_alert_error,
					R.string.err_time_out, android.R.drawable.ic_dialog_alert);
			return true;
		}

		if (error instanceof ServerError) {
			Utility.showAlertDialog(context, R.string.title_alert_error,
					R.string.err_server, android.R.drawable.ic_dialog_alert);
			return true;
		}

		if (error instanceof ParseError) {
			try {
				String data = new String(error.networkResponse.data,
						HttpHeaderParser
								.parseCharset(error.networkResponse.headers));
				if (data.contains("Access denied")
						|| data.contains("command denied")) {
					Utility.showAlertDialog(context,
							R.string.title_alert_error,
							R.string.err_access_denied,
							android.R.drawable.ic_dialog_alert);
					return true;
				}
			} catch (UnsupportedEncodingException e) {
				Utility.showAlertDialog(context, R.string.title_alert_error,
						R.string.err_bad_data,
						android.R.drawable.ic_dialog_alert);
				return true;
			}
			Utility.showAlertDialog(context, R.string.title_alert_error,
					R.string.err_bad_data, android.R.drawable.ic_dialog_alert);
			return true;
		}
		return false;
	}

	/**
	 * It will check if string contains any white-space.
	 * 
	 * @param str
	 *            string to check
	 * @return boolean true - string has white-space false - string doesn't
	 *         contain white-space
	 * 
	 *         public static boolean hasWhiteSpace(String str) { Pattern pattern
	 *         = Pattern.compile("\\s"); return pattern.matcher(str).find(); }
	 */

	/**
	 * It will check if given phone number is valid.
	 * 
	 * @param phoneNumber
	 *            phone number
	 * @return boolean true - phone number is valid false - phone number is
	 *         invalid
	 */

	public static boolean isValidPhoneNumber(String phoneNumber) {
		Pattern pattern = Patterns.PHONE;
		return pattern.matcher(phoneNumber).matches();
	}

	/**
	 * It will check if given email is valid.
	 * 
	 * @param email
	 *            email
	 * @return boolean true - email is valid false - phone number is invalid
	 */

	public static boolean isValidEmail(String email) {
		Pattern pattern = Patterns.EMAIL_ADDRESS;
		return pattern.matcher(email).matches();
	}
}
