/** 
 * Name	  : VolleyErrorHandler.java 
 * Version: 1.0 
 * Date   : Oct 30, 2013
 * Copyright (c) 2013 Accenture. All rights reserved. 
 */

package com.footballpredictgame.network;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class VolleyErrorHandler {
	private static final int ERROR_RESOURCE_NOT_FOUND = 404;
	private static final int ERROR_METHOD_NOT_ALLOWED = 405;
	private static final int ERROR_AUTHENTICATION_FAILURE = 403;
	private static final int ERROR_MALFORMED_SYNTAX = 400;
	private static final int ERROR = 401;

	/**
	 * Returns appropriate message which is to be displayed to the user
	 * against the specified error object.
	 * 
	 * @param error
	 * @param context
	 * @return
	 */
	public static String getMessage(Object error, Context context) {
		if (error instanceof TimeoutError) {
			return "Error: Server Down";
		} else if (isServerProblem(error)) {
			return handleServerError(error, context);
		} else if (isNetworkProblem(error)) {
			return "Error: No internet connection.";
		}
		return "Unknown error occured";
	}

	/**
	 * Determines whether the error is related to network
	 * 
	 * @param error
	 * @return
	 */
	private static boolean isNetworkProblem(Object error) {
		return (error instanceof NetworkError) || (error instanceof NoConnectionError);
	}

	/**
	 * Determines whether the error is related to server.
	 * 
	 * @param error
	 * @return
	 */
	private static boolean isServerProblem(Object error) {
		return (error instanceof ServerError) || (error instanceof AuthFailureError);
	}

	/**
	 * Handles the server error, tries to determine whether to show a stock
	 * message or to
	 * show a message retrieved from the server.
	 * 
	 * @param err
	 * @param context
	 * @return
	 */
	private static String handleServerError(Object err, Context context) {
		VolleyError error = (VolleyError) err;

		NetworkResponse response = error.networkResponse;

		if (response != null) {
			switch (response.statusCode) {
				case ERROR_METHOD_NOT_ALLOWED :
					return "Error: Method not allowed on resource";
				case ERROR_RESOURCE_NOT_FOUND :
					return "Error: Resource not found.";
				case ERROR_AUTHENTICATION_FAILURE :
					return "Authentication failure or invalid Application ID.";
				case ERROR :
					try {

						HashMap<String, String> result = new Gson().fromJson(new String(response.data),
								new TypeToken<Map<String, String>>() {
								}.getType());

						if (result != null && result.containsKey("error")) {
							return result.get("error");
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
					// invalid request
					return error.getMessage();

				case ERROR_MALFORMED_SYNTAX :

					return "Error: Malformed syntax or a bad query.";
				default :

					return "Unknown error occured.";
			}
		}

		return "Unknown error occured";
	}
}
