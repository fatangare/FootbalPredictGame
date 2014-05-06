/** 
 * Name	  : UserManager.java 
 * Version: 1.0 
 * Date   : Sep 4, 2013
 * Copyright (c) 2013 Accenture. All rights reserved. 
 */

package com.footballpredictgame.model;

import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * UserManager is used to store user specific information.
 * 
 * @author Accenture India
 */
public final class UserAccountManager {

	private final SharedPreferences mPreferences;

	/**
	 * Editor for Shared preferences.
	 */
	private final Editor mEditor;

	/**
	 * Shared preferences mode.
	 */
	private static final int PRIVATE_MODE = 0;

	/**
	 * Shared preferences file name.
	 */
	private static final String PREF_NAME = "UserPreferences";

	/**
	 * User name (make variable public to access from outside).
	 */
	public static final String KEY_USER = "user";

	/**
	 * Password (make variable public to access from outside).
	 */
	public static final String KEY_PASSWORD = "password";

	/**
	 * UserManager constructor. It will get access shared preferences in private
	 * mode.
	 * 
	 * @param context
	 *            context to get shared preferences
	 * 
	 */
	public UserAccountManager(Context context) {
		mPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		mEditor = mPreferences.edit();
		mEditor.apply();
	}

	/**
	 * Store user details in shared preferences.
	 * 
	 * @param key
	 *            key for the field to be stored
	 * @param value
	 *            value of the field to be stored
	 * @throws Exception
	 * */
	public void setUserDetails(final String key, final String value) {
		// Storing name in pref
		mEditor.putString(key, value);
	}

	/**
	 * Get stored user details.
	 * 
	 * @param key
	 *            Field for which details are required
	 * @return string containing user details for the required field. null if
	 *         key is not available.
	 * */
	public String getUserDetails(String key) {
		// return user

		return mPreferences.getString(key, null);
	}

	/**
	 * Save modifications to disk.
	 * 
	 * @return boolean - true if the new values were successfully written to
	 *         persistent storage.
	 * */
	public boolean save() {
		// commit changes
		return mEditor.commit();
	}

	/**
	 * Save modifications to disk asynchronously. It is useful when you don't
	 * want to process return value.
	 * */
	public void saveAsync() {
		// commit changes
		mEditor.apply();
	}
}
