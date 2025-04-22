
package org.eclipse.jgit.internal.storage.io;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ProgressMonitor;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.security.MessageDigest;

public class CancellableDigestOutputStream extends OutputStream {

	public static final int BYTES_TO_WRITE_BEFORE_CANCEL_CHECK = 128 * 1024;

	private final ProgressMonitor writeMonitor;

	private final OutputStream out;

	private final MessageDigest md = Constants.newMessageDigest();

	private long count;

	private long checkCancelAt;

	public CancellableDigestOutputStream(ProgressMonitor writeMonitor
			OutputStream out) {
		this.writeMonitor = writeMonitor;
		this.out = out;
		this.checkCancelAt = BYTES_TO_WRITE_BEFORE_CANCEL_CHECK;
	}

	public final ProgressMonitor getWriteMonitor() {
		return writeMonitor;
	}

	public final byte[] getDigest() {
		return md.digest();
	}

	public final long length() {
		return count;
	}

	@Override
	public final void write(int b) throws IOException {
		if (checkCancelAt <= count) {
			if (writeMonitor.isCancelled()) {
				throw new InterruptedIOException();
			}
			checkCancelAt = count + BYTES_TO_WRITE_BEFORE_CANCEL_CHECK;
		}

		out.write(b);
		md.update((byte) b);
		count++;
	}

	@Override
	public final void write(byte[] b
		while (0 < len) {
			if (checkCancelAt <= count) {
				if (writeMonitor.isCancelled()) {
					throw new InterruptedIOException();
				}
				checkCancelAt = count + BYTES_TO_WRITE_BEFORE_CANCEL_CHECK;
			}

			int n = Math.min(len
			out.write(b
			md.update(b
			count += n;

			off += n;
			len -= n;
		}
	}

	@Override
	public void flush() throws IOException {
		out.flush();
	}
}
