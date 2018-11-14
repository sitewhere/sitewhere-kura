/**
 * 
 */
package com.sitewhere.device;

import org.eclipse.kura.message.KuraPayload;

/**
 * @author jorge
 *
 */
public class DeviceRegistrationPayload extends KuraPayload {

    private static final String DEVICE_TOKEN = "deviceToken";

    private static final String DEVICE_TYPE_TOKEN = "deviceTypeToken";

    private static final String CUSTOMER_TOKEN = "customerToken";

    private static final String AREA_TOKEN = "areaToken";

    public String getDeviceToken() {
	return (String) getMetric(DEVICE_TOKEN);
    }
    
    public String getDeviceTypeToken() {
	return (String) getMetric(DEVICE_TYPE_TOKEN);
    }

    public String getCustomerToken() {
	return (String) getMetric(CUSTOMER_TOKEN);
    }

    public String getAreaToken() {
	return (String) getMetric(AREA_TOKEN);
    }

    @Override
    public String toString() {
	final StringBuilder sb = new StringBuilder("DeviceRegistrationPayload [");

	sb.append("getDeviceToken()=").append(getDeviceToken()).append(", ");
	sb.append("getDeviceTypeToken()=").append(getDeviceTypeToken()).append(", ");
	sb.append("getCustomerToken()=").append(getCustomerToken()).append(", ");
	sb.append("getAreaToken()=").append(getAreaToken());

	sb.append("]");

	return sb.toString();
    }

    public static class DeviceRegistrationPayloadBuilder {

	private String deviceToken;
	
	private String deviceTypeToken;

	private String customerToken;

	private String areaToken;

	public DeviceRegistrationPayloadBuilder withDeviceTypeToken(String deviceTypeToken) {
	    this.deviceTypeToken = deviceTypeToken;
	    return this;
	}

	public DeviceRegistrationPayloadBuilder withDeviceToken(String deviceToken) {
	    this.deviceToken = deviceToken;
	    return this;
	}

	public DeviceRegistrationPayloadBuilder withCustomerToken(String customerToken) {
	    this.customerToken = customerToken;
	    return this;
	}

	public DeviceRegistrationPayloadBuilder withAreaToken(String areaToken) {
	    this.areaToken = areaToken;
	    return this;
	}

	public DeviceRegistrationPayload build() {
	    DeviceRegistrationPayload deviceRegistrationPayload = new DeviceRegistrationPayload();

	    if (this.deviceToken != null) {
		deviceRegistrationPayload.addMetric(DEVICE_TOKEN, this.deviceToken);
	    }
	    if (this.deviceTypeToken != null) {
		deviceRegistrationPayload.addMetric(DEVICE_TYPE_TOKEN, this.deviceTypeToken);
	    }
	    if (this.customerToken != null) {
		deviceRegistrationPayload.addMetric(CUSTOMER_TOKEN, this.customerToken);
	    }
	    if (this.areaToken != null) {
		deviceRegistrationPayload.addMetric(AREA_TOKEN, this.areaToken);
	    }
	    return deviceRegistrationPayload;
	}
    }

}
