package com.couchbase.client.core.message.binary;

import com.couchbase.client.core.message.CouchbaseRequest;
import com.couchbase.client.core.message.ResponseStatus;
import io.netty.buffer.ByteBuf;

public class ObserveResponse extends AbstractBinaryResponse {

    private final ObserveStatus observeStatus;
    private final boolean master;

    public ObserveResponse(ResponseStatus status, byte obs, boolean master, String bucket, ByteBuf content, CouchbaseRequest request) {
        super(status, bucket, content, request);
        observeStatus = ObserveStatus.valueOf(obs);
        this.master = master;
    }

    public ObserveStatus observeStatus() {
        return observeStatus;
    }

    public boolean master() {
        return master;
    }

    public static enum ObserveStatus {
        UNINITIALIZED((byte) 0xff),
        MODIFIED((byte) 0xfe),
        FOUND_PERSISTED((byte) 0x01),
        FOUND_NOT_PERSISTED((byte) 0x00),
        NOT_FOUND_PERSISTED((byte) 0x80),
        NOT_FOUND_NOT_PERSISTED((byte) 0x81);

        private final byte value;

        ObserveStatus(byte b) {
            value = b;
        }

        public static ObserveStatus valueOf(byte b) {
            switch (b) {
                case (byte) 0x00:
                    return ObserveStatus.FOUND_NOT_PERSISTED;
                case (byte) 0x01:
                    return ObserveStatus.FOUND_PERSISTED;
                case (byte) 0x80:
                    return ObserveStatus.NOT_FOUND_PERSISTED;
                case (byte) 0x81:
                    return ObserveStatus.NOT_FOUND_NOT_PERSISTED;
                case (byte) 0xfe:
                    return ObserveStatus.MODIFIED;
                default:
                    return ObserveStatus.UNINITIALIZED;
            }
        }

    }



}
