
package com.couchbase.client.internal;

import net.spy.memcached.internal.GenericCompletionListener;

public interface ReplicaGetCompletionListener
  extends GenericCompletionListener<ReplicaGetFuture<?>> {
}
