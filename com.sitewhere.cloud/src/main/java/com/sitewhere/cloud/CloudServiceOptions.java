/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.cloud;

//import static org.eclipse.kura.core.cloud.CloudServiceLifecycleCertsPolicy.DISABLE_PUBLISHING;
//import static org.eclipse.kura.core.cloud.CloudServiceLifecycleCertsPolicy.PUBLISH_BIRTH_CONNECT_RECONNECT;

import java.util.Map;

import org.eclipse.kura.cloud.CloudPayloadEncoding;
import org.eclipse.kura.system.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SiteWhere Cloud Service Options for Eclipse Kura.
 * 
 * @author Jorge Villaverde
 */
public class CloudServiceOptions {

	private static final Logger logger = LoggerFactory.getLogger(CloudServiceOptions.class);

	private static final String TOPIC_SEPARATOR = "/";
	private static final String TOPIC_ACCOUNT_TOKEN = "#account-name";
	private static final String TOPIC_CLIENT_ID_TOKEN = "#client-id";
	private static final String TOPIC_BIRTH_SUFFIX = "MQTT/BIRTH";
	private static final String TOPIC_DISCONNECT_SUFFIX = "MQTT/DC";
	private static final String TOPIC_APPS_SUFFIX = "MQTT/APPS";
	private static final String TOPIC_CONTROL_PREFIX = "topic.control-prefix";
	private static final String TOPIC_CONTROL_PREFIX_DEFAULT = "$EDC";
	private static final String TOPIC_WILD_CARD = "#";

	private static final String DEVICE_DISPLAY_NAME = "device.display-name";
	private static final String DEVICE_CUSTOM_NAME = "device.custom-name";
	private static final String REPUB_BIRTH_ON_GPS_LOCK = "republish.mqtt.birth.cert.on.gps.lock";
	private static final String REPUB_BIRTH_ON_MODEM_DETECT = "republish.mqtt.birth.cert.on.modem.detect";
	private static final String ENABLE_DFLT_SUBSCRIPTIONS = "enable.default.subscriptions";
	private static final String BIRTH_CERT_POLICY = "birth.cert.policy";
	private static final String PAYLOAD_ENCODING = "payload.encoding";

	/** Application Tenant property name */
	private static final String APPLICATION_TENANT_NAME = "application.tenant";

	/** Application Area Token property name */
	private static final String APPLICATION_AREA_TOKEN = "application.area";

	/** Default Application Area Token value */
	private static final String APPLICATION_AREA_TOKEN_DEFAULT = "southeast";

	/** Application Customer Token property name */
	private static final String APPLICATION_CUSTOMER_TOKEN = "application.customer";

	/** Default Application Customer Token value */
	private static final String APPLICATION_CUSTOMER_TOKEN_DEFAULT = "acme";

	/** Device Type Token property name */
	private static final String DEVICE_TYPE_TOKEN = "device.type";

	/** Default Application Customer Token value */
	private static final String DEVICE_TYPE_TOKEN_DEFAULT = "raspberrypi";

	/** SiteWhere Topic Prefix */
	private static final String SITEWHERE_PREFIX = "SiteWhere";

	/** Default SiteWhere tenant */
	private static final String APPLICATION_TENANT_NAME_DEFAULT = "default";

	/** INPUT Topic */
	private static final String INPUT_TOPIC = "input";

	/** JSON Topic */
	private static final String JSON_TOPIC = "json";

	/** Protocol Buffer Topic */
	private static final String PROTOBUF_TOPIC = "protobuf";

	private static final int LIFECYCLE_QOS = 0;
	private static final int LIFECYCLE_PRIORITY = 0;
	private static final boolean LIFECYCLE_RETAIN = false;

	private final Map<String, Object> properties;
	private final SystemService systemService;

	public CloudServiceOptions(Map<String, Object> properties, SystemService systemService) {
		this.properties = properties;
		this.systemService = systemService;
	}

