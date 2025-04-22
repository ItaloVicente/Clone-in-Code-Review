
package org.eclipse.jgit.storage.dht;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public abstract class Sync<T> implements AsyncCallback<T> {
	private static final Sync<?> NONE = new Sync<Object>() {
		public void onSuccess(Object result) {
		}

		public void onFailure(DhtException error) {
		}

		@Override
		public Object get(long timeout
				InterruptedException
			return null;
		}
	};

	public static <T> Sync<T> create() {
		return new Value<T>();
	}

	@SuppressWarnings("unchecked")
	public static <T> Sync<T> none() {
		return (Sync<T>) NONE;
	}

	public T get(Timeout timeout) throws DhtException
			TimeoutException {
		return get(timeout.getTime()
	}

	public abstract T get(long timeout
			InterruptedException

	private static class Value<T> extends Sync<T> {

		private final CountDownLatch wait = new CountDownLatch(1);

		private T data;

		private DhtException error;

		public T get(long timeout
				InterruptedException
			if (wait.await(timeout
				if (error != null)
					throw error;
				return data;
			}
			throw new TimeoutException();
		}

		public void onSuccess(T obj) {
			data = obj;
			wait.countDown();
		}

		public void onFailure(DhtException err) {
			error = err;
			wait.countDown();
		}
	}
}
