/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.cloud.publisher;

import org.eclipse.kura.KuraException;
import org.eclipse.kura.cloudconnection.listener.CloudConnectionListener;
import org.eclipse.kura.cloudconnection.listener.CloudDeliveryListener;
import org.eclipse.kura.cloudconnection.message.KuraMessage;
import org.eclipse.kura.cloudconnection.publisher.CloudNotificationPublisher;

import com.sitewhere.cloud.SiteWhereCloudServiceImpl;

/**
 * SiteWhere Cloud Notification Publisher Implementation for Eclipse Kura.
 * 
 * @author Jorge Villaverde
 */
public class NotificationPublisherImpl implements CloudNotificationPublisher {

    private SiteWhereCloudServiceImpl siteWhereCloudServiceImpl;
    
    public NotificationPublisherImpl(SiteWhereCloudServiceImpl siteWhereCloudServiceImpl) {
	super();
	this.siteWhereCloudServiceImpl = siteWhereCloudServiceImpl;
    }

    /* (non-Javadoc)
     * @see org.eclipse.kura.cloudconnection.publisher.CloudPublisher#publish(org.eclipse.kura.cloudconnection.message.KuraMessage)
     */
    @Override
    public String publish(KuraMessage arg0) throws KuraException {
	// TODO Auto-generated method stub
	return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.kura.cloudconnection.publisher.CloudPublisher#registerCloudConnectionListener(org.eclipse.kura.cloudconnection.listener.CloudConnectionListener)
     */
    @Override
    public void registerCloudConnectionListener(CloudConnectionListener arg0) {
	// TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see org.eclipse.kura.cloudconnection.publisher.CloudPublisher#registerCloudDeliveryListener(org.eclipse.kura.cloudconnection.listener.CloudDeliveryListener)
     */
    @Override
    public void registerCloudDeliveryListener(CloudDeliveryListener arg0) {
	// TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see org.eclipse.kura.cloudconnection.publisher.CloudPublisher#unregisterCloudConnectionListener(org.eclipse.kura.cloudconnection.listener.CloudConnectionListener)
     */
    @Override
    public void unregisterCloudConnectionListener(CloudConnectionListener arg0) {
	// TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see org.eclipse.kura.cloudconnection.publisher.CloudPublisher#unregisterCloudDeliveryListener(org.eclipse.kura.cloudconnection.listener.CloudDeliveryListener)
     */
    @Override
    public void unregisterCloudDeliveryListener(CloudDeliveryListener arg0) {
	// TODO Auto-generated method stub

    }

}
