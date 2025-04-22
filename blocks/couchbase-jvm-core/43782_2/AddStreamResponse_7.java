
package com.couchbase.client.core.message.dcp;

public class AddStreamRequest extends AbstractDCPRequest {
    private final boolean takeOver;
    private final boolean diskOnly;

    public AddStreamRequest(short partition, String bucket) {
        this(partition, false, false, bucket, null);
    }

    public AddStreamRequest(short partition, String bucket, String password) {
        this(partition, false, false, bucket, password);
    }

    public AddStreamRequest(short partition, boolean takeOver, boolean diskOnly, String bucket, String password) {
        super(bucket, password);
        this.takeOver = takeOver;
        this.diskOnly = diskOnly;
        this.partition(partition);
    }

    public boolean diskOnly() {
        return diskOnly;
    }

    public boolean takeOver() {
        return takeOver;
    }
}
