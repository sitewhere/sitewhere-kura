/*******************************************************************************
 * Copyright (c) 2018 Eurotech and/or its affiliates and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package com.sitewhere.cloud.publisher;

import java.util.Map;

import org.eclipse.kura.cloudconnection.CloudConnectionConstants;

public class CloudPublisherOptions {

    private static final Property<String> PROPERTY_CLOUD_SERVICE_PID = new Property<>(
            CloudConnectionConstants.CLOUD_ENDPOINT_SERVICE_PID_PROP_NAME.value(), "com.sitewhere.cloud.SiteWhereCloudService");
    private static final Property<String> PROPERTY_APP_ID = new Property<>("appId", "W1");
    private static final Property<String> PROPERTY_APP_TOPIC = new Property<>("app.topic", "A1/$assetName");
    private static final Property<Integer> PROPERTY_QOS = new Property<>("qos", 0);
    private static final Property<Boolean> PROPERTY_RETAIN = new Property<>("retain", false);
    private static final Property<Integer> PROPERTY_PRIORITY = new Property<>("priority", 7);

    private final String cloudServicePid;
    private final String appId;
    private final String appTopic;
    private final int qos;
    private final boolean retain;
    private final int priority;

    public CloudPublisherOptions(final Map<String, Object> properties) {
        this.cloudServicePid = PROPERTY_CLOUD_SERVICE_PID.get(properties);
        this.appId = PROPERTY_APP_ID.get(properties);
        this.appTopic = PROPERTY_APP_TOPIC.get(properties);
        this.qos = PROPERTY_QOS.get(properties);
        this.retain = PROPERTY_RETAIN.get(properties);
        this.priority = PROPERTY_PRIORITY.get(properties);
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

    public boolean isRetain() {
        return this.retain;
    }

    public int getPriority() {
        return this.priority;
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
