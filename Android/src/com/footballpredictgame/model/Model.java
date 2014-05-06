/** 
 * Name	  : Model.java 
 * Version: 1.0 
 * Date   : Apr 10, 2014
 * Copyright (c) 2014 Sandeep Fatangare. All rights reserved. 
 */

package com.footballpredictgame.model;

/**
 * It is used to set global level data
 * 
 * @author Sandeep Fatangare India
 */
public final class Model {

	private static int mCurrentUserId = -1;

	private Model() {
	}

	public static void setCurrentUserId(final int currentUserId) {
		mCurrentUserId = currentUserId;
	}

	public static int getCurrentUserId() {
		return mCurrentUserId;
	}
}
