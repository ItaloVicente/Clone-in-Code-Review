
package com.couchbase.client.core.message.kv;

import com.couchbase.client.core.message.DiagnosticRequest;
import com.couchbase.client.core.utils.NetworkAddress;

import java.net.SocketAddress;

public class NoopRequest extends AbstractKeyValueRequest implements DiagnosticRequest {

    private volatile SocketAddress local;
    private volatile SocketAddress remote;
    private final NetworkAddress hostname;

    public NoopRequest(String bucket, NetworkAddress hostname) {
        super("", bucket);
        this.hostname = hostname;
        partition((short) 0);
    }

    public NetworkAddress hostname() {
        return hostname;
    }

    @Override
    public SocketAddress localSocket() {
        return local;
    }

    @Override
    public NoopRequest localSocket(SocketAddress local) {
        this.local = local;
        return this;
    }

    @Override
    public SocketAddress remoteSocket() {
        return remote;
    }

    @Override
    public NoopRequest remoteSocket(SocketAddress remote) {
        this.remote = remote;
        return this;
    }
}
