
package org.eclipse.jgit.storage.pack;

import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.zip.CRC32;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.util.NB;

public final class PackOutputStream extends OutputStream {
	private final ProgressMonitor writeMonitor;

	private final OutputStream out;

	private final boolean ofsDelta;

	private final CRC32 crc = new CRC32();

	private final MessageDigest md = Constants.newMessageDigest();

	private long count;

	private byte[] headerBuffer = new byte[32];

	private byte[] copyBuffer;

	public PackOutputStream(final ProgressMonitor writeMonitor
			final OutputStream out
		this.writeMonitor = writeMonitor;
		this.out = out;
		this.ofsDelta = pw.isDeltaBaseAsOffset();
	}

	@Override
	public void write(final int b) throws IOException {
		count++;
		out.write(b);
		crc.update(b);
		md.update((byte) b);
	}

	@Override
	public void write(final byte[] b
			throws IOException {
		count += len;
		out.write(b
		crc.update(b
		md.update(b
	}

	@Override
	public void flush() throws IOException {
		out.flush();
	}

	void writeFileHeader(int version
		System.arraycopy(Constants.PACK_SIGNATURE
		NB.encodeInt32(headerBuffer
		NB.encodeInt32(headerBuffer
		write(headerBuffer
	}

	public void writeHeader(ObjectToPack otp
			throws IOException {
		if (otp.isDeltaRepresentation()) {
			if (ofsDelta) {
				ObjectToPack baseInPack = otp.getDeltaBase();
				if (baseInPack != null && baseInPack.isWritten()) {
					final long start = count;
					int n = encodeTypeSize(Constants.OBJ_OFS_DELTA
					write(headerBuffer

					long offsetDiff = start - baseInPack.getOffset();
					n = headerBuffer.length - 1;
					headerBuffer[n] = (byte) (offsetDiff & 0x7F);
					while ((offsetDiff >>= 7) > 0)
						headerBuffer[--n] = (byte) (0x80 | (--offsetDiff & 0x7F));
					write(headerBuffer
					return;
				}
			}

			int n = encodeTypeSize(Constants.OBJ_REF_DELTA
			otp.getDeltaBaseId().copyRawTo(headerBuffer
			write(headerBuffer
		} else {
			int n = encodeTypeSize(otp.getType()
			write(headerBuffer
		}
	}

	private int encodeTypeSize(int type
		long nextLength = rawLength >>> 4;
		headerBuffer[0] = (byte) ((nextLength > 0 ? 0x80 : 0x00)
				| (type << 4) | (rawLength & 0x0F));
		rawLength = nextLength;
		int n = 1;
		while (rawLength > 0) {
			nextLength >>>= 7;
			headerBuffer[n++] = (byte) ((nextLength > 0 ? 0x80 : 0x00) | (rawLength & 0x7F));
			rawLength = nextLength;
		}
		return n;
	}

	public byte[] getCopyBuffer() {
		if (copyBuffer == null)
			copyBuffer = new byte[16 * 1024];
		return copyBuffer;
	}

	void endObject() {
		writeMonitor.update(1);
	}

	long length() {
		return count;
	}

	int getCRC32() {
		return (int) crc.getValue();
	}

	void resetCRC32() {
		crc.reset();
	}

	byte[] getDigest() {
		return md.digest();
	}
}
