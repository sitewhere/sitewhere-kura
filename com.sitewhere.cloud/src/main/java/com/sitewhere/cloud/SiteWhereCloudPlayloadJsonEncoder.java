package com.sitewhere.cloud;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.eclipse.kura.message.KuraPayload;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.sitewhere.device.DeviceRegistrationPayload;
import com.sitewhere.device.SendMeasurementePayload;

public class SiteWhereCloudPlayloadJsonEncoder {
    
    public static byte[] getBytes(KuraPayload payload) {
	if (payload instanceof DeviceRegistrationPayload) {
	    return getDeviceRegistrationBytes((DeviceRegistrationPayload)payload);
	} else if (payload instanceof SendMeasurementePayload) {
	    return getSendMeasurementBytes((SendMeasurementePayload)payload);
	}
	return null;
    }

    private static byte[] getSendMeasurementBytes(SendMeasurementePayload payload) {
        JsonObject json = Json.object();
        
        json.add("type", "DeviceMeasurements");
        json.add("originator", "device");
        json.add("deviceToken", payload.getDeviceToken());
        
        encodeMeasurementsRequest(payload, json);

        return json.toString().getBytes(StandardCharsets.UTF_8);	
    }

    private static byte[] getDeviceRegistrationBytes(DeviceRegistrationPayload payload) {
        JsonObject json = Json.object();
        
        json.add("type", "RegisterDevice");
        json.add("originator", "device");
        json.add("deviceToken", payload.getDeviceToken());
        
        encodeRegistrationRequest(payload, json);

        return json.toString().getBytes(StandardCharsets.UTF_8);	
    }
    
    private static void encodeRegistrationRequest(DeviceRegistrationPayload payload, JsonObject json) {
        JsonObject jsonRequest = Json.object();
        jsonRequest.add("areaToken", payload.getAreaToken());
        jsonRequest.add("customerToken", payload.getCustomerToken());
        jsonRequest.add("deviceTypeToken", payload.getDeviceTypeToken());
        
        encodeRequestMetadata(payload, jsonRequest);
        
        json.add("request", jsonRequest);
    }

    private static void encodeMeasurementsRequest(SendMeasurementePayload payload, JsonObject json) {
        JsonObject jsonRequest = Json.object();
	
        jsonRequest.add("name", payload.getName());
        jsonRequest.add("value", String.valueOf(payload.getValue()));
	
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
