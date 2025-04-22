
package org.eclipse.jgit.internal.storage.file;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.DigestOutputStream;
import java.text.MessageFormat;
import java.util.List;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.transport.PackedObjectInfo;
import org.eclipse.jgit.util.NB;

public abstract class PackIndexWriter {
	protected static final byte[] TOC = { -1

	public static PackIndexWriter createOldestPossible(final OutputStream dst
			final List<? extends PackedObjectInfo> objs) {
		return createVersion(dst
	}

	public static int oldestPossibleFormat(
			final List<? extends PackedObjectInfo> objs) {
		for (PackedObjectInfo oe : objs) {
			if (!PackIndexWriterV1.canStore(oe))
				return 2;
		}
		return 1;
	}


	public static PackIndexWriter createVersion(final OutputStream dst
			final int version) {
		switch (version) {
		case 1:
			return new PackIndexWriterV1(dst);
		case 2:
			return new PackIndexWriterV2(dst);
		default:
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().unsupportedPackIndexVersion
					Integer.valueOf(version)));
		}
	}

	protected final DigestOutputStream out;

	protected final byte[] tmp;

	protected List<? extends PackedObjectInfo> entries;

	protected byte[] packChecksum;

	protected PackIndexWriter(OutputStream dst) {
		out = new DigestOutputStream(dst instanceof BufferedOutputStream ? dst
				: new BufferedOutputStream(dst)
				Constants.newMessageDigest());
		tmp = new byte[4 + Constants.OBJECT_ID_LENGTH];
	}

	public void write(final List<? extends PackedObjectInfo> toStore
			final byte[] packDataChecksum) throws IOException {
		entries = toStore;
		packChecksum = packDataChecksum;
		writeImpl();
		out.flush();
	}

	protected abstract void writeImpl() throws IOException;

	protected void writeTOC(int version) throws IOException {
		out.write(TOC);
		NB.encodeInt32(tmp
		out.write(tmp
	}

	protected void writeFanOutTable() throws IOException {
		final int[] fanout = new int[256];
		for (PackedObjectInfo po : entries)
			fanout[po.getFirstByte() & 0xff]++;
		for (int i = 1; i < 256; i++)
			fanout[i] += fanout[i - 1];
		for (int n : fanout) {
			NB.encodeInt32(tmp
			out.write(tmp
		}
	}

	protected void writeChecksumFooter() throws IOException {
		out.write(packChecksum);
		out.on(false);
		out.write(out.getMessageDigest().digest());
	}
}
