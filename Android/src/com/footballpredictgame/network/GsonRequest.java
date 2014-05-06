
package com.footballpredictgame.network;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Class to execute request and deserializes the Json into an object of
 * the specified class.
 * 
 * @author Accenture India
 * @param <T>
 *            The type of parsed response this request expects.
 */
public class GsonRequest<T> extends Request<T> {
	private final Gson mGson;
	private final Class<T> mClazz;
	private final Type mType;
	private final Listener<T> mListener;
	private static final String PROTOCOL_CHARSET = "utf-8";
	private final Map mParams;
	private final String mStrParams;
	private String mTag = "";

	private final static String TAG = "Volley";
	/**
	 * Constructor to execute json request.
	 * 
	 * @param method
	 *            Request with given method.
	 * @param url
	 *            Url to fetch the json from.
	 * @param tag
	 *            String tag to execute this request
	 * @param params
	 *            parameters to post with the request. Null is allowed and
	 *            indicates no parameters will be posted along with request.
	 * @param clazz
	 *            Relevant class object
	 * @param listener
	 *            Listener to get response after successful execution of
	 *            request.
	 * @param errorListener
	 *            Error listener if there is some error executing this request.
	 */
	public GsonRequest(int method, String url, String tag,  Map params, Class<T> clazz,
			Listener<T> listener, ErrorListener errorListener) {
		super(method, url, errorListener);
		this.mClazz = clazz;
		this.mType=null;
		this.mListener = listener;
		mParams = params;
		mStrParams = null;
		mTag = tag;
		mGson = new Gson();

		// RequestQueueManager.getInstance().addToRequestQueue(this, tag);
	}
	
	public GsonRequest(int method, String url, String tag,  String params, Class<T> clazz,
			Listener<T> listener, ErrorListener errorListener) {
		super(method, url, errorListener);
		this.mClazz = clazz;
		this.mType=null;
		this.mListener = listener;
		mStrParams = params;
		mTag = tag;
		mParams=null;
		mGson = new Gson();

		// RequestQueueManager.getInstance().addToRequestQueue(this, tag);
	}
	
	public GsonRequest(int method, String url, String tag, Map params, Type type,
			Listener<T> listener, ErrorListener errorListener) {
		super(method, url, errorListener);
		this.mType=type;
		this.mClazz=null;
		this.mListener = listener;
		mParams = params;
		mStrParams=null;
		mTag = tag;
		mGson = new Gson();

		// RequestQueueManager.getInstance().addToRequestQueue(this, tag);
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		return (RequestQueueManager.getInstance().getConfiguration() != null && RequestQueueManager.getInstance()
				.getConfiguration().httpHeaders != null) ? RequestQueueManager.getInstance().getConfiguration().httpHeaders
				: super.getHeaders();
	}

	@Override
	public byte[] getPostBody() {
		return getBody();
	}

	@Override
	public byte[] getBody() {		
		if(mStrParams==null){
			Log.v(TAG, new JSONObject(mParams).toString()+"  post data");
			return mParams == null ? null : new JSONObject(mParams).toString().getBytes();
		}
		else
			return mStrParams.getBytes();
		/*try {
			return mParams == null ? null : new JSONObject(mParams).toString().getBytes(PROTOCOL_CHARSET);
		} catch (UnsupportedEncodingException uee) {
			VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mParams,
					PROTOCOL_CHARSET);
			return null;
		}*/
	}

	@Override
	protected void deliverResponse(T response) {
		mListener.onResponse(response);
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			Log.v(TAG, "response data= "+json);
			T t=null;
			t=mGson.fromJson(json, (mClazz==null?mType:mClazz));
			return Response.success(t, HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(response));
		} catch (JsonSyntaxException e) {
			return Response.error(new ParseError(response));
		}
	}

	/**
	 * Cancel all pending request using specified tag.
	 * 
	 * @param tag
	 *            To cancel pending request associated with this tag. If tag is
	 *            not specified Default tag will be used to cancel request(s).
	 */
	public void cancelPendingRequest(String tag) {

		RequestQueueManager.getInstance().cancelPendingRequests(tag);

	}

	/**
	 * Executes GsonRequest.
	 */
	public void executeGsonRequest() {
		RequestQueueManager.getInstance().addToRequestQueue(this, mTag);
	}

}
