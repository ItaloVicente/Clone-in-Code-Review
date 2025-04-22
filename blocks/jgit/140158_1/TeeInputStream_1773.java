
package org.eclipse.jgit.util.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;

public class StreamCopyThread extends Thread {
	private static final int BUFFER_SIZE = 1024;

	private final InputStream src;

	private final OutputStream dst;

	private volatile boolean done;

	private final Object writeLock;

	public StreamCopyThread(InputStream i
		src = i;
		dst = o;
		writeLock = new Object();
	}

	public void halt() throws InterruptedException {
		for (;;) {
			if (isAlive()) {
				done = true;
				interrupt();
			} else
				break;
		}
	}

	@Override
	public void run() {
		try {
			final byte[] buf = new byte[BUFFER_SIZE];
			boolean readInterrupted = false;
			for (;;) {
				try {
					if (readInterrupted) {
						synchronized (writeLock) {
							boolean interruptedAgain = Thread.interrupted();
							dst.flush();
							if (interruptedAgain) {
								interrupt();
							}
						}
						readInterrupted = false;
					}

					if (done)
						break;

					final int n;
					try {
						n = src.read(buf);
					} catch (InterruptedIOException wakey) {
						readInterrupted = true;
						continue;
					}
					if (n < 0)
						break;

					synchronized (writeLock) {
						boolean writeInterrupted = Thread.interrupted();
						dst.write(buf
						if (writeInterrupted) {
							interrupt();
						}
					}
				} catch (IOException e) {
					break;
				}
			}
		} finally {
			try {
				src.close();
			} catch (IOException e) {
			}
			try {
				dst.close();
			} catch (IOException e) {
			}
		}
	}
}
