
package com.couchbase.client.core.message.dcp;

public class BufferAcknowledgmentRequest extends AbstractDCPRequest {
    private final int bufferBytes;

    public BufferAcknowledgmentRequest(final int bufferBytes, final String bucket) {
        super(bucket, null);
        this.bufferBytes = bufferBytes;
    }

    public BufferAcknowledgmentRequest(final int bufferBytes, final String bucket, final String password) {
        super(bucket, password);
        this.bufferBytes = bufferBytes;
    }

    public int bufferBytes() {
        return bufferBytes;
    }
}
