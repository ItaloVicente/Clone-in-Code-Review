
package com.couchbase.client.internal;

import net.spy.memcached.internal.OperationFuture;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

public class ObserveFuture<T> extends OperationFuture<T> {

  private volatile boolean cancelled;
  private volatile boolean done;

  public ObserveFuture(final String k, final CountDownLatch l,
    final long opTimeout, final ExecutorService service) {
    super(k, l, opTimeout, service);

    cancelled = false;
    done = false;
  }

  @Override
  public boolean cancel() {
    cancelled = true;
    done = true;
    notifyListeners();
    return true;
  }

  @Override
  public boolean cancel(boolean ign) {
    return cancel();
  }

  @Override
  public boolean isCancelled() {
    return cancelled;
  }

  @Override
  public boolean isDone() {
    return done;
  }
}
