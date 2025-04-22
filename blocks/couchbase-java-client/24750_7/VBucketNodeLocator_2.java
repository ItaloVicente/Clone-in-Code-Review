
package com.couchbase.client.internal;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import net.spy.memcached.internal.GetFuture;

public class ReplicaGetFuture<T extends Object> implements Future<T> {

  final long timeout;
  final List<GetFuture<T>> futures;
  boolean cancelled = false;

  public ReplicaGetFuture(long timeout, List<GetFuture<T>> futures) {
    this.timeout = timeout;
    this.futures = futures;
  }

  @Override
  public T get() throws InterruptedException, ExecutionException {
    try {
      return get(timeout, TimeUnit.MILLISECONDS);
    } catch (TimeoutException e) {
      throw new RuntimeException("Timed out waiting for operation", e);
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public T get(long timeout, TimeUnit unit) throws InterruptedException,
    ExecutionException, TimeoutException {
    long start = System.currentTimeMillis();
    long timeoutMs =TimeUnit.MILLISECONDS.convert(timeout, unit);

    while(System.currentTimeMillis() - start <= timeoutMs) {
      for(GetFuture future: futures) {
        if(future.isDone() && !future.isCancelled()) {
          cancelOtherFutures(future);
          return (T) future.get();
        }
      }
    }

    throw new TimeoutException("No replica get future returned with success "
      + "before timeout.");
  }

  public void cancelOtherFutures(GetFuture successFuture) {
    for(GetFuture future : futures) {
      if(!future.equals(successFuture)) {
        future.cancel(true);
      }
    }
  }

  @Override
  public boolean cancel(boolean ign) {
    cancelled = true;
    boolean allCancelled = true;
    for(GetFuture future : futures) {
      if(!future.cancel(ign)) {
        allCancelled = false;
      }
    }
    return allCancelled;
  }

  @Override
  public boolean isCancelled() {
    return cancelled;
  }

  @Override
  public boolean isDone() {
    boolean allDone = true;
    for(GetFuture future : futures) {
      if(!future.isDone()) {
        allDone = false;
      }
    }
    return allDone;
  }

}
