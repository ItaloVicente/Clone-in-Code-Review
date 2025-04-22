
package com.couchbase.client.core.tracing;

import java.util.Map;

public class SlowlogLogData {

    private final long timeMicros;
    private final String message;
    private final Map<String, ?> fields;

    public SlowlogLogData(long timeMicros, String message, Map<String, ?> fields) {
        this.timeMicros = timeMicros;
        this.message = message;
        this.fields = fields;
    }

    public long time() {
        return timeMicros;
    }

    public String message() {
        return message;
    }

    public Map<String, ?> fields() {
        return fields;
    }
}
