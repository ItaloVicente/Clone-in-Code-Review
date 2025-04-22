
package org.eclipse.jgit.util.io;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.jgit.internal.JGitText;

public class IsolatedOutputStream extends OutputStream {
	private final OutputStream dst;
	private final ExecutorService copier;
	private Future<Void> pending;

	public IsolatedOutputStream(OutputStream out) {
		dst = out;
		copier = new ThreadPoolExecutor(1
				new ArrayBlockingQueue<Runnable>(1)
	}

	@Override
	public void write(int ch) throws IOException {
		write(new byte[] { (byte) ch }
	}

	@Override
	public void write(byte[] buf
			throws IOException {
		checkClosed();
		execute(new Callable<Void>() {
			@Override
			public Void call() throws IOException {
				dst.write(buf
				return null;
			}
		});
	}

	@Override
	public void flush() throws IOException {
		checkClosed();
		execute(new Callable<Void>() {
			@Override
			public Void call() throws IOException {
				dst.flush();
				return null;
			}
		});
	}

	@Override
	public void close() throws IOException {
		if (!copier.isShutdown()) {
			try {
				if (pending == null || tryCleanClose()) {
					cleanClose();
				} else {
					dirtyClose();
				}
			} finally {
				copier.shutdown();
			}
		}
	}

	private boolean tryCleanClose() {
		try {
			pending.get(0
			pending = null;
			return true;
		} catch (TimeoutException | InterruptedException e) {
			return false;
		} catch (ExecutionException e) {
			pending = null;
			return true;
		}
	}

	private void cleanClose() throws IOException {
		execute(new Callable<Void>() {
			@Override
			public Void call() throws IOException {
				dst.close();
				return null;
			}
		});
	}

	private void dirtyClose() throws IOException {
		pending.cancel(true);

		Future<Void> close;
		try {
			close = copier.submit(new Callable<Void>() {
				@Override
				public Void call() throws IOException {
					dst.close();
					return null;
				}
			});
		} catch (RejectedExecutionException e) {
			throw new IOException(e);
		}
		try {
			close.get(200
		} catch (InterruptedException | TimeoutException e) {
			close.cancel(true);
			throw new IOException(e);
		} catch (ExecutionException e) {
			throw new IOException(e.getCause());
		}
	}

	private void checkClosed() throws IOException {
		if (copier.isShutdown()) {
			throw new IOException(JGitText.get().closed);
		}
	}

	private void execute(Callable<Void> task) throws IOException {
		if (pending != null) {
			checkedGet(pending);
		}
		try {
			pending = copier.submit(task);
		} catch (RejectedExecutionException e) {
			throw new IOException(e);
		}
		checkedGet(pending);
		pending = null;
	}

	private static void checkedGet(Future<Void> future) throws IOException {
		try {
			future.get();
		} catch (InterruptedException e) {
			throw interrupted(e);
		} catch (ExecutionException e) {
			throw new IOException(e.getCause());
		}
	}

	private static InterruptedIOException interrupted(InterruptedException c) {
		InterruptedIOException e = new InterruptedIOException();
		e.initCause(c);
		return e;
	}

	private static class NamedThreadFactory implements ThreadFactory {
		private static final AtomicInteger cnt = new AtomicInteger();

		@Override
		public Thread newThread(Runnable r) {
			int n = cnt.incrementAndGet();
			String name = IsolatedOutputStream.class.getSimpleName() + '-' + n;
			return new Thread(r
		}
	}
}
