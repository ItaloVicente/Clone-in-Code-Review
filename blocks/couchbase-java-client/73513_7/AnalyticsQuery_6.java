package com.couchbase.client.java.analytics;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.document.json.JsonValue;
import com.couchbase.client.java.query.N1qlParams;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@InterfaceStability.Uncommitted
@InterfaceAudience.Public
public class AnalyticsParams implements Serializable {

    private static final long serialVersionUID = 8888370260267213836L;

    private String clientContextId;
    private Map<String, Object> rawParams;

    private AnalyticsParams() { }

    public void injectParams(JsonObject queryJson) {
        if (this.clientContextId != null) {
            queryJson.put("client_context_id", this.clientContextId);
        }

        queryJson.put("pretty", false);

        if (this.rawParams != null) {
            for (Map.Entry<String, Object> entry : rawParams.entrySet()) {
                queryJson.put(entry.getKey(), entry.getValue());
            }
        }
    }

    public AnalyticsParams withContextId(String clientContextId) {
        this.clientContextId = clientContextId;
        return this;
    }

    @InterfaceStability.Uncommitted
    public AnalyticsParams rawParam(String name, Object value) {
        if (this.rawParams == null) {
            this.rawParams = new HashMap<String, Object>();
        }

        if (!JsonValue.checkType(value)) {
            throw new IllegalArgumentException("Only JSON types are supported.");
        }

        rawParams.put(name, value);
        return this;
    }

    public static AnalyticsParams build() {
        return new AnalyticsParams();
    }

}
