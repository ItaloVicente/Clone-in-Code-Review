
package org.eclipse.jgit.util.io;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class IsolatedOutputStream extends OutputStream {
	private final Copier copier;
	private final ReentrantLock lock;
	private final Condition writeReady;

	private final Condition writeDone;

	private final byte[] buffer = new byte[4096];
	private int len;
	private boolean flush;
	private boolean close;
	private IOException err;

	public IsolatedOutputStream(OutputStream dst) {
		lock = new ReentrantLock();
		writeReady = lock.newCondition();
		writeDone = lock.newCondition();

		copier = new Copier(dst);
		copier.start();
	}

	@Override
	public void write(int ch) throws IOException {
		try {
			lock.lockInterruptibly();
			try {
				checkError();
				writeIfFull();
				buffer[len++] = (byte) ch;
			} finally {
				lock.unlock();
			}
		} catch (InterruptedException e) {
			throw new InterruptedIOException();
		}
	}

	@Override
	public void write(byte[] d
		try {
			lock.lockInterruptibly();
			try {
				checkError();
				while (n > 0) {
					writeIfFull();
					int c = Math.min(n
					System.arraycopy(d
					len += c;
					o += c;
					n -= c;
				}
			} finally {
				lock.unlock();
			}
		} catch (InterruptedException e) {
			throw new InterruptedIOException();
		}
	}

	@Override
	public void flush() throws IOException {
		try {
			lock.lockInterruptibly();
			try {
				checkError();
				flush = true;
				writeReady.signal();
				writeDone.await();
				checkError();
			} finally {
				lock.unlock();
			}
		} catch (InterruptedException e) {
			throw new InterruptedIOException();
		}
	}

	@Override
	public void close() throws IOException {
		try {
			lock.lockInterruptibly();
			try {
				if (!close) {
					checkError();
					close = true;
					writeReady.signal();
					writeDone.await();
					checkError();
				}
			} finally {
				lock.unlock();
			}
			copier.join();
		} catch (InterruptedException e) {
			throw new InterruptedIOException();
		}
	}

	private void writeIfFull() throws InterruptedException
		if (len == buffer.length) {
			writeReady.signal();
			writeDone.await();
			checkError();
		}
	}

	private void checkError() throws IOException {
		if (err != null) {
			throw err;
		}
	}

	private class Copier extends Thread {
		private final OutputStream dst;

		Copier(OutputStream dst) {
			this.dst = dst;

			String outer = IsolatedOutputStream.class.getSimpleName();
		}

		@Override
		public void run() {
			for (;;) {
				lock.lock();
				try {
					writeReady.awaitUninterruptibly();
					try {
						if (len > 0) {
							dst.write(buffer
							len = 0;
						}
						if (flush) {
							dst.flush();
							flush = false;
						}
						if (close) {
							dst.close();
							break;
						}
					} catch (IOException e) {
						err = e;
						break;
					}
				} finally {
					writeDone.signal();
					lock.unlock();
				}
			}
		}
	}
}
