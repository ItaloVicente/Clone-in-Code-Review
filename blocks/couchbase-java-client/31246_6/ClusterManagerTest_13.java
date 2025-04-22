
package com.couchbase.client;

import java.net.URI;
import java.util.List;

public class ClusterManagerBuilder {

  private int connectionTimeout = ClusterManager.DEFAULT_CONN_TIMEOUT;
  private int socketTimeout = ClusterManager.DEFAULT_SOCKET_TIMEOUT;
  private boolean tcpNoDelay = ClusterManager.DEFAULT_TCP_NODELAY;
  private int ioThreadCount = ClusterManager.DEFAULT_IO_THREADS;
  private int connectionsPerNode = ClusterManager.DEFAULT_CONNS_PER_NODE;

  public int getConnectionTimeout() {
    return connectionTimeout;
  }

  public int getSocketTimeout() {
    return socketTimeout;
  }

  public boolean getTcpNoDelay() {
    return tcpNoDelay;
  }

  public int getIoThreadCount() {
    return ioThreadCount;
  }

  public int getConnectionsPerNode() {
    return connectionsPerNode;
  }

  public ClusterManagerBuilder setConnectionTimeout(int connectionTimeout) {
    this.connectionTimeout = connectionTimeout;
    return this;
  }

  public ClusterManagerBuilder setSocketTimeout(int socketTimeout) {
    this.socketTimeout = socketTimeout;
    return this;
  }

  public ClusterManagerBuilder setTcpNoDelay(boolean tcpNoDelay) {
    this.tcpNoDelay = tcpNoDelay;
    return this;
  }

  public ClusterManagerBuilder setIoThreadCount(int ioThreadCount) {
    this.ioThreadCount = ioThreadCount;
    return this;
  }

  public ClusterManagerBuilder setConnectionsPerNode(int connectionsPerNode) {
    this.connectionsPerNode = connectionsPerNode;
    return this;
  }

  public ClusterManager build(final List<URI> nodes, final String username,
    final String password) {
    return new ClusterManager(nodes, username, password, connectionTimeout,
      socketTimeout, tcpNoDelay, ioThreadCount, connectionsPerNode);
  }

}
