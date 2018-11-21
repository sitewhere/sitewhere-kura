/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.cloud.payload;

import org.eclipse.kura.message.KuraPayload;

/**
 * 
 * @author Jorge Villaverde
 */
public class SiteWherePayload extends KuraPayload {

    protected static final String DEVICE_TOKEN = "deviceToken";

    protected static final String ORIGINATOR = "originator";

    public String getDeviceToken() {
	return (String) getMetric(DEVICE_TOKEN);
    }
    
    public String getOriginator() {
	return (String) getMetric(ORIGINATOR);
    }
}
