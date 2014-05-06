/** 
 * Name	  : ApplicationExt.java 
 * Version	  : 1.0 
 * Copyright (c) 2013 Accenture. All rights reserved. 
 */

package com.footballpredictgame;

import java.util.HashMap;
import java.util.Map;

import com.footballpredictgame.network.Configuration;
import com.footballpredictgame.network.RequestQueueManager;

import android.app.Application;

//import de.akquinet.android.androlog.Log;

/**
 * It is extension of Application class and is usually used to keep application
 * level state-info e.g. global variables, functions
 * 
 * @author Accenture India
 */
public class ApplicationExt extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		Map<String, String> map = new HashMap<String, String>();
		map.put("Content-type", "application/json; charset=utf-8");

		Configuration configuration = new Configuration.Builder(
				this).setRetryPolicy(120000, 0).setHttpHeaders(map).build();

		RequestQueueManager.init(configuration);;
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}

}
