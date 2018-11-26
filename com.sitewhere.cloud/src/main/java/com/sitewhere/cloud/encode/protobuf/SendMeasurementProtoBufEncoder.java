/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.cloud.encode.protobuf;

import com.google.protobuf.MessageLite;
import com.sitewhere.cloud.payload.SendMeasurementPayload;
import com.sitewhere.communication.protobuf.proto.SiteWhere;
import com.sitewhere.communication.protobuf.proto.SiteWhere.DeviceEvent.Command;
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
	return Command.SendMeasurement;
    }

    @Override
    protected MessageLite buildPayload() {
	SiteWhere.DeviceEvent.DeviceMeasurement.Builder builder = SiteWhere.DeviceEvent.DeviceMeasurement
		.newBuilder();

	builder.setEventDate(GOptionalFixed64.newBuilder().setValue(getPayload().getEventDate()).build());

	builder.getMeasurementNameBuilder().setValue(getPayload().getMeasurementId());
	builder.getMeasurementValueBuilder().setValue(getPayload().getMeasurementValue());

	return builder.build();
    }

    @Override
    protected SendMeasurementPayload getPayload() {
	return (SendMeasurementPayload) super.getPayload();
    }

}
