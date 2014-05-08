package com.footballpredictgame.model;

import com.google.gson.annotations.SerializedName;

public class HistoryItem {

	@SerializedName("id")
	public String mId;

	@SerializedName("points")
	public String mPoints;

	@SerializedName("home")
	public String mTeam1;

	@SerializedName("away")
	public String mTeam2;

	@SerializedName("matchdate")
	public String mMatchDate;

	@SerializedName("matchtime")
	public String mMatchTime;

	public HistoryItem() {
	}

}
