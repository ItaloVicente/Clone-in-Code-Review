package com.couchbase.client.core.endpoint;

public enum ServerFeatures {

    DATATYPE((short) 0x01),


    TCPNODELAY((short) 0x03),

    MUTATION_SEQNO((short) 0x04),

    TCPDELAY((short) 0x05);

    private final short value;

    ServerFeatures(short value) {
        this.value = value;
    }

    public short value() {
        return value;
    }

    public static ServerFeatures fromValue(short input) {
        switch(input) {
            case 0x01: return DATATYPE;
            case 0x03: return TCPNODELAY;
            case 0x04: return MUTATION_SEQNO;
            case 0x05: return TCPDELAY;
            default: throw new IllegalStateException("Unrequested server feature: " + input);
        }
    }
}
