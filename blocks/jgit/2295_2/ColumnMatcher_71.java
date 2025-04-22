
package org.eclipse.jgit.storage.dht.spi.tools;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.jgit.storage.dht.AsyncCallback;
import org.eclipse.jgit.storage.dht.DhtException;
import org.eclipse.jgit.storage.dht.DhtTimeoutException;
import org.eclipse.jgit.storage.dht.spi.WriteBuffer;

public abstract class AbstractWriteBuffer implements WriteBuffer {
	private final static int AUTO_FLUSH_SIZE = 512 * 1024;

	private final ExecutorService executor;

	private final int bufferSize;

	private final List<Future<?>> running;

	private final Semaphore spaceAvailable;

	private int queuedCount;

	private boolean flushing;

	private Callable<?> finalTask;

	protected AbstractWriteBuffer(ExecutorService executor
		this.executor = executor;
		this.bufferSize = bufferSize;
		this.running = new LinkedList<Future<?>>();
		this.spaceAvailable = new Semaphore(bufferSize);
	}

	protected boolean add(int size) throws DhtException {
		acquireSpace(size);
		return size < AUTO_FLUSH_SIZE;
	}

	protected void queued(int size) throws DhtException {
		queuedCount += size;

		if (AUTO_FLUSH_SIZE < queuedCount) {
			startQueuedOperations(queuedCount);
			queuedCount = 0;
		}
	}

	protected abstract void startQueuedOperations(int bufferedByteCount)
			throws DhtException;

	public void flush() throws DhtException {
		try {
			flushing = true;

			if (0 < queuedCount) {
				startQueuedOperations(queuedCount);
				queuedCount = 0;
			}


			if (finalTask != null) {
				try {
					waitFor(finalTask);
				} finally {
					finalTask = null;
				}
			}

			checkRunningTasks(true);
		} finally {
			flushing = false;
		}
	}

	private void acquireSpace(int sz) throws DhtException {
		try {
			final int permits = permitsForSize(sz);
			if (spaceAvailable.tryAcquire(permits))
				return;

			if (0 < queuedCount) {
				startQueuedOperations(queuedCount);
				queuedCount = 0;
			}

			spaceAvailable.acquire(permits);
		} catch (InterruptedException e) {
			throw new DhtTimeoutException(e);
		}
	}

	private int permitsForSize(int size) {

		if (size <= 0)
			size = 1;
		return Math.min(size
	}

	protected <T> void start(final Callable<T> task
			throws DhtException {
		final int permits = permitsForSize(size);
		final Callable<T> op = new Callable<T>() {
			public T call() throws Exception {
				try {
					return task.call();
				} finally {
					spaceAvailable.release(permits);
				}
			}
		};

		if (flushing && finalTask == null) {
			finalTask = op;
			return;
		}

		if (!flushing)
			checkRunningTasks(false);
		running.add(executor.submit(op));
	}

	protected <T> AsyncCallback<T> wrap(final AsyncCallback<T> callback
			int size) throws DhtException {
		int permits = permitsForSize(size);
		WrappedCallback<T> op = new WrappedCallback<T>(callback
		checkRunningTasks(false);
		running.add(op);
		return op;
	}

	private void checkRunningTasks(boolean wait) throws DhtException {
		if (running.isEmpty())
			return;

		Iterator<Future<?>> itr = running.iterator();
		while (itr.hasNext()) {
			Future<?> task = itr.next();
			if (task.isDone() || wait) {
				itr.remove();
				waitFor(task);
			}
		}
	}

	private static void waitFor(Callable<?> task) throws DhtException {
		try {
			task.call();
		} catch (DhtException err) {
			throw err;
		} catch (Exception err) {
			throw new DhtException(err);
		}
	}

	private static void waitFor(Future<?> task) throws DhtException {
		try {
			task.get();

		} catch (InterruptedException e) {
			throw new DhtTimeoutException(e);

		} catch (ExecutionException err) {

			Throwable t = err;
			while (t != null) {
				if (t instanceof DhtException)
					throw (DhtException) t;
				t = t.getCause();
			}

			throw new DhtException(err);
		}
	}

	private final class WrappedCallback<T> implements AsyncCallback<T>
			Future<T> {
		private final AsyncCallback<T> callback;

		private final int permits;

		private final CountDownLatch sync;

		private volatile boolean done;

		WrappedCallback(AsyncCallback<T> callback
			this.callback = callback;
			this.permits = permits;
			this.sync = new CountDownLatch(1);
		}

		public void onSuccess(T result) {
			try {
				callback.onSuccess(result);
			} finally {
				done();
			}
		}

		public void onFailure(DhtException error) {
			try {
				callback.onFailure(error);
			} finally {
				done();
			}
		}

		private void done() {
			spaceAvailable.release(permits);
			done = true;
			sync.countDown();
		}

		public boolean cancel(boolean mayInterrupt) {
			return false;
		}

		public T get() throws InterruptedException
			sync.await();
			return null;
		}

		public T get(long time
				ExecutionException
			sync.await(time
			return null;
		}

		public boolean isCancelled() {
			return false;
		}

		public boolean isDone() {
			return done;
		}
	}
}
