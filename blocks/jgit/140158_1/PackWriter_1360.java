
package org.eclipse.jgit.internal.storage.pack;

import static org.eclipse.jgit.lib.Constants.OBJ_OFS_DELTA;
import static org.eclipse.jgit.lib.Constants.OBJ_REF_DELTA;
import static org.eclipse.jgit.lib.Constants.PACK_SIGNATURE;

import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.util.NB;

public final class PackOutputStream extends OutputStream {
	private static final int BYTES_TO_WRITE_BEFORE_CANCEL_CHECK = 128 * 1024;

	private final ProgressMonitor writeMonitor;

	private final OutputStream out;

	private final PackWriter packWriter;

	private final MessageDigest md = Constants.newMessageDigest();

	private long count;

	private final byte[] headerBuffer = new byte[32];

	private final byte[] copyBuffer = new byte[64 << 10];

	private long checkCancelAt;

	private boolean ofsDelta;

	public PackOutputStream(final ProgressMonitor writeMonitor
			final OutputStream out
		this.writeMonitor = writeMonitor;
		this.out = out;
		this.packWriter = pw;
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
			throws IOException {
		while (0 < len) {
			final int n = Math.min(len
			count += n;

			if (checkCancelAt <= count) {
				if (writeMonitor.isCancelled()) {
					throw new IOException(
							JGitText.get().packingCancelledDuringObjectsWriting);
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

	final void writeFileHeader(int version
			throws IOException {
		System.arraycopy(PACK_SIGNATURE
		NB.encodeInt32(headerBuffer
		NB.encodeInt32(headerBuffer
		write(headerBuffer
		ofsDelta = packWriter.isDeltaBaseAsOffset();
	}

	public final void writeObject(ObjectToPack otp) throws IOException {
		packWriter.writeObject(this
	}

	@SuppressWarnings("ShortCircuitBoolean")
	public final void writeHeader(ObjectToPack otp
			throws IOException {
		ObjectToPack b = otp.getDeltaBase();
			int n = objectHeader(rawLength
			n = ofsDelta(count - b.getOffset()
			write(headerBuffer
		} else if (otp.isDeltaRepresentation()) {
			int n = objectHeader(rawLength
			otp.getDeltaBaseId().copyRawTo(headerBuffer
			write(headerBuffer
		} else {
			int n = objectHeader(rawLength
			write(headerBuffer
		}
	}

	private static final int objectHeader(long len
		byte b = (byte) ((type << 4) | (len & 0x0F));
		int n = 0;
		for (len >>>= 4; len != 0; len >>>= 7) {
			buf[n++] = (byte) (0x80 | b);
			b = (byte) (len & 0x7F);
		}
		buf[n++] = b;
		return n;
	}

	private static final int ofsDelta(long diff
		p += ofsDeltaVarIntLength(diff);
		int n = p;
		buf[--n] = (byte) (diff & 0x7F);
		while ((diff >>>= 7) != 0)
			buf[--n] = (byte) (0x80 | (--diff & 0x7F));
		return p;
	}

	private static final int ofsDeltaVarIntLength(long v) {
		int n = 1;
		for (; (v >>>= 7) != 0; n++)
			--v;
		return n;
	}

	public final byte[] getCopyBuffer() {
		return copyBuffer;
	}

	void endObject() {
		writeMonitor.update(1);
	}

	public final long length() {
		return count;
	}

	final byte[] getDigest() {
		return md.digest();
	}
}
