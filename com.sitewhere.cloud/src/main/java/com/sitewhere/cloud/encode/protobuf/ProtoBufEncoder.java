/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.cloud.encode.protobuf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.google.protobuf.MessageLite;
import com.sitewhere.cloud.encode.AbstractPayloadEncoder;
import com.sitewhere.cloud.payload.SiteWherePayload;
import com.sitewhere.communication.protobuf.proto.SiteWhere;
import com.sitewhere.communication.protobuf.proto.SiteWhere.DeviceEvent.Command;

/**
 * SiteWhere Protocol Buffer Payload Encoder superclass
 * @author Jorge Villaverde
 */
public abstract class ProtoBufEncoder extends AbstractPayloadEncoder {
    
    public ProtoBufEncoder(SiteWherePayload paylaod) {
	super(paylaod);
    }
    
    /**
     * Conversion method to serialize an KuraPayload instance into a byte array.
     *
     * @return
     */
    @Override
    public byte[] getBytes() throws IOException {
	getLogger().debug("Create Protobuf payload");

	ByteArrayOutputStream out = new ByteArrayOutputStream();
	
	SiteWhere.DeviceEvent.Header.Builder headerBuilder = SiteWhere.DeviceEvent.Header.newBuilder();
	
	// Command
	headerBuilder.setCommand(getCommand());
	// Device Token
	headerBuilder.setDeviceToken(
		headerBuilder.getDeviceTokenBuilder().setValue(
			this.paylaod.getDeviceToken()).build());
	// Originator
	headerBuilder.setOriginator(
		headerBuilder.getOriginatorBuilder().setValue(
			this.paylaod.getOriginator()).build());
	
	SiteWhere.DeviceEvent.Header header = headerBuilder.build();
	header.writeDelimitedTo(out);
	
	MessageLite payload = buildPayload();
	payload.writeDelimitedTo(out);
	
	out.close();
	return out.toByteArray();
    }    

    /**
     * SiteWhere Message Command
     * @return
     */
    protected abstract Command getCommand();
    
    /**
     * Build the Payload
     * @return
     */
    protected abstract MessageLite buildPayload();
}
