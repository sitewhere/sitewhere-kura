/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.cloud;

import org.eclipse.kura.cloud.CloudClientListener;
import org.eclipse.kura.message.KuraPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SiteWhere CloudClientListerner Implementation for Eclipse Kura.
 * 
 * @author Jorge Villaverde
 */
@SuppressWarnings("deprecation")
public class CloudClientListenerAdapter implements CloudClientListener {
    
    private static final String IGNORED_ERROR_NOTIFYING = "IGNORED - Error notifying ";

    private static final Logger s_logger = LoggerFactory.getLogger(CloudClientListenerAdapter.class);

    private final CloudClientListener m_listener;

    CloudClientListenerAdapter(CloudClientListener listener) {
        this.m_listener = listener;
    }

    public CloudClientListener getCloudClientListenerAdapted() {
        return this.m_listener;
    }

    @Override
    public void onControlMessageArrived(String deviceId, String appTopic, KuraPayload msg, int qos, boolean retain) {
        try {
            this.m_listener.onControlMessageArrived(deviceId, appTopic, msg, qos, retain);
        } catch (Exception e) {
            s_logger.error(IGNORED_ERROR_NOTIFYING + this.m_listener + " for onControlMessageArrived", e);
        }
    }

    @Override
    public void onMessageArrived(String deviceId, String appTopic, KuraPayload msg, int qos, boolean retain) {
        try {
            this.m_listener.onMessageArrived(deviceId, appTopic, msg, qos, retain);
        } catch (Exception e) {
            s_logger.error(IGNORED_ERROR_NOTIFYING + this.m_listener + " for onMessageArrived", e);
        }
    }

    @Override
    public void onConnectionLost() {
        try {
            this.m_listener.onConnectionLost();
        } catch (Exception e) {
            s_logger.error(IGNORED_ERROR_NOTIFYING + this.m_listener + " for onConnectionLost", e);
        }
    }

    @Override
    public void onConnectionEstablished() {
        try {
            this.m_listener.onConnectionEstablished();
        } catch (Exception e) {
            s_logger.error(IGNORED_ERROR_NOTIFYING + this.m_listener + " for onConnectionEstablished", e);
        }
    }

    @Override
    public void onMessageConfirmed(int messageId, String appTopic) {
        try {
            this.m_listener.onMessageConfirmed(messageId, appTopic);
        } catch (Exception e) {
            s_logger.error(IGNORED_ERROR_NOTIFYING + this.m_listener + " for onMessageConfirmed", e);
        }
    }

    @Override
    public void onMessagePublished(int messageId, String appTopic) {
        try {
            this.m_listener.onMessagePublished(messageId, appTopic);
        } catch (Exception e) {
            s_logger.error(IGNORED_ERROR_NOTIFYING + this.m_listener + " for onMessagePublished", e);
        }
    }
}
