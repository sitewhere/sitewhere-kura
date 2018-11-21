/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.device;

import java.util.Map;

import org.eclipse.kura.message.KuraPayload;

/**
 * SiteWhere Cloud Client Implementation for Eclipse Kura.
 * 
 * @author Jorge Villaverde
 */
public class SendMeasurementePayload extends KuraPayload {

    private static final String DEVICE_TOKEN = "deviceToken";

    private static final String MEASUREMENT_NAME = "measurementName";

    private static final String MEASUREMENT_VALUE = "measurementValue";
    
    private static final String UPDATE_STATE = "updateState";
    
    private static final String METADATA = "metadata";

    public String getDeviceToken() {
	return (String) getMetric(DEVICE_TOKEN);
    }

    public String getName() {
	return (String) getMetric(MEASUREMENT_NAME);
    }
    
    public Object getValue() {
	return getMetric(MEASUREMENT_VALUE);
    }
    
    @Override
    public String toString() {
	final StringBuilder sb = new StringBuilder("SendMeasurementePayload [");

	sb.append("getDeviceToken()=").append(getDeviceToken()).append(", ");

	sb.append("]");

	return sb.toString();
    }

    public static Builder newBuilder() {
	return new Builder();
    }
    
    public static class Builder {

	private String deviceToken;
	
	private String name;
	
	private Object value;

	private boolean updateState;
	
	private Map<String, String> metadata;

	private Builder() {
	    super();
	}

	public SendMeasurementePayload.Builder withDeviceToken(String deviceToken) {
	    this.deviceToken = deviceToken;
	    return this;
	}

	public SendMeasurementePayload.Builder withName(String name) {
	    this.name = name;
	    return this;
	}

	public SendMeasurementePayload.Builder withValue(Object value) {
	    this.value = value;
	    return this;
	}
	
	public SendMeasurementePayload.Builder withUpdateState(boolean updateState) {
	    this.updateState = updateState;
	    return this;
	}

	public SendMeasurementePayload.Builder withMetadata(Map<String, String> metadata) {
	    this.metadata = metadata;
	    return this;
	}

	public SendMeasurementePayload build() {
	    SendMeasurementePayload payload = new SendMeasurementePayload();
	    if (this.deviceToken != null) {
		payload.addMetric(DEVICE_TOKEN, this.deviceToken);
	    }
	    if (this.name != null) {
		payload.addMetric(MEASUREMENT_NAME, this.name);
	    }
	    if (this.value != null) {
		payload.addMetric(MEASUREMENT_VALUE, this.value);
	    }
	    if (this.metadata != null) {
		payload.addMetric(METADATA, metadata);
	    }
	    payload.addMetric(UPDATE_STATE, updateState);
	    
	    return payload;
	}
    }
}
