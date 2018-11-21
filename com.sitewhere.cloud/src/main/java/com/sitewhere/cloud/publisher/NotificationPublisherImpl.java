/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.cloud.publisher;

import static java.util.Objects.isNull;
import static org.eclipse.kura.core.message.MessageConstants.CONTROL;
import static org.eclipse.kura.core.message.MessageConstants.PRIORITY;
import static org.eclipse.kura.core.message.MessageConstants.QOS;
import static org.eclipse.kura.core.message.MessageConstants.RETAIN;
import static org.eclipse.kura.core.message.MessageConstants.FULL_TOPIC;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.kura.KuraErrorCode;
import org.eclipse.kura.KuraException;
import org.eclipse.kura.cloudconnection.listener.CloudConnectionListener;
import org.eclipse.kura.cloudconnection.listener.CloudDeliveryListener;
import org.eclipse.kura.cloudconnection.message.KuraMessage;
import org.eclipse.kura.cloudconnection.publisher.CloudNotificationPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sitewhere.cloud.CloudServiceOptions;
import com.sitewhere.cloud.SiteWhereCloudServiceImpl;

/**
 * SiteWhere Cloud Notification Publisher Implementation for Eclipse Kura.
 * 
 * @author Jorge Villaverde
 */
public class NotificationPublisherImpl implements CloudNotificationPublisher {

    /** Logger */
    private static final Logger logger = LoggerFactory.getLogger(NotificationPublisherImpl.class);

    private SiteWhereCloudServiceImpl siteWhereCloudServiceImpl;

    private static final String TOPIC_PATTERN_STRING = "\\$([^\\s/]+)";
    private static final Pattern TOPIC_PATTERN = Pattern.compile(TOPIC_PATTERN_STRING);

    private static final int DFLT_PUB_QOS = 0;
    private static final boolean DFLT_RETAIN = false;
    private static final int DFLT_PRIORITY = 1;

    private static final String MESSAGE_TYPE_KEY = "messageType";

    private static final String REQUESTOR_CLIENT_ID_KEY = "requestorClientId";

    private static final String APP_ID_KEY = "appId";

    public NotificationPublisherImpl(SiteWhereCloudServiceImpl siteWhereCloudServiceImpl) {
	super();
	this.siteWhereCloudServiceImpl = siteWhereCloudServiceImpl;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.kura.cloudconnection.publisher.CloudPublisher#publish(org.eclipse
     * .kura.cloudconnection.message.KuraMessage)
     */
    @Override
    public String publish(KuraMessage message) throws KuraException {
	logger.info("Public Kura Message to SiteWhere: " + message);

	if (this.siteWhereCloudServiceImpl == null) {
	    logger.warn("Null cloud connection");
	    throw new KuraException(KuraErrorCode.SERVICE_UNAVAILABLE);
	}

	if (message == null) {
	    logger.warn("Received null message!");
	    throw new IllegalArgumentException();
	}

	String fullTopic = encodeFullTopic(message);

	Map<String, Object> publishMessageProps = new HashMap<>();
	publishMessageProps.put(FULL_TOPIC.name(), fullTopic);
	publishMessageProps.put(QOS.name(), DFLT_PUB_QOS);
	publishMessageProps.put(RETAIN.name(), DFLT_RETAIN);
	publishMessageProps.put(PRIORITY.name(), DFLT_PRIORITY);
	publishMessageProps.put(CONTROL.name(), true);

	KuraMessage publishMessage = new KuraMessage(message.getPayload(), publishMessageProps);

	return this.siteWhereCloudServiceImpl.publish(publishMessage);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.kura.cloudconnection.publisher.CloudPublisher#
     * registerCloudConnectionListener(org.eclipse.kura.cloudconnection.listener.
     * CloudConnectionListener)
     */
    @Override
    public void registerCloudConnectionListener(CloudConnectionListener arg0) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.kura.cloudconnection.publisher.CloudPublisher#
     * registerCloudDeliveryListener(org.eclipse.kura.cloudconnection.listener.
     * CloudDeliveryListener)
     */
    @Override
    public void registerCloudDeliveryListener(CloudDeliveryListener arg0) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.kura.cloudconnection.publisher.CloudPublisher#
     * unregisterCloudConnectionListener(org.eclipse.kura.cloudconnection.listener.
     * CloudConnectionListener)
     */
    @Override
    public void unregisterCloudConnectionListener(CloudConnectionListener arg0) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.kura.cloudconnection.publisher.CloudPublisher#
     * unregisterCloudDeliveryListener(org.eclipse.kura.cloudconnection.listener.
     * CloudDeliveryListener)
     */
    @Override
    public void unregisterCloudDeliveryListener(CloudDeliveryListener arg0) {
    }

    private String encodeFullTopic(KuraMessage message) {
	String appId = (String) message.getProperties().get(APP_ID_KEY);
	String messageType = (String) message.getProperties().get(MESSAGE_TYPE_KEY);
	String requestorClientId = (String) message.getProperties().get(REQUESTOR_CLIENT_ID_KEY);

	if (isNull(appId) || isNull(messageType) || isNull(requestorClientId)) {
	    throw new IllegalArgumentException("Incomplete properties in received message.");
	}

	String fullTopic = encodeTopic(appId, messageType, requestorClientId);
	return fillAppTopicPlaceholders(fullTopic, message);
    }

    private String encodeTopic(String appId, String messageType, String requestorClientId) {
        CloudServiceOptions options = this.siteWhereCloudServiceImpl.getCloudServiceOptions();
        String deviceId = CloudServiceOptions.getTopicClientIdToken();
        String topicSeparator = CloudServiceOptions.getTopicSeparator();

        StringBuilder sb = new StringBuilder();

        sb.append(options.getTopicControlPrefix())
          .append(topicSeparator);
        
        sb.append(CloudServiceOptions.getTopicAccountToken())
          .append(topicSeparator)
          .append(requestorClientId)
          .append(topicSeparator)
          .append(appId);

        sb.append(topicSeparator)
          .append("NOTIFY")
          .append(topicSeparator)
          .append(deviceId)
          .append(topicSeparator)
          .append(messageType);

        return sb.toString();
    }

    private String fillAppTopicPlaceholders(String fullTopic, KuraMessage message) {
        Matcher matcher = TOPIC_PATTERN.matcher(fullTopic);
        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            Map<String, Object> properties = message.getProperties();
            if (properties.containsKey(matcher.group(1))) {
                String replacement = matcher.group(0);

                Object value = properties.get(matcher.group(1));
                if (replacement != null) {
                    matcher.appendReplacement(buffer, value.toString());
                }
            }
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }
}
