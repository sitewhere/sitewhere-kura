package com.sitewhere.cloud;

import java.nio.charset.StandardCharsets;

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
        json.add("request", jsonRequest);
    }

}
