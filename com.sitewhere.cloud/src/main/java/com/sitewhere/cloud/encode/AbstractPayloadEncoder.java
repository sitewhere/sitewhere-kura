/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.cloud.encode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sitewhere.cloud.payload.SiteWherePayload;

/**
 * Abstract Payload Encoder
 * @author Jorge Villaverde
 */
public abstract class AbstractPayloadEncoder implements PayloadEncoder {

    /** Logger */
    protected static final Logger logger = LoggerFactory.getLogger(AbstractPayloadEncoder.class);

    protected SiteWherePayload paylaod;
   
    public AbstractPayloadEncoder(SiteWherePayload paylaod) {
	super();
	this.paylaod = paylaod;
    }
    
    protected SiteWherePayload getPayload() {
	return this.paylaod;
    }
    
    protected Logger getLogger() {
	return logger;
    }
}
