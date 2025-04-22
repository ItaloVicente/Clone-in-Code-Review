
package org.eclipse.jgit.storage.dfs;

import java.io.EOFException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.jgit.util.IO;

final class ReadAheadTask implements Callable<Void> {
	private final DfsBlockCache cache;

	private final ReadableChannel channel;

	private final List<BlockFuture> futures;

	private boolean running;

	ReadAheadTask(DfsBlockCache cache
			List<BlockFuture> futures) {
		this.cache = cache;
		this.channel = channel;
		this.futures = futures;
	}

	public Void call() {
		int idx = 0;
		try {
			synchronized (this) {
				if (channel.isOpen())
					running = true;
				else
					return null;
			}

			long position = channel.position();
			for (; idx < futures.size() && !Thread.interrupted(); idx++) {
				BlockFuture f = futures.get(idx);
				if (cache.contains(f.pack
					f.done();
					continue;
				}

				if (position != f.start)
					channel.position(f.start);

				int size = (int) (f.end - f.start);
				byte[] buf = new byte[size];
				if (IO.read(channel
					throw new EOFException();

				cache.put(new DfsBlock(f.pack
				f.done();
				position = f.end;
			}
		} catch (IOException err) {
		} finally {
			for (; idx < futures.size(); idx++)
				futures.get(idx).abort();
			close();
		}
		return null;
	}

	void abort() {
		for (BlockFuture f : futures)
			f.abort();

		synchronized (this) {
			if (!running)
				close();
		}
	}

	private synchronized void close() {
		try {
			if (channel.isOpen())
				channel.close();
		} catch (IOException err) {
		}
	}

	static final class TaskFuture extends java.util.concurrent.FutureTask<Void> {
		final ReadAheadTask task;

		TaskFuture(ReadAheadTask task) {
			super(task);
			this.task = task;
		}

		@Override
		public boolean cancel(boolean mayInterruptIfRunning) {
			if (super.cancel(mayInterruptIfRunning)) {
				task.abort();
				return true;
			}
			return false;
		}
	}

	static final class BlockFuture implements Future<Void> {
		private static enum State {
			PENDING
		}

		private volatile State state;

		private volatile Future<?> task;

		private final CountDownLatch latch;

		final DfsPackKey pack;

		final long start;

		final long end;

		BlockFuture(DfsPackKey key
			this.state = State.PENDING;
			this.latch = new CountDownLatch(1);
			this.pack = key;
			this.start = start;
			this.end = end;
		}

		synchronized void setTask(Future<?> task) {
			if (state == State.PENDING)
				this.task = task;
		}

		boolean contains(DfsPackKey want
			return pack == want && start <= pos && pos < end;
		}

		synchronized void done() {
			if (state == State.PENDING) {
				latch.countDown();
				state = State.DONE;
				task = null;
			}
		}

		synchronized void abort() {
			if (state == State.PENDING) {
				latch.countDown();
				state = State.CANCELLED;
				task = null;
			}
		}

		public boolean cancel(boolean mayInterruptIfRunning) {
			Future<?> t = task;
			if (t == null)
				return false;

			boolean r = t.cancel(mayInterruptIfRunning);
			abort();
			return r;
		}

		public Void get() throws InterruptedException
			latch.await();
			return null;
		}

		public Void get(long timeout
				throws InterruptedException
				TimeoutException {
			if (latch.await(timeout
				return null;
			else
				throw new TimeoutException();
		}

		public boolean isCancelled() {
			State s = state;
			if (s == State.DONE)
				return false;
			if (s == State.CANCELLED)
				return true;

			Future<?> t = task;
			return t != null ? t.isCancelled() : true;
		}

		public boolean isDone() {
			return state == State.DONE;
		}
	}
}
