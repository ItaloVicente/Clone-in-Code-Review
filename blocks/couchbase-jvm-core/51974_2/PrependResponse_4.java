package com.couchbase.client.core.message.kv;

public class MutationDescriptor {

    private final long vbucketUUID;
    private final long seqNo;

    public MutationDescriptor(long vbucketUUID, long seqNo) {
        this.vbucketUUID = vbucketUUID;
        this.seqNo = seqNo;
    }

    public long vbucketUUID() {
        return vbucketUUID;
    }

    public long seqNo() {
        return seqNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MutationDescriptor that = (MutationDescriptor) o;

        if (vbucketUUID != that.vbucketUUID) return false;
        return seqNo == that.seqNo;

    }

    @Override
    public int hashCode() {
        int result = (int) (vbucketUUID ^ (vbucketUUID >>> 32));
        result = 31 * result + (int) (seqNo ^ (seqNo >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("md{");
        sb.append("vbuuid=").append(vbucketUUID);
        sb.append(", seqno=").append(seqNo);
        sb.append('}');
        return sb.toString();
    }
}
