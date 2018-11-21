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
 * SiteWhere Device Registration Message for Eclipse Kura.
 * 
 * @author Jorge Villaverde
 */
public class DeviceRegistrationPayload extends SiteWherePayload {

    private static final String DEVICE_TYPE_TOKEN = "deviceTypeToken";

    private static final String CUSTOMER_TOKEN = "customerToken";

    private static final String AREA_TOKEN = "areaToken";
    
    private static final String METADATA = "metadata";

    public String getDeviceTypeToken() {
	return (String) getMetric(DEVICE_TYPE_TOKEN);
    }

    public String getCustomerToken() {
	return (String) getMetric(CUSTOMER_TOKEN);
    }

    public String getAreaToken() {
	return (String) getMetric(AREA_TOKEN);
    }
    
    @SuppressWarnings("unchecked")
    public Map<String, String> getMetadata() {
	return (Map<String, String>) getMetric(METADATA);
    }

    @Override
    public String toString() {
	final StringBuilder sb = new StringBuilder("DeviceRegistrationPayload [");

	sb.append("getDeviceToken()=").append(getDeviceToken()).append(", ");
	sb.append("getDeviceTypeToken()=").append(getDeviceTypeToken()).append(", ");
	sb.append("getCustomerToken()=").append(getCustomerToken()).append(", ");
	sb.append("getAreaToken()=").append(getAreaToken());

	sb.append("]");

	return sb.toString();
    }

    public static Builder newBuilder() {
	return new Builder();
    }

    public static class Builder {

	private String deviceToken;
		
	private String originator;
	
	private String deviceTypeToken;

	private String customerToken;

	private String areaToken;
	
	private Map<String, String> metadata;

	private Builder() {
	    super();
	}

	public DeviceRegistrationPayload.Builder withDeviceToken(String deviceToken) {
	    this.deviceToken = deviceToken;
	    return this;
	}
	
	public DeviceRegistrationPayload.Builder withOriginator(String originator) {
	    this.originator = originator;
	    return this;
	}

	public DeviceRegistrationPayload.Builder withDeviceTypeToken(String deviceTypeToken) {
	    this.deviceTypeToken = deviceTypeToken;
	    return this;
	}

	public DeviceRegistrationPayload.Builder withCustomerToken(String customerToken) {
	    this.customerToken = customerToken;
	    return this;
	}

	public DeviceRegistrationPayload.Builder withAreaToken(String areaToken) {
	    this.areaToken = areaToken;
	    return this;
	}

	public DeviceRegistrationPayload.Builder withMetadata(Map<String, String> metadata) {
	    this.metadata = metadata;
	    return this;
	}
	
	public DeviceRegistrationPayload build() {
	    DeviceRegistrationPayload payload = new DeviceRegistrationPayload();

	    if (this.deviceToken != null) {
		payload.addMetric(DEVICE_TOKEN, this.deviceToken);
	    }
	    if (this.originator != null) {
		payload.addMetric(ORIGINATOR, this.originator);
	    }
	    if (this.deviceTypeToken != null) {
		payload.addMetric(DEVICE_TYPE_TOKEN, this.deviceTypeToken);
	    }
	    if (this.customerToken != null) {
		payload.addMetric(CUSTOMER_TOKEN, this.customerToken);
	    }
	    if (this.areaToken != null) {
		payload.addMetric(AREA_TOKEN, this.areaToken);
	    }
	    if (this.metadata != null) {
		payload.addMetric(METADATA, metadata);
	    }
	    return payload;
	}
    }
}
