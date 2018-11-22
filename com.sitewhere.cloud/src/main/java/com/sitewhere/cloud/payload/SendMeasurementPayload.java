/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.cloud.payload;

import java.util.Map;

/**
 * SiteWhere Cloud Client Implementation for Eclipse Kura.
 * 
 * @author Jorge Villaverde
 */
public class SendMeasurementPayload extends SiteWherePayload {

    private static final String MEASUREMENT_ID = "measurementId";

    private static final String MEASUREMENT_VALUE = "measurementValue";
    
    private static final String EVENT_DATE = "eventDate";
    
    private static final String UPDATE_STATE = "updateState";
    
    private static final String METADATA = "metadata";

    public Long getEventDate() {
	return (Long) getMetric(EVENT_DATE);
    }
    
    public Boolean getUpdateState() {
	return (Boolean) getMetric(UPDATE_STATE);
    }
    
    public String getMeasurementId() {
	return (String) getMetric(MEASUREMENT_ID);
    }
    
    public Double getMeasurementValue() {
	return (Double)getMetric(MEASUREMENT_VALUE);
    }
    
    @SuppressWarnings("unchecked")
    public Map<String, String> getMetadata() {
	return (Map<String, String>) getMetric(METADATA);
    }
    
    @Override
    public String toString() {
	final StringBuilder sb = new StringBuilder("SendMeasurementPayload [");

	sb.append("getDeviceToken()=").append(getDeviceToken()).append(", ");

	sb.append("]");

	return sb.toString();
    }

    public static Builder newBuilder() {
	return new Builder();
    }
    
    public static class Builder {

	private String deviceToken;
	
	private String originator;
	
	private String measurementId;
	
	private Double measurementValue;

	private boolean updateState;
	
	private Map<String, String> metadata;
	
	private Long eventDate;

	private Builder() {
	    super();
	}

	public SendMeasurementPayload.Builder withDeviceToken(String deviceToken) {
	    this.deviceToken = deviceToken;
	    return this;
	}
	
	public SendMeasurementPayload.Builder withOriginator(String originator) {
	    this.originator = originator;
	    return this;
	}

	public SendMeasurementPayload.Builder withMeasurementId(String measurementId) {
	    this.measurementId = measurementId;
	    return this;
	}

	public SendMeasurementPayload.Builder withMeasurementValue(Double measurementValue) {
	    this.measurementValue = measurementValue;
	    return this;
	}
	
	public SendMeasurementPayload.Builder withUpdateState(boolean updateState) {
	    this.updateState = updateState;
	    return this;
	}
	
	public SendMeasurementPayload.Builder withEventDate(Long eventDate) {
	    this.eventDate = eventDate;
	    return this;
	}

	public SendMeasurementPayload.Builder withMetadata(Map<String, String> metadata) {
	    this.metadata = metadata;
	    return this;
	}

	public SendMeasurementPayload build() {
	    SendMeasurementPayload payload = new SendMeasurementPayload();
	    if (this.deviceToken != null) {
		payload.addMetric(DEVICE_TOKEN, this.deviceToken);
	    }
	    if (this.originator != null) {
		payload.addMetric(ORIGINATOR, this.originator);
	    }
	    if (this.measurementId != null) {
		payload.addMetric(MEASUREMENT_ID, this.measurementId);
	    }
	    if (this.measurementValue != null) {
		payload.addMetric(MEASUREMENT_VALUE, this.measurementValue);
	    }
	    if (this.eventDate != null) {
		payload.addMetric(EVENT_DATE, this.eventDate);
	    }	    
	    if (this.metadata != null) {
		payload.addMetric(METADATA, metadata);
	    }
	    payload.addMetric(UPDATE_STATE, updateState);
	    
	    return payload;
	}
    }
}
