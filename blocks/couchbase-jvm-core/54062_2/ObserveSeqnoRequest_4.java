package com.couchbase.client.core.message.kv;

import com.couchbase.client.core.message.CouchbaseRequest;
import com.couchbase.client.core.message.ResponseStatus;

public class NoFailoverObserveSeqnoResponse extends AbstractKeyValueResponse {

    private final boolean master;
    private final short vbucketID;
    private final long vbucketUUID;
    private final long lastPersistedSeqNo;
    private final long currentSeqNo;

    public NoFailoverObserveSeqnoResponse(boolean master, short vbucketID, long vbucketUUID, long lastPersistedSeqNo,
        long currentSeqNo, ResponseStatus status, short serverStatusCode, String bucket, CouchbaseRequest request) {
        super(status, serverStatusCode, bucket, null, request);

        this.master = master;
        this.vbucketID = vbucketID;
        this.vbucketUUID = vbucketUUID;
        this.lastPersistedSeqNo = lastPersistedSeqNo;
        this.currentSeqNo = currentSeqNo;
    }

    public boolean master() {
        return master;
    }

    public short vbucketID() {
        return vbucketID;
    }

    public long vbucketUUID() {
        return vbucketUUID;
    }

    public long lastPersistedSeqNo() {
        return lastPersistedSeqNo;
    }

    public long currentSeqNo() {
        return currentSeqNo;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NoFailoverObserveSeqnoResponse{");
        sb.append("master=").append(master);
        sb.append(", vbucketID=").append(vbucketID);
        sb.append(", vbucketUUID=").append(vbucketUUID);
        sb.append(", lastPersistedSeqNo=").append(lastPersistedSeqNo);
        sb.append(", currentSeqNo=").append(currentSeqNo);
        sb.append('}');
        return sb.toString();
    }
}
