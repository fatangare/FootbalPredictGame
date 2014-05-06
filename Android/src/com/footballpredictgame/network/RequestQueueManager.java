package com.footballpredictgame.network;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Utility class to manage request queue. Also adds each request to request
 * queue.
 * 
 * @author 
 */
public class RequestQueueManager {
	private static RequestQueue mRequestQueue;
	private static final String TAG = "VolleyPatterns";

	private static RequestQueueManager mInstance = null;
	private static Configuration mConfiguration = null;

	static {
		if (mInstance == null) {
			mInstance = new RequestQueueManager();
		}
	}

	/**
	 * Initializes the requestqueue
	 * 
	 * @param context
	 */
	public static void init(Context context) {
		mRequestQueue = Volley.newRequestQueue(context);

	}

	/**
	 * Initialize the request queue with configuration.
	 * 
	 * @param configuration
	 *            Instance of Configuration class.
	 */
	public static void init(Configuration configuration) {
		mRequestQueue = Volley.newRequestQueue(configuration.context);

		mConfiguration = configuration;

	}

	/**
	 * Gets the instance of RequestQueueManager.
	 * 
	 * @return RequestQueueManager
	 */
	public static synchronized RequestQueueManager getInstance() {
		return mInstance;
	}

	/**
	 * Gets the instance of RequestQueue.
	 * 
	 * @return RequestQueue
	 */
	public RequestQueue getRequestQueue() {
		// lazy initialize the request queue, the queue instance will be
		// created when it is accessed for the first time

		if (mRequestQueue != null) {
			return mRequestQueue;
		} else {
			throw new IllegalStateException("RequestQueue not initialized");
		}

	}

	/**
	 * Gets the instance of Configuration.
	 * 
	 * @return Configuration
	 */
	public Configuration getConfiguration() {
		return mConfiguration;
	}

	/**
	 * Adds the specified request to the global queue, if tag is specified
	 * then it is used else Default TAG is used.
	 * 
	 * @param req
	 * @param tag
	 */
	public <T> void addToRequestQueue(Request<T> req, String tag) {
		Log.d("test", "Request tag: " + tag);

		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

		if (mConfiguration != null) {
			req.setRetryPolicy(new DefaultRetryPolicy(mConfiguration.initialTimeoutMs, mConfiguration.maxNumRetries,
					1.0F));
		}

		try {
			getRequestQueue().add(req);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds the specified request to the global queue using the Default TAG.
	 * 
	 * @param req
	 * @param tag
	 */
	public <T> void addToRequestQueue(Request<T> req) {
		// set the default tag if tag is empty
		req.setTag(TAG);

		if (mConfiguration != null) {
			req.setRetryPolicy(new DefaultRetryPolicy(mConfiguration.initialTimeoutMs, mConfiguration.maxNumRetries,
					1.0F));
		}

		try {
			getRequestQueue().add(req);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cancels all pending requests by the specified TAG, it is important
	 * to specify a TAG so that the pending/ongoing requests can be cancelled.
	 * 
	 * @param tag
	 */
	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			if (tag != null) {
				mRequestQueue.cancelAll(tag);
			} else {
				mRequestQueue.cancelAll(TAG);
			}
		}
	}

}
