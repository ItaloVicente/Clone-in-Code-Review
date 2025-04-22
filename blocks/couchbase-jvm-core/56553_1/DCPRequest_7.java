
package com.couchbase.client.core.message.dcp;

public enum ControlParameter {
    ENABLE_NOOP("enable_noop"),
    CONNECTION_BUFFER_SIZE("connection_buffer_size"),
    SET_NOOP_INTERVAL("set_noop_interval"),
    SET_PRIORITY("set_priority"),
    ENABLE_EXT_METADATA("enable_ext_metadata"),
    ENABLE_VALUE_COMPRESSION("enable_value_compression"),
    SUPPORTS_CURSOR_DROPPING("supports_cursor_dropping");

    private final String value;

    ControlParameter(final String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
