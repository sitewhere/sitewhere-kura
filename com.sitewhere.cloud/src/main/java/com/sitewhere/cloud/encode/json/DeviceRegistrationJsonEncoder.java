/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.cloud.encode.json;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.sitewhere.cloud.payload.DeviceRegistrationPayload;

/**
 * SiteWhere Device Registration JSON Payload Encoder.
 * 
 * @author Jorge Villaverde
 */
public class DeviceRegistrationJsonEncoder extends JsonPayloadEncoder {
    
    /** Device Registration Header Type */
    private static final String HEADER_TYPE_REGISTER_DEVICE = "RegisterDevice";

    /** Area Token */
    private static final String AREA_TOKEN = "areaToken";
    
    /** Device Type Token */
    private static final String DEVICE_TYPE_TOKEN = "deviceTypeToken";

    /** Customer Token */
    private static final String CUSTOMER_TOKEN = "customerToken";

    public DeviceRegistrationJsonEncoder(final DeviceRegistrationPayload payload) {
	super(payload);
    }

    @Override
    protected JsonObject encodeRequest() {
        JsonObject jsonRequest = Json.object();
        jsonRequest.add(AREA_TOKEN, getPayload().getAreaToken());
        jsonRequest.add(CUSTOMER_TOKEN, getPayload().getCustomerToken());
        jsonRequest.add(DEVICE_TYPE_TOKEN, getPayload().getDeviceTypeToken());
        encodeMetadata(getPayload().getMetadata(), jsonRequest);
        return jsonRequest;
    }

    protected DeviceRegistrationPayload getPayload() {
	return (DeviceRegistrationPayload)super.getPayload();
    }

    @Override
    protected String getPayloadType() {
	return HEADER_TYPE_REGISTER_DEVICE;
    }
}
