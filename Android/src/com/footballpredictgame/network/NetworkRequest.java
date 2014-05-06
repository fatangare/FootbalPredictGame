
package com.footballpredictgame.network;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.footballpredictgame.utiity.Constant;

public final class NetworkRequest {
	public static final boolean NETWORK_MODE = true;

	private NetworkRequest() {
	}

	public static void login(final String mobile, final String pwd, final NetworkRequestListener listener) {
		if (NETWORK_MODE) {

			Map<String, String> params = new HashMap<String, String>();
			params.put(Constant.MOBILE, mobile);
			params.put(Constant.PWD, pwd);

			GsonRequest<Integer> gsonRequest = new GsonRequest<Integer>(Method.POST, Constant.URL + Constant.URL_LOGIN,
					"gson", params, Integer.class, new Listener<Integer>() {

						@Override
						public void onResponse(Integer response) {
							Log.v(Constant.TAG, "login= " + Integer.toString(response));
							if (response == -1)
								listener.onErrorResponse(null, response);
							else
								listener.onResponse(response);
						}
					}, new Response.ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							Log.v(Constant.TAG, error.toString());
							listener.onErrorResponse(error, null);
						}
					});

			gsonRequest.executeGsonRequest();
		} else {

		}

	}

	public static void register(final String mobile, final String email, final String pwd,
			final NetworkRequestListener listener) {
		if (NETWORK_MODE) {

			Map<String, String> params = new HashMap<String, String>();
			params.put(Constant.MOBILE, mobile);
			params.put(Constant.EMAIL, email);
			params.put(Constant.PWD, pwd);
			// ToDo: remove later
			params.put(Constant.FIRSTNAME, "Sandeep");
			params.put(Constant.LASTNAME, "Fatangare");
			params.put(Constant.CITY, "Pune");

			GsonRequest<Integer> gsonRequest = new GsonRequest<Integer>(Method.POST, Constant.URL
					+ Constant.URL_REGISTER, "gson", params, Integer.class, new Listener<Integer>() {

				@Override
				public void onResponse(Integer response) {
					Log.v(Constant.TAG, "register= " + Integer.toString(response));
					if (response == -1)
						listener.onErrorResponse(null, response);
					else
						listener.onResponse(response);
				}
			}, new Response.ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError error) {
					Log.v(Constant.TAG, error.toString());
					listener.onErrorResponse(error, null);
				}
			});

			gsonRequest.executeGsonRequest();
		} else {

		}

	}

	public static void forgotPassword(final String mobile, final String email, final NetworkRequestListener listener) {
		if (NETWORK_MODE) {

			Map<String, String> params = new HashMap<String, String>();
			params.put(Constant.MOBILE, mobile);
			params.put(Constant.EMAIL, email);

			GsonRequest<Integer> gsonRequest = new GsonRequest<Integer>(Method.POST, Constant.URL
					+ Constant.URL_FORGOT_PASSWORD, "gson", params, Integer.class, new Listener<Integer>() {

				@Override
				public void onResponse(Integer response) {
					Log.v(Constant.TAG, "register= " + Integer.toString(response));
					if (response == -1)
						listener.onErrorResponse(null, response);
					else
						listener.onResponse(response);
				}
			}, new Response.ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError error) {
					Log.v(Constant.TAG, error.toString());
					listener.onErrorResponse(error, null);
				}
			});

			gsonRequest.executeGsonRequest();
		} else {

		}

	}

	public static void updateProfile(final String fname, final String lname, final String mobile, final String email,
			final String city, final NetworkRequestListener listener) {
		if (NETWORK_MODE) {

			Map<String, String> params = new HashMap<String, String>();
			params.put(Constant.MOBILE, mobile);
			params.put(Constant.EMAIL, email);
			params.put(Constant.FIRSTNAME, fname);
			params.put(Constant.LASTNAME, lname);
			params.put(Constant.CITY, city);

			GsonRequest<Integer> gsonRequest = new GsonRequest<Integer>(Method.POST, Constant.URL
					+ Constant.URL_UPDATE_PROFILE, "gson", params, Integer.class, new Listener<Integer>() {

				@Override
				public void onResponse(Integer response) {
					Log.v(Constant.TAG, "register= " + Integer.toString(response));
					if (response == -1)
						listener.onErrorResponse(null, response);
					else
						listener.onResponse(response);
				}
			}, new Response.ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError error) {
					Log.v(Constant.TAG, error.toString());
					listener.onErrorResponse(error, null);
				}
			});

			gsonRequest.executeGsonRequest();
		} else {

		}

	}
}
