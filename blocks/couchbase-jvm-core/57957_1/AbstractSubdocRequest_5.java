
package com.couchbase.client.core.message.kv.subdoc;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.message.CouchbaseResponse;
import io.netty.buffer.ByteBuf;
import rx.subjects.AsyncSubject;
import rx.subjects.Subject;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public abstract class AbstractSubdocMutationRequest extends AbstractSubdocRequest implements BinarySubdocMutationRequest {

    private final int expiration;

    private final int flags;

    private final ByteBuf fragment;

    private boolean createIntermediaryPath;

    private long cas;

    protected AbstractSubdocMutationRequest(String key, String path, String bucket,
                                         int expiration, ByteBuf fragment, int flags, long cas) {
        this(key, path, bucket, AsyncSubject.<CouchbaseResponse>create(), expiration, fragment, flags, cas);
    }

    protected AbstractSubdocMutationRequest(String key, String path, String bucket, Subject<CouchbaseResponse, CouchbaseResponse> observable,
                                            int expiration, ByteBuf fragment, int flags, long cas) {
        super(key, path, bucket, observable, fragment);
        this.expiration = expiration;
        this.fragment = fragment;
        this.createIntermediaryPath = false;
        this.flags = flags;
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
    public int flags() {
        return flags;
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
