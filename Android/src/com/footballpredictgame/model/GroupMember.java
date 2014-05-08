/** 
 * Name	  : Profile.java 
 * Version: 1.0 
 * Date   : Apr 11, 2014
 * Copyright (c) 2014 Sandeep Fatangare. All rights reserved. 
 */

package com.footballpredictgame.model;

import com.google.gson.annotations.SerializedName;

/**
 * It is POJO object for holding profile data
 * 
 * @author Sandeep Fatangare India
 */
public class GroupMember {

	@SerializedName("id")
	public String mUserId;

	@SerializedName("firstname")
	public String mFirstName;

	@SerializedName("lastname")
	public String mLastName;

	@SerializedName("points")
	public int mPoints;

	public GroupMember() {
	}

}
