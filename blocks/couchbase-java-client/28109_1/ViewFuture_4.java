
package com.couchbase.client.internal;

import net.spy.memcached.internal.GenericCompletionListener;

public interface ViewCompletionListener
  extends GenericCompletionListener<ViewFuture> {
}
