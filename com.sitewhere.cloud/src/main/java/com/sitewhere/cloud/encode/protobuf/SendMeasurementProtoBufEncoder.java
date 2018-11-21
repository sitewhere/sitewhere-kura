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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.MessageLite;
import com.sitewhere.cloud.encode.PayloadEncoder;
import com.sitewhere.cloud.payload.DeviceRegistrationPayload;
import com.sitewhere.cloud.payload.SendMeasurementPayload;
import com.sitewhere.communication.protobuf.proto.SiteWhere;
import com.sitewhere.communication.protobuf.proto.SiteWhere.DeviceEvent.Command;
import com.sitewhere.communication.protobuf.proto.SiteWhere.DeviceEvent.Measurement;
import com.sitewhere.communication.protobuf.proto.SiteWhere.GOptionalFixed64;

/**
 * Encodes SiteWhere Measurement Message using Protocol Buffer
 * 
 * @author Jorge Villaverde
 */

public class SendMeasurementProtoBufEncoder extends ProtoBufEncoder {

    public SendMeasurementProtoBufEncoder(SendMeasurementPayload payload) {
	super(payload);
    }

    @Override
    protected Command getCommand() {
	return Command.SEND_MEASUREMENT;
    }

    @Override
    protected MessageLite buildPayload() {
	SiteWhere.DeviceEvent.DeviceMeasurements.Builder builder = SiteWhere.DeviceEvent.DeviceMeasurements
		.newBuilder();

	builder.setEventDate(GOptionalFixed64.newBuilder().setValue(getPayload().getEventDate()).build());

	builder.addMeasurement(Measurement.newBuilder()
		.setMeasurementId(builder.addMeasurementBuilder().getMeasurementIdBuilder()
			.setValue(getPayload().getMeasurementId()).build())
		.setMeasurementValue(builder.addMeasurementBuilder().getMeasurementValueBuilder()
			.setValue(getPayload().getMeasurementValue()).build())
		.build());

	return builder.build();
    }

    @Override
    protected SendMeasurementPayload getPayload() {
	return (SendMeasurementPayload) super.getPayload();
    }

}
