/** 
 * Name	  : Parser.java 
 * Version: 1.0 
 * Date   : Nov 20, 2013
 * Copyright (c) 2013 Accenture. All rights reserved. 
 */

package com.footballpredictgame.network;

/**
 * Base parser class.
 * 
 * @author Accenture India
 */
public class Request {

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
}
