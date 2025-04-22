
package org.eclipse.jgit.util.io;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.jgit.internal.JGitText;

public class IsolatedOutputStream extends OutputStream {
	private final OutputStream dst;
	private final ExecutorService copier;

	public IsolatedOutputStream(OutputStream out) {
		dst = out;
		copier = new ThreadPoolExecutor(1
				new SynchronousQueue<Runnable>()
	}

	@Override
	public void write(int ch) throws IOException {
		write(new byte[] { (byte) ch }
	}

	@Override
	public void write(final byte[] buf
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
				execute(new Callable<Void>() {
					@Override
					public Void call() throws IOException {
						dst.close();
						return null;
					}
				});
			} finally {
				copier.shutdown();
			}
		}
	}

	private void checkClosed() throws IOException {
		if (copier.isShutdown()) {
			throw new IOException(JGitText.get().closed);
		}
	}

	private void execute(Callable<Void> task) throws IOException {
		try {
			copier.submit(task).get();
		} catch (InterruptedException e) {
			throw interrupted(e);
		} catch (RejectedExecutionException e) {
			throw new IOException(e);
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
