
package org.eclipse.jgit.internal.storage.commitgraph;

import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.COMMIT_GRAPH_MAGIC;

import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.util.NB;

public class CommitGraphOutPutStream extends OutputStream {

	private static final int BYTES_TO_WRITE_BEFORE_CANCEL_CHECK = 128 * 1024;

	private final ProgressMonitor writeMonitor;

	private final OutputStream out;

	private final MessageDigest md = Constants.newMessageDigest();

	private long count;

	private long checkCancelAt;

	private final byte[] headerBuffer = new byte[16];

	public CommitGraphOutPutStream(ProgressMonitor writeMonitor
			OutputStream out) {
		this.writeMonitor = writeMonitor;
		this.out = out;
		this.checkCancelAt = BYTES_TO_WRITE_BEFORE_CANCEL_CHECK;
	}

	@Override
	public final void write(int b) throws IOException {
		count++;
		out.write(b);
		md.update((byte) b);
	}

	@Override
	public final void write(byte[] b
		while (0 < len) {
			int n = Math.min(len
			count += n;

			if (checkCancelAt <= count) {
				if (writeMonitor.isCancelled()) {
					throw new IOException(JGitText
							.get().commitGraphGeneratingCancelledDuringWriting);
				}
				checkCancelAt = count + BYTES_TO_WRITE_BEFORE_CANCEL_CHECK;
			}

			out.write(b
			md.update(b

			off += n;
			len -= n;
		}
	}

	@Override
	public void flush() throws IOException {
		out.flush();
	}

	void writeFileHeader(int version
			throws IOException {
		NB.encodeInt32(headerBuffer
		byte[] buff = { (byte) version
				(byte) 0 };
		System.arraycopy(buff
		write(headerBuffer
	}

	byte[] getDigest() {
		return md.digest();
	}

	void updateMonitor() {
		writeMonitor.update(1);
	}
}
