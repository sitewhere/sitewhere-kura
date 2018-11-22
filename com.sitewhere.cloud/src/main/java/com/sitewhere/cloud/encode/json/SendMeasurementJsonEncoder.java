/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.cloud.encode.json;

import java.util.Date;

import org.eclipse.kura.message.KuraPayload;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.sitewhere.cloud.payload.SendMeasurementPayload;

/**
 * Encodes SiteWhere Measurement Message using Simple JSON
 * 
 * @author Jorge Villaverde
 */
public class SendMeasurementJsonEncoder extends JsonPayloadEncoder {

    /** Device Measurements Header Type */
    private static final String HEADER_TYPE_DEVICE_MEASUREMENTS = "DeviceMeasurement";

    /** Measurement Id */
    private static final String MEASUREMENT_ID = "name";

    /** Measurement Value */
    private static final String MEASUREMENT_VALUE = "value";
    
    /** Event Date */
    private static final String EVENT_DATE = "eventDate";

    /** Update State */
    private static final String UPDATE_STATE = "updateState";
    
    public SendMeasurementJsonEncoder(SendMeasurementPayload payload) {
	super(payload);
    }

    @Override
    protected JsonObject encodeRequest() {
	JsonObject jsonRequest = Json.object();

	jsonRequest.add(MEASUREMENT_ID, getPayload().getMeasurementId());
	jsonRequest.add(MEASUREMENT_VALUE, String.valueOf(getPayload().getMeasurementValue()));
	jsonRequest.add(EVENT_DATE, formatJsonDate(new Date(getPayload().getEventDate())));
	jsonRequest.add(UPDATE_STATE, String.valueOf(getPayload().getUpdateState()));
	
	return jsonRequest;
    }

    @Override
    protected String getPayloadType() {
	return HEADER_TYPE_DEVICE_MEASUREMENTS;
    }
    
    @Override
    protected SendMeasurementPayload getPayload() {
	KuraPayload payload = super.getPayload();
	return (SendMeasurementPayload)payload;
    }


}
