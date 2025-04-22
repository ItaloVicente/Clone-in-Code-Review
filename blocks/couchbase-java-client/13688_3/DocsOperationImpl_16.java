
package com.couchbase.client.internal;

import com.couchbase.client.protocol.views.ViewResponse;
import com.couchbase.client.protocol.views.ViewResponseWithDocs;
import com.couchbase.client.protocol.views.ViewRow;
import com.couchbase.client.protocol.views.ViewRowWithDocs;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

import net.spy.memcached.OperationTimeoutException;
import net.spy.memcached.internal.BulkFuture;
import net.spy.memcached.ops.OperationStatus;

public class ViewFuture extends HttpFuture<ViewResponse> {
  private final AtomicReference<BulkFuture<Map<String, Object>>> multigetRef;

  public ViewFuture(CountDownLatch latch, long timeout) {
    super(latch, timeout);
    this.multigetRef =
        new AtomicReference<BulkFuture<Map<String, Object>>>(null);
  }

  @Override
  public ViewResponse get(long duration, TimeUnit units)
    throws InterruptedException, ExecutionException, TimeoutException {

    if (!latch.await(duration, units)) {
      if (op != null) {
        op.timeOut();
      }
      status = new OperationStatus(false, "Timed out");
      throw new TimeoutException("Timed out waiting for operation");
    }

    if (op != null && op.hasErrored()) {
      status = new OperationStatus(false, op.getException().getMessage());
      throw new ExecutionException(op.getException());
    }

    if (op.isCancelled()) {
      status = new OperationStatus(false, "Operation Cancelled");
      throw new ExecutionException(new RuntimeException("Cancelled"));
    }

    if (op != null && op.isTimedOut()) {
      status = new OperationStatus(false, "Timed out");
      throw new ExecutionException(new OperationTimeoutException(
          "Operation timed out."));
    }

    if (multigetRef.get() == null) {
      return null;
    }

    Map<String, Object> docMap = multigetRef.get().get();
    final ViewResponseWithDocs view = (ViewResponseWithDocs) objRef.get();
    Collection<ViewRow> rows = new LinkedList<ViewRow>();
    Iterator<ViewRow> itr = view.iterator();

    while (itr.hasNext()) {
      ViewRow r = itr.next();
      rows.add(new ViewRowWithDocs(r.getId(), r.getKey(), r.getValue(),
          docMap.get(r.getId())));
    }
    return new ViewResponseWithDocs(rows, view.getErrors());
  }

  public void set(ViewResponse viewResponse,
      BulkFuture<Map<String, Object>> oper, OperationStatus s) {
    objRef.set(viewResponse);
    multigetRef.set(oper);
    status = s;
  }
}
