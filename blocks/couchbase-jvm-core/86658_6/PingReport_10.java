
package com.couchbase.client.core.message.internal;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.couchbase.client.core.message.internal.DiagnosticsReport.VERSION;
import static com.couchbase.client.core.message.internal.DiagnosticsReport.serviceTypeFromEnum;

@InterfaceAudience.Public
@InterfaceStability.Experimental
public class PingReport {

    private final List<PingServiceHealth> services;
    private final String sdk;
    private final String id;
    private final int version;
    private final long configRev;

    public PingReport(List<PingServiceHealth> services, String sdk, String id, long configRev) {
        this.services = services;
        this.version = VERSION;
        this.sdk = sdk;
        this.configRev = configRev;
        this.id = id == null ? UUID.randomUUID().toString() : id;
    }

    public List<PingServiceHealth> services() {
        return services;
    }

    public String exportToJson() {
        return exportToJson(false);
    }

    public String exportToJson(boolean pretty) {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, List<Map<String, Object>>> services = new HashMap<String, List<Map<String, Object>>>();

        for (PingServiceHealth h : this.services) {
            String type = serviceTypeFromEnum(h.type());
            if (!services.containsKey(type)) {
                services.put(type, new ArrayList<Map<String, Object>>());
            }
            List<Map<String, Object>> eps = services.get(type);
            eps.add(h.toMap());
        }

        result.put("config_rev", configRev);
        result.put("version", version);
        result.put("services", services);
        result.put("sdk", sdk);
        result.put("id", id);

        try {
            if (pretty) {
                return DiagnosticsReport.JACKSON.writerWithDefaultPrettyPrinter().writeValueAsString(result);
            } else {
                return DiagnosticsReport.JACKSON.writeValueAsString(result);
            }
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Could not encode as JSON string.", e);
        }
    }

    public String sdk() {
        return sdk;
    }

    public String id() {
        return id;
    }

    public int version() {
        return version;
    }

    public long configRev() {
        return configRev;
    }

    @Override
    public String toString() {
        return "PingReport{" +
            "services=" + services +
            '}';
    }
}
