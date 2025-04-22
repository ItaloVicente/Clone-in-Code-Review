package net.spy.memcached.internal;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public interface BulkFuture<V> extends Future<V> {

	public boolean isTimeout();

	public V getSome(long timeout, TimeUnit unit)
			throws InterruptedException, ExecutionException;

}
