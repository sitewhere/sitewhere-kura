/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.cloud;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.eclipse.kura.message.KuraPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sitewhere.communication.protobuf.proto3.SiteWhere2;
import com.sitewhere.communication.protobuf.proto3.SiteWhere2.DeviceEvent.Command;
import com.sitewhere.device.DeviceRegistrationPayload;

/**
 * Encodes Device Registration Message using Protocol Buffer
 * 
 * @author Jorge Villaverde
 */
public class CloudPayloadProtoBufEncoderImpl implements PayloadEncoder {

    private static final Logger logger = LoggerFactory.getLogger(CloudPayloadProtoBufEncoderImpl.class);

    private final KuraPayload kuraPayload;

    public CloudPayloadProtoBufEncoderImpl(KuraPayload kuraPayload) {
        this.kuraPayload = kuraPayload;
    }

    /**
     * Conversion method to serialize an KuraPayload instance into a byte array.
     *
     * @return
     */
    @Override
    public byte[] getBytes() throws IOException {
	logger.debug("Create Device Registration Protobuf payload");
	
	ByteArrayOutputStream out = new ByteArrayOutputStream();
	
	SiteWhere2.DeviceEvent.Header.Builder headerBuilder = SiteWhere2.DeviceEvent.Header.newBuilder();
	
	headerBuilder.setCommand(Command.Registration);
	headerBuilder.setDeviceToken(
		headerBuilder.getDeviceTokenBuilder().setValue(
			getDeviceRegistrationPayload().getDeviceToken()).build());
	
	SiteWhere2.DeviceEvent.Header header = headerBuilder.build();
	header.writeDelimitedTo(out);
	
	SiteWhere2.DeviceEvent.DeviceRegistrationRequest.Builder payloadBuilder =
		SiteWhere2.DeviceEvent.DeviceRegistrationRequest.newBuilder();
	
	payloadBuilder.getAreaTokenBuilder().setValue(getDeviceRegistrationPayload().getAreaToken());
	payloadBuilder.getCustomerTokenBuilder().setValue(getDeviceRegistrationPayload().getCustomerToken());
	payloadBuilder.getDeviceTypeTokenBuilder().setValue(getDeviceRegistrationPayload().getDeviceTypeToken());
	payloadBuilder.putAllMetadata(getDeviceRegistrationPayload().getMetadata());
	
	SiteWhere2.DeviceEvent.DeviceRegistrationRequest payload = payloadBuilder.build();

	payload.writeDelimitedTo(out);
	
	out.close();
	return out.toByteArray();
    }
    
    private DeviceRegistrationPayload getDeviceRegistrationPayload() {
	return (DeviceRegistrationPayload) kuraPayload;
    }
}
