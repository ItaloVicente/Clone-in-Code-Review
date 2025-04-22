
package com.couchbase.client.core.message.view;

import com.couchbase.client.core.message.AbstractCouchbaseRequest;
import com.couchbase.client.core.message.DiagnosticRequest;
import com.couchbase.client.core.message.PrelocatedRequest;
import com.couchbase.client.core.message.query.QueryRequest;

import java.net.InetAddress;
import java.net.SocketAddress;

public class PingRequest
    extends AbstractCouchbaseRequest
    implements ViewRequest, PrelocatedRequest, DiagnosticRequest {

    private final InetAddress sendTo;
    private volatile SocketAddress local;
    private volatile SocketAddress remote;

    public PingRequest(InetAddress sendTo, String bucket, String password) {
        super(bucket, password);
        this.sendTo = sendTo;
    }

    @Override
    public SocketAddress localSocket() {
        return local;
    }

    @Override
    public PingRequest localSocket(SocketAddress socket) {
        this.local = socket;
        return this;
    }

    @Override
    public SocketAddress remoteSocket() {
        return remote;
    }

    @Override
    public PingRequest remoteSocket(SocketAddress socket) {
        this.remote = socket;
        return this;
    }

    @Override
    public InetAddress sendTo() {
        return sendTo;
    }
}
