
package org.eclipse.jgit.internal.storage.file;

import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.jgit.transport.PackedObjectInfo;
import org.eclipse.jgit.util.NB;

class PackIndexWriterV2 extends PackIndexWriter {
	private static final int MAX_OFFSET_32 = 0x7fffffff;
	private static final int IS_OFFSET_64 = 0x80000000;

	PackIndexWriterV2(final OutputStream dst) {
		super(dst);
	}

	@Override
	protected void writeImpl() throws IOException {
		writeTOC(2);
		writeFanOutTable();
		writeObjectNames();
		writeCRCs();
		writeOffset32();
		writeOffset64();
		writeChecksumFooter();
	}

	private void writeObjectNames() throws IOException {
		for (PackedObjectInfo oe : entries)
			oe.copyRawTo(out);
	}

	private void writeCRCs() throws IOException {
		for (PackedObjectInfo oe : entries) {
			NB.encodeInt32(tmp
			out.write(tmp
		}
	}

	private void writeOffset32() throws IOException {
		int o64 = 0;
		for (PackedObjectInfo oe : entries) {
			final long o = oe.getOffset();
			if (o <= MAX_OFFSET_32)
				NB.encodeInt32(tmp
			else
				NB.encodeInt32(tmp
			out.write(tmp
		}
	}

	private void writeOffset64() throws IOException {
		for (PackedObjectInfo oe : entries) {
			final long o = oe.getOffset();
			if (MAX_OFFSET_32 < o) {
				NB.encodeInt64(tmp
				out.write(tmp
			}
		}
	}
}