	/**
	 * Returns the display name for the device.
	 *
	 * @return a String value.
	 */
	public String getDeviceDisplayName() {
		String displayName = "";
		if (this.properties == null) {
			return displayName;
		}
		String deviceDisplayNameOption = (String) this.properties.get(DEVICE_DISPLAY_NAME);

		// Use the device name from SystemService. This should be kura.device.name from
		// the properties file.
		if ("device-name".equals(deviceDisplayNameOption)) {
			displayName = this.systemService.getDeviceName();
		}
		// Try to get the device hostname
		else if ("hostname".equals(deviceDisplayNameOption)) {
			displayName = this.systemService.getHostname();
		}
		// Return the custom field defined by the user
		else if ("custom".equals(deviceDisplayNameOption)
				&& this.properties.get(DEVICE_CUSTOM_NAME) instanceof String) {
			displayName = (String) this.properties.get(DEVICE_CUSTOM_NAME);
		}
		// Return empty string to the server
		else if ("server".equals(deviceDisplayNameOption)) {
			displayName = "";
		}

		return displayName;
	}

	/**
	 * Returns true if the current CloudService configuration specifies the cloud
	 * client should republish the MQTT birth certificate on GPS lock events.
	 *
	 * @return a boolean value.
	 */
	public boolean getRepubBirthCertOnGpsLock() {
		boolean repubBirth = false;
		if (this.properties != null && this.properties.get(REPUB_BIRTH_ON_GPS_LOCK) instanceof Boolean) {
			repubBirth = (Boolean) this.properties.get(REPUB_BIRTH_ON_GPS_LOCK);
		}
		return repubBirth;
	}

	/**
	 * Returns true if the current CloudService configuration specifies the cloud
	 * client should republish the MQTT birth certificate on modem detection events.
	 *
	 * @return a boolean value.
	 */
	public boolean getRepubBirthCertOnModemDetection() {
		boolean repubBirth = false;
		if (this.properties != null && this.properties.get(REPUB_BIRTH_ON_MODEM_DETECT) instanceof Boolean) {
			repubBirth = (Boolean) this.properties.get(REPUB_BIRTH_ON_MODEM_DETECT);
		}
		return repubBirth;
	}

	/**
	 * Returns the prefix to be used when publishing messages to control topics.
	 *
	 * @return a String value.
	 */
	public String getTopicControlPrefix() {
		String prefix = TOPIC_CONTROL_PREFIX_DEFAULT;
		if (this.properties != null && this.properties.get(TOPIC_CONTROL_PREFIX) instanceof String) {
			prefix = (String) this.properties.get(TOPIC_CONTROL_PREFIX);
		}
		return prefix;
	}

	/**
	 * Returns true if the current CloudService configuration specifies that the
	 * cloud client should perform default subscriptions.
	 *
	 * @return a boolean value.
	 */
	public boolean getEnableDefaultSubscriptions() {
		boolean enable = true;
		if (this.properties != null && this.properties.get(ENABLE_DFLT_SUBSCRIPTIONS) instanceof Boolean) {
			enable = (Boolean) this.properties.get(ENABLE_DFLT_SUBSCRIPTIONS);
		}
		return enable;
	}

	/**
	 * This method parses the Cloud Service configuration and returns true if the
	 * Cloud Service instance should not publish lifecycle messages.
	 *
	 * @return a boolean value.
	 */
	@SuppressWarnings("unused")
	public boolean isLifecycleCertsDisabled() {
		boolean birthPubDisabled = false;
		String birthPubPolicy = "";
		if (this.properties != null && this.properties.get(BIRTH_CERT_POLICY) instanceof String) {
			birthPubPolicy = (String) this.properties.get(BIRTH_CERT_POLICY);
		}
		// TODO
//        if (DISABLE_PUBLISHING.getValue().equals(birthPubPolicy)) {
//            birthPubDisabled = true;
//        }
		return birthPubDisabled;
	}

	/**
	 * This method parses the Cloud Service configuration and returns true if the
	 * Cloud Service instance should republish the birth message on a reconnection.
	 * By default, this method returns true.
	 *
	 * @return a boolean value.
	 */
	@SuppressWarnings("unused")
	public boolean getRepubBirthCertOnReconnect() {
		boolean republishBirt = true;
		String birthPubPolicy = "";
		if (this.properties != null && this.properties.get(BIRTH_CERT_POLICY) instanceof String) {
			birthPubPolicy = (String) this.properties.get(BIRTH_CERT_POLICY);
		}
		// TODO
//        if (!PUBLISH_BIRTH_CONNECT_RECONNECT.getValue().equals(birthPubPolicy)) {
//            republishBirt = false;
//        }
		return republishBirt;
	}

