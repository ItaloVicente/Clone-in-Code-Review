
package org.eclipse.jgit.internal.storage.file;

import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.transport.PackedObjectInfo;
import org.eclipse.jgit.util.NB;

class PackIndexWriterV1 extends PackIndexWriter {
	static boolean canStore(PackedObjectInfo oe) {
		return oe.getOffset() >>> 1 < Integer.MAX_VALUE;
	}

	PackIndexWriterV1(final OutputStream dst) {
		super(dst);
	}

	@Override
	protected void writeImpl() throws IOException {
		writeFanOutTable();

		for (PackedObjectInfo oe : entries) {
			if (!canStore(oe))
				throw new IOException(JGitText.get().packTooLargeForIndexVersion1);
			NB.encodeInt32(tmp
			oe.copyRawTo(tmp
			out.write(tmp);
		}

		writeChecksumFooter();
	}
}
