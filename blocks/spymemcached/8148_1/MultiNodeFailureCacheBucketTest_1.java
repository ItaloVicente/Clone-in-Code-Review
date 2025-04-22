package net.spy.memcached;

import org.couchbase.mock.CouchbaseMock;

public class MultiNodeFailureBaseBucketTest extends AbstractMultiNodeFailure {
    @Override
    protected CouchbaseMock.BucketType getBucketType() {
        return CouchbaseMock.BucketType.BASE;
    }
}
