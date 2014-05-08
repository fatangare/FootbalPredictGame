/** 
 * Name	  : Profile.java 
 * Version: 1.0 
 * Date   : Apr 11, 2014
 * Copyright (c) 2014 Sandeep Fatangare. All rights reserved. 
 */

package com.footballpredictgame.model;

import com.google.gson.annotations.SerializedName;

/**
 * It is POJO object for holding Group data
 * 
 * @author Sandeep Fatangare India
 */
public class Group {

	@SerializedName("id")
	public String mId;

	@SerializedName("name")
	public String mName;

	@SerializedName("members")
	public String mMembers;

	@SerializedName("rank")
	public int mRank;

	public Group() {
	}

}
