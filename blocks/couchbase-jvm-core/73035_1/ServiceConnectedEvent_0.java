
package com.couchbase.client.core.event.system;

import com.couchbase.client.core.event.CouchbaseEvent;
import com.couchbase.client.core.event.EventType;
import com.couchbase.client.core.service.ServiceType;
import com.couchbase.client.core.utils.Events;

import java.net.InetAddress;
import java.util.Map;

public class ServiceConnectedEvent implements CouchbaseEvent {

    private final InetAddress host;
    private final ServiceType serviceType;

    public ServiceConnectedEvent(InetAddress host, ServiceType serviceType) {
        this.host = host;
        this.serviceType = serviceType;
    }

    @Override
    public EventType type() {
        return EventType.SYSTEM;
    }

    public InetAddress host() {
        return host;
    }

    public ServiceType serviceType() {
        return serviceType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ServiceConnectedEvent{");
        sb.append("host=").append(host);
        sb.append("service=").append(this.serviceType.toString());
        sb.append('}');
        return sb.toString();
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> result = Events.identityMap(this);
        result.put("host", host().toString());
        result.put("service", serviceType().toString());
        return result;
    }
}
