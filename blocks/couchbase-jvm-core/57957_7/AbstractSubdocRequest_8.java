
package com.couchbase.client.core.message.kv.subdoc.simple;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.message.CouchbaseResponse;
import com.couchbase.client.core.message.kv.subdoc.BinarySubdocMutationRequest;
import io.netty.buffer.ByteBuf;
import rx.subjects.AsyncSubject;
import rx.subjects.Subject;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public abstract class AbstractSubdocMutationRequest extends AbstractSubdocRequest implements BinarySubdocMutationRequest {

    private final int expiration;

    private final ByteBuf fragment;

    private boolean createIntermediaryPath;

    private long cas;

    protected AbstractSubdocMutationRequest(String key, String path, ByteBuf fragment, String bucket,
                                         int expiration, long cas) {
        this(key, path, fragment, bucket, expiration, cas, AsyncSubject.<CouchbaseResponse>create());
    }

    protected AbstractSubdocMutationRequest(String key, String path, ByteBuf fragment, String bucket,
                                            int expiration, long cas, Subject<CouchbaseResponse, CouchbaseResponse> observable) {
        super(key, path, bucket, observable, fragment);
        this.expiration = expiration;
        this.fragment = fragment;
        this.createIntermediaryPath = false;
        this.cas = cas;
    }

    @Override
    public int expiration() {
        return expiration;
    }

    @Override
    public ByteBuf fragment() {
        return fragment;
    }

    @Override
    public boolean createIntermediaryPath() {
        return this.createIntermediaryPath;
    }

    public void createIntermediaryPath(boolean createIntermediaryPath) {
        this.createIntermediaryPath = createIntermediaryPath;
    }

    @Override
    public long cas() {
        return this.cas;
    }
}
