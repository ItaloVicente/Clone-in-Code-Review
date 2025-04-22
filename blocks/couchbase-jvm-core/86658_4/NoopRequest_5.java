
package com.couchbase.client.core.message;

import java.net.SocketAddress;

public interface DiagnosticRequest {

    SocketAddress localSocket();
    DiagnosticRequest localSocket(SocketAddress socket);

    SocketAddress remoteSocket();
    DiagnosticRequest remoteSocket(SocketAddress socket);
}
