package com.footballpredictgame.network;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.footballpredictgame.model.Group;
import com.footballpredictgame.model.GroupMember;
import com.footballpredictgame.model.HistoryItem;
import com.footballpredictgame.model.Model;
import com.footballpredictgame.utiity.Constant;
import com.google.gson.reflect.TypeToken;

public final class NetworkRequest {
	public static final boolean NETWORK_MODE = true;

	private NetworkRequest() {
	}

	public static void login(final String mobile, final String pwd,
			final NetworkRequestListener listener) {
		if (NETWORK_MODE) {

			Map<String, String> params = new HashMap<String, String>();
			params.put(Constant.MOBILE, mobile);
			params.put(Constant.PWD, pwd);

			GsonRequest<String> gsonRequest = new GsonRequest<String>(
					Method.POST, Constant.URL + Constant.URL_LOGIN, "gson",
					params, String.class, new Listener<String>() {

						@Override
						public void onResponse(String response) {
							Log.v(Constant.TAG, "login= " + response);
							if (response.compareTo("-1") == 0)
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

	public static void register(final String mobile, final String email,
			final String pwd, final NetworkRequestListener listener) {
		if (NETWORK_MODE) {

			Map<String, String> params = new HashMap<String, String>();
			params.put(Constant.MOBILE, mobile);
			params.put(Constant.EMAIL, email);
			params.put(Constant.PWD, pwd);
			// ToDo: remove later
			params.put(Constant.FIRSTNAME, "Sandeep");
			params.put(Constant.LASTNAME, "Fatangare");
			params.put(Constant.CITY, "Pune");

			GsonRequest<String> gsonRequest = new GsonRequest<String>(
					Method.POST, Constant.URL + Constant.URL_REGISTER, "gson",
					params, String.class, new Listener<String>() {

						@Override
						public void onResponse(String response) {
							Log.v(Constant.TAG, "register= " + response);
							if (response.compareTo("-1") == 0)
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

	public static void forgotPassword(final String mobile, final String email,
			final NetworkRequestListener listener) {
		if (NETWORK_MODE) {

			Map<String, String> params = new HashMap<String, String>();
			params.put(Constant.MOBILE, mobile);
			params.put(Constant.EMAIL, email);

			GsonRequest<String> gsonRequest = new GsonRequest<String>(
					Method.POST, Constant.URL + Constant.URL_FORGOT_PASSWORD,
					"gson", params, String.class, new Listener<String>() {

						@Override
						public void onResponse(String response) {
							Log.v(Constant.TAG, "register= " + response);
							if (response.compareTo("-1") == 0)
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

	public static void updateProfile(final String fname, final String lname,
			final String mobile, final String email, final String city,
			final NetworkRequestListener listener) {
		if (NETWORK_MODE) {

			Map<String, String> params = new HashMap<String, String>();
			params.put(Constant.MOBILE, mobile);
			params.put(Constant.EMAIL, email);
			params.put(Constant.FIRSTNAME, fname);
			params.put(Constant.LASTNAME, lname);
			params.put(Constant.CITY, city);

			GsonRequest<String> gsonRequest = new GsonRequest<String>(
					Method.POST, Constant.URL + Constant.URL_UPDATE_PROFILE,
					"gson", params, String.class, new Listener<String>() {

						@Override
						public void onResponse(String response) {
							Log.v(Constant.TAG, "register= " + response);
							if (response.compareTo("-1") == 0)
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

	public static void getHistory(final NetworkRequestListener listener) {
		if (NETWORK_MODE) {

			Map<String, String> params = new HashMap<String, String>();
			params.put(Constant.ID, Model.getCurrentUserId());

			Type collectionType = new TypeToken<List<HistoryItem>>() {
			}.getType();
			GsonRequest<List<HistoryItem>> gsonRequest = new GsonRequest<List<HistoryItem>>(
					Method.POST, Constant.URL + Constant.URL_GET_HISTORY,
					"gson", params, collectionType,
					new Listener<List<HistoryItem>>() {

						@Override
						public void onResponse(List<HistoryItem> response) {
							Log.v(Constant.TAG,
									"getHistory= " + response.size());
							if (response == null || response.isEmpty())
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

	public static void insertOrUpdateGroup(final String groupId,
			final String favorite, final String groupName,
			final NetworkRequestListener listener) {
		if (NETWORK_MODE) {

			Map<String, String> params = new HashMap<String, String>();
			params.put(Constant.NAME, groupName);
			params.put(Constant.FAVORITE, favorite);
			params.put(Constant.OWNERID, Model.getCurrentUserId());

			StringBuilder url = new StringBuilder();
			url.append(Constant.URL);
			if (groupId.isEmpty()) {
				url = url.append(Constant.URL_ADD_GROUP);
			} else {
				url = url.append(Constant.URL_UPDATE_GROUP);
				params.put(Constant.ID, groupId);
			}

			GsonRequest<Integer> gsonRequest = new GsonRequest<Integer>(
					Method.POST, url.toString(), "gson", params, Integer.class,
					new Listener<Integer>() {

						@Override
						public void onResponse(Integer response) {
							Log.v(Constant.TAG,
									"add group= " + Integer.toString(response));
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

	public static void getGroups(final NetworkRequestListener listener) {
		if (NETWORK_MODE) {

			Map<String, String> params = new HashMap<String, String>();
			params.put(Constant.ID, Model.getCurrentUserId());

			Type collectionType = new TypeToken<List<Group>>() {
			}.getType();
			GsonRequest<List<Group>> gsonRequest = new GsonRequest<List<Group>>(
					Method.POST, Constant.URL + Constant.URL_GET_GROUPS,
					"gson", params, collectionType,
					new Listener<List<Group>>() {

						@Override
						public void onResponse(List<Group> response) {
							Log.v(Constant.TAG, "getGroups= " + response.size());
							if (response == null || response.isEmpty())
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

	public static void getGroupMembers(final String groupId,
			final NetworkRequestListener listener) {
		if (NETWORK_MODE) {

			Map<String, String> params = new HashMap<String, String>();
			params.put(Constant.ID, Model.getCurrentUserId());
			params.put(Constant.GROUPID, groupId);

			Type collectionType = new TypeToken<List<GroupMember>>() {
			}.getType();
			GsonRequest<List<GroupMember>> gsonRequest = new GsonRequest<List<GroupMember>>(
					Method.POST, Constant.URL + Constant.URL_GET_GROUPUSERS,
					"gson", params, collectionType,
					new Listener<List<GroupMember>>() {

						@Override
						public void onResponse(List<GroupMember> response) {
							Log.v(Constant.TAG,
									"getGroupMembers= " + response.size());
							if (response == null || response.isEmpty())
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
