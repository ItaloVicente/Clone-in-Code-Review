
package org.eclipse.jgit.storage.dht;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Sync<T> implements AsyncCallback<T> {
	public static <T> Sync<T> create() {
		return new Sync<T>();
	}

	private final CountDownLatch wait = new CountDownLatch(1);

	private T data;

	private DhtException error;

	public T get(Timeout timeout) throws DhtException
			TimeoutException {
		return get(timeout.getTime()
	}

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
