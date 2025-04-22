package com.couchbase.client.core.endpoint.binary;

public class SupportedDatatypes {

    private final boolean json;

    private final boolean compression;

    public SupportedDatatypes(final boolean json, final boolean compression) {
        this.json = json;
        this.compression = compression;
    }

    public boolean json() {
        return json;
    }

    public boolean compression() {
        return compression;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SupportedDatatypes{");
        sb.append("json=").append(json);
        sb.append(", compression=").append(compression);
        sb.append('}');
        return sb.toString();
    }
}
