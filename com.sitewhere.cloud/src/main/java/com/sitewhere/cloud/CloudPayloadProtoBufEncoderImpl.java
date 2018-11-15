/*******************************************************************************
 * Copyright (c) 2011, 2017 Eurotech and/or its affiliates and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Eurotech
 *     Red Hat Inc
 *******************************************************************************/
package com.sitewhere.cloud;

import java.io.IOException;

import org.eclipse.kura.message.KuraPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sitewhere.device.DeviceRegistrationPayload;
import com.sitewhere.grpc.model.DeviceEventModel;

/**
 * Encodes an KuraPayload class using the Google ProtoBuf binary format.
 */
public class CloudPayloadProtoBufEncoderImpl implements CloudPayloadEncoder {

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
	
	DeviceEventModel.GDeviceRegistrationRequest.Builder builder = 
		DeviceEventModel.GDeviceRegistrationRequest.newBuilder();
	
	builder.getAreaTokenBuilder().setValue(getDeviceRegistrationPayload().getAreaToken());
	builder.getCustomerTokenBuilder().setValue(getDeviceRegistrationPayload().getCustomerToken());
	builder.getDeviceTypeTokenBuilder().setValue(getDeviceRegistrationPayload().getDeviceTypeToken());
	builder.putAllMetadata(getDeviceRegistrationPayload().getMetadata());
	
	return builder.build().toByteArray();
    }
    
    private DeviceRegistrationPayload getDeviceRegistrationPayload() {
	return (DeviceRegistrationPayload) kuraPayload;
    }
}
