
package com.footballpredictgame.network;

import com.android.volley.VolleyError;

public interface NetworkRequestListener {
	public void onResponse(Object obj);

	public void onErrorResponse(VolleyError error, Object obj);
}
