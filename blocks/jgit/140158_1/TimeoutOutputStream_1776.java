
package org.eclipse.jgit.util.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;

public class TimeoutInputStream extends FilterInputStream {
	private final InterruptTimer myTimer;

	private int timeout;

	public TimeoutInputStream(final InputStream src
			final InterruptTimer timer) {
		super(src);
		myTimer = timer;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int millis) {
		if (millis < 0)
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().invalidTimeout
		timeout = millis;
	}

	@Override
	public int read() throws IOException {
		try {
			beginRead();
			return super.read();
		} catch (InterruptedIOException e) {
			throw readTimedOut();
		} finally {
			endRead();
		}
	}

	@Override
	public int read(byte[] buf) throws IOException {
		return read(buf
	}

	@Override
	public int read(byte[] buf
		try {
			beginRead();
			return super.read(buf
		} catch (InterruptedIOException e) {
			throw readTimedOut();
		} finally {
			endRead();
		}
	}

	@Override
	public long skip(long cnt) throws IOException {
		try {
			beginRead();
			return super.skip(cnt);
		} catch (InterruptedIOException e) {
			throw readTimedOut();
		} finally {
			endRead();
		}
	}

	private void beginRead() {
		myTimer.begin(timeout);
	}

	private void endRead() {
		myTimer.end();
	}

	private InterruptedIOException readTimedOut() {
		return new InterruptedIOException(MessageFormat.format(
				JGitText.get().readTimedOut
	}
}
