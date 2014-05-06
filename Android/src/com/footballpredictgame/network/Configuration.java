/** 
 * Name	  : RestClientSettings.java 
 * Version: 1.0 
 * Date   : Nov 14, 2013
 * Copyright (c) 2013 Accenture. All rights reserved. 
 */

package com.footballpredictgame.network;

import java.util.Map;

import android.content.Context;

/**
 * Utility configuration class to set parameters like timeout, headers etc.
 * 
 * @author Accenture India
 */
public final class Configuration {

	final Context context;
	final int initialTimeoutMs;
	final int maxNumRetries;
	final Map<String, String> httpHeaders;

	/**
	 * Constructor.
	 * 
	 * @param builder
	 */
	private Configuration(Builder builder) {
		context = builder.context;
		initialTimeoutMs = builder.initialTimeoutMs;
		maxNumRetries = builder.maxNumRetries;
		httpHeaders = builder.httpHeaders;
	}

	/**
	 * Inner builder class to set parameters.
	 * 
	 * @author Accenture India
	 */
	public static class Builder {
		private final Context context;
		private int initialTimeoutMs;
		private int maxNumRetries;
		private Map<String, String> httpHeaders;

		/**
		 * Constructor.
		 * 
		 * @param context
		 */
		public Builder(Context context) {
			this.context = context.getApplicationContext();
		}

		/**
		 * Constructs a new retry policy.
		 * 
		 * @param initialTimeoutMs
		 *            The initial timeout fo the policy.
		 * @param maxNumRetries
		 *            The max number of reties.
		 * @return Builder
		 */
		public Builder setRetryPolicy(int initialTimeoutMs, int maxNumRetries) {

			this.initialTimeoutMs = initialTimeoutMs;
			this.maxNumRetries = maxNumRetries;

			return this;

		}

		/**
		 * Sets the extra Http Headers to go along with each request.
		 * 
		 * @param httpHeaders
		 *            Extra Http Headers to go along with the request.
		 * @return Builder
		 */
		public Builder setHttpHeaders(Map<String, String> httpHeaders) {
			this.httpHeaders = httpHeaders;
			return this;
		}

		/**
		 * Sets all the parameters.
		 * 
		 * @return Configuration
		 */
		public Configuration build() {
			return new Configuration(this);
		}
	}
}
