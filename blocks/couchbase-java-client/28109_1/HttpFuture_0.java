
package com.couchbase.client.internal;

import java.util.concurrent.Future;
import net.spy.memcached.internal.GenericCompletionListener;

public interface HttpCompletionListener
  extends GenericCompletionListener<HttpFuture<?>> {
}
