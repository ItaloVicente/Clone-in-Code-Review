
package com.couchbase.client.http;

import org.apache.http.HttpHost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.impl.nio.pool.BasicNIOConnPool;
import org.apache.http.nio.NHttpClientConnection;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.pool.PoolEntry;
import org.apache.http.pool.PoolEntryCallback;

import java.util.concurrent.TimeUnit;

public class ViewPool extends BasicNIOConnPool {

  public ViewPool(final ConnectingIOReactor reactor,
    final ConnectionConfig config) {
    super(reactor, config);
  }

  public void closeConnectionsForHost(final HttpHost host) {
    enumAvailable(new PoolEntryCallback<HttpHost, NHttpClientConnection>() {
      @Override
      public void process(PoolEntry<HttpHost, NHttpClientConnection> entry) {
        if (entry.getRoute().equals(host)) {
          entry.updateExpiry(0, TimeUnit.MILLISECONDS);
        }
      }
    });

    enumLeased(new PoolEntryCallback<HttpHost, NHttpClientConnection>() {
      @Override
      public void process(PoolEntry<HttpHost, NHttpClientConnection> entry) {
        if (entry.getRoute().equals(host)) {
          entry.updateExpiry(0, TimeUnit.MILLISECONDS);
        }
      }
    });

    closeExpired();
  }

}
