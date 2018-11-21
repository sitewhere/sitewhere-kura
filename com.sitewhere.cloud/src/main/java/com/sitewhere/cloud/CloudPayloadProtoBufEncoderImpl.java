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

import com.sitewhere.communication.protobuf.proto.SiteWhere;
import com.sitewhere.communication.protobuf.proto.SiteWhere.DeviceEvent.Command;
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
	
	SiteWhere.DeviceEvent.Header.Builder headerBuilder = SiteWhere.DeviceEvent.Header.newBuilder();
	
	headerBuilder.setCommand(Command.SEND_REGISTRATION);
	headerBuilder.setDeviceToken(
		headerBuilder.getDeviceTokenBuilder().setValue(
			getDeviceRegistrationPayload().getDeviceToken()).build());
	
	SiteWhere.DeviceEvent.Header header = headerBuilder.build();
	header.writeDelimitedTo(out);
	
	SiteWhere.DeviceEvent.DeviceRegistrationRequest.Builder payloadBuilder =
		SiteWhere.DeviceEvent.DeviceRegistrationRequest.newBuilder();
	
	payloadBuilder.getAreaTokenBuilder().setValue(getDeviceRegistrationPayload().getAreaToken());
	payloadBuilder.getCustomerTokenBuilder().setValue(getDeviceRegistrationPayload().getCustomerToken());
	payloadBuilder.getDeviceTypeTokenBuilder().setValue(getDeviceRegistrationPayload().getDeviceTypeToken());
	payloadBuilder.putAllMetadata(getDeviceRegistrationPayload().getMetadata());
	
	SiteWhere.DeviceEvent.DeviceRegistrationRequest payload = payloadBuilder.build();

	payload.writeDelimitedTo(out);
	
	out.close();
	return out.toByteArray();
    }
    
    private DeviceRegistrationPayload getDeviceRegistrationPayload() {
	return (DeviceRegistrationPayload) kuraPayload;
    }
}
