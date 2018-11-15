package com.sitewhere.cloud;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.eclipse.kura.message.KuraPayload;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.sitewhere.device.DeviceRegistrationPayload;

public class SiteWhereCloudPlayloadJsonEncoder {
    
    public static byte[] getBytes(KuraPayload payload) {
	if (payload instanceof DeviceRegistrationPayload) {
	    return getDeviceRegistrationBytes((DeviceRegistrationPayload)payload);
	}
	return null;
    }

    private static byte[] getDeviceRegistrationBytes(DeviceRegistrationPayload payload) {
        JsonObject json = Json.object();
        
        json.add("type", "RegisterDevice");
        json.add("deviceToken", payload.getDeviceToken());
        
        encodeRequest(payload, json);

        return json.toString().getBytes(StandardCharsets.UTF_8);	
    }
    
    private static void encodeRequest(DeviceRegistrationPayload payload, JsonObject json) {
        JsonObject jsonRequest = Json.object();
        jsonRequest.add("areaToken", payload.getAreaToken());
        jsonRequest.add("customerToken", payload.getCustomerToken());
        jsonRequest.add("deviceTypeToken", payload.getDeviceTypeToken());
        
        encodeRequestMetadata(payload, jsonRequest);
        
        json.add("request", jsonRequest);
    }

    private static void encodeRequestMetadata(DeviceRegistrationPayload payload, JsonObject jsonRequest) {
        JsonObject jsonMetadata = Json.object();
        
        Map<String, String> metadata = payload.getMetadata();
        
        if (metadata != null) {
            for (String key : metadata.keySet()) {
        	String value = metadata.get(key);
        	jsonMetadata.add(key, value);
            }
        }
        
        jsonRequest.add("metadata", jsonMetadata);
    }

}
