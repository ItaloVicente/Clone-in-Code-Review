package org.eclipse.jgit.internal.storage.file;

import java.io.BufferedOutputStream;
import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.security.DigestOutputStream;
import java.text.MessageFormat;
import java.util.List;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.transport.PackedObjectInfo;

public abstract class PackReverseIndexWriter {
	protected static byte[] MAGIC = { 'R'

	protected static final int VERSION_1 = 1;

	protected final DigestOutputStream out;

	protected final DataOutput dataOutput;

	private static final int DEFAULT_VERSION = VERSION_1;

	protected PackReverseIndexWriter(OutputStream dst) {
		out = new DigestOutputStream(
				dst instanceof BufferedOutputStream ? dst
						: new BufferedOutputStream(dst)
				Constants.newMessageDigest());
		dataOutput = new SimpleDataOutput(out);
	}

	public static PackReverseIndexWriter createWriter(final OutputStream dst) {
		return createWriter(dst
	}

	public static PackReverseIndexWriter createWriter(final OutputStream dst
			final int version) {
		if (version == VERSION_1) {
			return new PackReverseIndexWriterV1(dst);
		}
		throw new IllegalArgumentException(MessageFormat.format(
				JGitText.get().unsupportedPackReverseIndexVersion
				Integer.toString(version)));
	}

	public void write(
			List<? extends PackedObjectInfo> objectsSortedByIndexPosition
			byte[] packChecksum) throws IOException {
		writeHeader();
		writeBody(objectsSortedByIndexPosition);
		writeFooter(packChecksum);
		out.flush();
	}

	protected abstract void writeHeader() throws IOException;

	protected abstract void writeBody(
			List<? extends PackedObjectInfo> objectsSortedByIndexPosition)
			throws IOException;

	private void writeFooter(byte[] packChecksum) throws IOException {
		out.write(packChecksum);
		byte[] selfChecksum = out.getMessageDigest().digest();
		out.write(selfChecksum);
	}
}
