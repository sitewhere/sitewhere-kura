/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.cloud.encode.json;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.sitewhere.cloud.encode.AbstractPayloadEncoder;
import com.sitewhere.cloud.payload.SiteWherePayload;

/**
 * SiteWhere JSON Payload Encoder superclass
 * @author Jorge Villaverde
 */
public abstract class JsonPayloadEncoder extends AbstractPayloadEncoder {

    /** SiteWhere JSON Header Type */
    public static final String HEADER_TYPE = "type";
    
    /** SiteWhere JSON Header Originator */
    public static final String HEADER_ORIGINATOR = "originator";

    /** SiteWhere JSON Header Device Token */
    public static final String HEADER_DEVICE_TOKEN = "deviceToken";

    /** Metadata field */
    private static final String METADATA = "metadata";

    /** SiteWhere JSON Request */
    public static final String REQUEST = "request";
    
    
    public JsonPayloadEncoder(SiteWherePayload paylaod) {
	super(paylaod);
    }
    
    @Override
    public byte[] getBytes() throws IOException {
	logger.debug("Creating Device Registration JSON payload");
	
        JsonObject json = Json.object();
        
        json.add(HEADER_TYPE, getPayloadType());
        json.add(HEADER_ORIGINATOR, this.paylaod.getOriginator());
        json.add(HEADER_DEVICE_TOKEN, this.paylaod.getDeviceToken());
        json.add(REQUEST, encodeRequest());

        return json.toString().getBytes(StandardCharsets.UTF_8);	
    }
    
    /**
     * Type of SiteWhere payload
     * @return
     */
    protected abstract String getPayloadType();
    
    /**
     * Encode Request of message
     * @return
     */
    protected abstract JsonObject encodeRequest();
    
    /**
     * Encode Metadata
     * @param metadata
     * @param jsonRequest
     */
    public static void encodeMetadata(Map<String, String> metadata, JsonObject json) {
	if (metadata == null)
	    return;

	JsonObject jsonMetadata = Json.object();
	for (String key : metadata.keySet()) {
	    String value = metadata.get(key);
	    jsonMetadata.add(key, value);
	}
	json.add(METADATA, jsonMetadata);
    }
}
