/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.cloud.subscriber;

import java.util.Map;

import org.eclipse.kura.cloudconnection.CloudConnectionConstants;
import org.eclipse.kura.core.message.MessageType;

/**
 * SiteWhere Cloud Subscription Options for Eclipse Kura.
 * 
 * @author Jorge Villaverde
 */
public class CloudSubscriberOptions {

    private static final Property<String> PROPERTY_CLOUD_SERVICE_PID = new Property<>(
            CloudConnectionConstants.CLOUD_ENDPOINT_SERVICE_PID_PROP_NAME.value(), "com.sitewhere.cloud.SiteWhereCloudService");
    private static final Property<String> PROPERTY_APP_ID = new Property<>("appId", "appId");
    private static final Property<String> PROPERTY_APP_TOPIC = new Property<>("app.topic", "#");
    private static final Property<Integer> PROPERTY_QOS = new Property<>("qos", 0);
    private static final Property<String> PROPERTY_MESSAGE_TYPE = new Property<>("message.type", "data");

    private final String cloudServicePid;
    private final String appId;
    private final String appTopic;
    private final int qos;
    private final String messageType;

    public CloudSubscriberOptions(final Map<String, Object> properties) {
        this.cloudServicePid = PROPERTY_CLOUD_SERVICE_PID.get(properties);
        this.appId = PROPERTY_APP_ID.get(properties);
        this.appTopic = PROPERTY_APP_TOPIC.get(properties);
        this.qos = PROPERTY_QOS.get(properties);
        this.messageType = PROPERTY_MESSAGE_TYPE.get(properties);
    }

    public String getCloudServicePid() {
        return this.cloudServicePid;
    }

    public String getAppId() {
        return this.appId;
    }

    public String getAppTopic() {
        return this.appTopic;
    }

    public int getQos() {
        return this.qos;
    }

    public MessageType getMessageType() {
        return MessageType.fromValue(this.messageType);
    }

    private static final class Property<T> {

        private final String key;
        private final T defaultValue;

        public Property(final String key, final T defaultValue) {
            this.key = key;
            this.defaultValue = defaultValue;
        }

        @SuppressWarnings("unchecked")
        public T get(final Map<String, Object> properties) {
            final Object value = properties.get(this.key);

            if (this.defaultValue.getClass().isInstance(value)) {
                return (T) value;
            }
            return this.defaultValue;
        }
    }

}
