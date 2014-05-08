/** 
 * Name	  : Model.java 
 * Version: 1.0 
 * Date   : Apr 10, 2014
 * Copyright (c) 2014 Sandeep Fatangare. All rights reserved. 
 */

package com.footballpredictgame.model;

import java.util.List;

/**
 * It is used to set global level data
 * 
 * @author Sandeep Fatangare India
 */
public final class Model {

	private static String mCurrentUserId = "-1";
	private static List<HistoryItem> mHistory = null;

	private Model() {
	}

	public static void setCurrentUserId(final String currentUserId) {
		mCurrentUserId = currentUserId;
	}

	public static String getCurrentUserId() {
		return mCurrentUserId;
	}

	public static List<HistoryItem> getHistory() {
		if (mHistory == null || mHistory.isEmpty())
			return null;
		return mHistory;
	}

	public static void setHistory(final List<HistoryItem> history) {
		mHistory = history;
	}
}