	/**
	 * Returns the application tenant name for the device.
	 *
	 * @return a String value.
	 */
	public String getApplicationTenantName() {
		String tenantName = APPLICATION_TENANT_NAME_DEFAULT;
		if (this.properties != null && this.properties.get(APPLICATION_TENANT_NAME) instanceof String) {
			tenantName = (String) this.properties.get(APPLICATION_TENANT_NAME);
		}
		return tenantName;
	}

	/**
	 * Returns the application area token for the device.
	 *
	 * @return a String value.
	 */
	public String getApplicationAreaToken() {
		String areaToken = APPLICATION_AREA_TOKEN_DEFAULT;
		if (this.properties != null && this.properties.get(APPLICATION_AREA_TOKEN) instanceof String) {
			areaToken = (String) this.properties.get(APPLICATION_AREA_TOKEN);
		}
		return areaToken;
	}

	/**
	 * Returns the application area token for the device.
	 *
	 * @return a String value.
	 */
	public String getApplicationCustomerToken() {
		String customerToken = APPLICATION_CUSTOMER_TOKEN_DEFAULT;
		if (this.properties != null && this.properties.get(APPLICATION_CUSTOMER_TOKEN) instanceof String) {
			customerToken = (String) this.properties.get(APPLICATION_CUSTOMER_TOKEN);
		}
		return customerToken;
	}

	/**
	 * Returns the application area token for the device.
	 *
	 * @return a String value.
	 */
	public String getDeviceTypeToken() {
		String deviceTypeToken = DEVICE_TYPE_TOKEN_DEFAULT;
		if (this.properties != null && this.properties.get(DEVICE_TYPE_TOKEN) instanceof String) {
			deviceTypeToken = (String) this.properties.get(DEVICE_TYPE_TOKEN);
		}
		return deviceTypeToken;
	}

	/**
	 * This method parses the Cloud Service configuration and returns the selected
	 * cloud payload encoding. By default, this method returns
	 * {@link CloudPayloadEncoding} {@code KURA_PROTOBUF}.
	 *
	 * @return a boolean value.
	 */
	public CloudPayloadEncoding getPayloadEncoding() {
		CloudPayloadEncoding result = CloudPayloadEncoding.KURA_PROTOBUF;
		String encodingString = "";
		if (this.properties != null && this.properties.get(PAYLOAD_ENCODING) != null
				&& this.properties.get(PAYLOAD_ENCODING) instanceof String) {
			encodingString = (String) this.properties.get(PAYLOAD_ENCODING);
		}
		try {
			result = CloudPayloadEncoding.getEncoding(encodingString);
		} catch (IllegalArgumentException e) {
			logger.warn("Cannot parse the provided payload encoding.", e);
		}

		return result;
	}

	public static String getTopicSeparator() {
		return TOPIC_SEPARATOR;
	}

	public static String getTopicAccountToken() {
		return TOPIC_ACCOUNT_TOKEN;
	}

	public static String getTopicClientIdToken() {
		return TOPIC_CLIENT_ID_TOKEN;
	}

	public static String getTopicBirthSuffix() {
		return TOPIC_BIRTH_SUFFIX;
	}

	public static String getTopicDisconnectSuffix() {
		return TOPIC_DISCONNECT_SUFFIX;
	}

	public static String getTopicAppsSuffix() {
		return TOPIC_APPS_SUFFIX;
	}

	public static String getTopicWildCard() {
		return TOPIC_WILD_CARD;
	}

	public static int getLifeCycleMessageQos() {
		return LIFECYCLE_QOS;
	}

	public static int getLifeCycleMessagePriority() {
		return LIFECYCLE_PRIORITY;
	}

	public static boolean getLifeCycleMessageRetain() {
		return LIFECYCLE_RETAIN;
	}

	public String getSiteWhereTopic() {
		StringBuilder builder = new StringBuilder();

		CloudPayloadEncoding encoding = getPayloadEncoding();

		builder.append(SITEWHERE_PREFIX);
		builder.append(TOPIC_SEPARATOR);
		builder.append(getApplicationTenantName());
		builder.append(TOPIC_SEPARATOR);
		builder.append(INPUT_TOPIC);
		builder.append(TOPIC_SEPARATOR);

		if (CloudPayloadEncoding.KURA_PROTOBUF.equals(encoding)) {
			builder.append(PROTOBUF_TOPIC);
		} else if (CloudPayloadEncoding.SIMPLE_JSON.equals(encoding)) {
			builder.append(JSON_TOPIC);
		}

		return builder.toString();
	}
}
