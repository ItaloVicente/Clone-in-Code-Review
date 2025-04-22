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

	protected final DigestOutputStream out;
	protected final DataOutput dataOutput;

	protected List<? extends PackedObjectInfo> objectsSortedByIndexPosition;
	private byte[] packChecksum;

	protected static final int VERSION_1 = 1;
	private static final int DEFAULT_VERSION = VERSION_1;

	protected PackReverseIndexWriter(OutputStream dst) {
		out = new DigestOutputStream(dst instanceof BufferedOutputStream ? dst : new BufferedOutputStream(dst)
				Constants.newMessageDigest());
		dataOutput = new SimpleDataOutput(out);
	}

	public static PackReverseIndexWriter createWriter(final OutputStream dst) {
		return createWriter(dst
	}

	public static PackReverseIndexWriter createWriter(final OutputStream dst
		if (version == VERSION_1) {
			return new PackReverseIndexWriterV1(dst);
		} else {
			throw new IllegalArgumentException(
					MessageFormat.format(JGitText.get().unsupportedPackReverseIndexVersion
		}
	}

	public void write(List<? extends PackedObjectInfo> objectsSortedByIndexPosition
			throws IOException {
		this.objectsSortedByIndexPosition = objectsSortedByIndexPosition;
		this.packChecksum = packChecksum;
		writeHeader();
		writeBody();
		writeFooter();
		out.flush();
	}

	protected abstract void writeHeader() throws IOException;

	protected abstract void writeBody() throws IOException;

	private void writeFooter() throws IOException {
		out.write(packChecksum);
		byte[] selfChecksum = out.getMessageDigest().digest();
		out.write(selfChecksum);
	}
}
