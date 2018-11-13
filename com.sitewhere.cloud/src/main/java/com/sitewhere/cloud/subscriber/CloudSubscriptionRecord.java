/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.cloud.subscriber;

import org.eclipse.kura.core.util.MqttTopicUtil;

import com.sitewhere.cloud.CloudServiceOptions;

/**
 * SiteWhere Cloud Subscription Record for Eclipse Kura.
 * 
 * @author Jorge Villaverde
 */
public class CloudSubscriptionRecord {

    private final String topic;
    private final int qos;

    private String topicFilter;
    
    public CloudSubscriptionRecord(String topic, int qos) {
	super();
	this.topic = topic;
	this.qos = qos;
    }

    public String getTopic() {
        return this.topic;
    }

    public int getQos() {
        return this.qos;
    }

    public boolean matches(final String topic) {
        if (topicFilter == null) {
            topicFilter = this.topic.replaceAll(CloudServiceOptions.getTopicAccountToken(), "+")
                    .replaceAll(CloudServiceOptions.getTopicClientIdToken(), "+");
        }
        return MqttTopicUtil.isMatched(this.topicFilter, topic);
    }

    @Override
    public int hashCode() {
        return topic.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof CloudSubscriptionRecord)) {
            return false;
        }
        return ((CloudSubscriptionRecord) obj).topic.equals(topic);
    }

}
