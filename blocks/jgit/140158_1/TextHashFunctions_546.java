
package org.eclipse.jgit.pgm.debug;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.zip.InflaterInputStream;

import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StoredObjectRepresentationNotAvailableException;
import org.eclipse.jgit.internal.storage.pack.BinaryDelta;
import org.eclipse.jgit.internal.storage.pack.ObjectReuseAsIs;
import org.eclipse.jgit.internal.storage.pack.ObjectToPack;
import org.eclipse.jgit.internal.storage.pack.PackOutputStream;
import org.eclipse.jgit.internal.storage.pack.PackWriter;
import org.eclipse.jgit.internal.storage.pack.StoredObjectRepresentation;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.pgm.Command;
import org.eclipse.jgit.pgm.TextBuiltin;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.util.TemporaryBuffer;
import org.kohsuke.args4j.Argument;

@Command(usage = "usage_ShowPackDelta")
class ShowPackDelta extends TextBuiltin {
	@Argument(index = 0)
	private ObjectId objectId;

	@Override
	protected void run() throws Exception {
		ObjectReader reader = db.newObjectReader();
		RevObject obj;
		try (RevWalk rw = new RevWalk(reader)) {
			obj = rw.parseAny(objectId);
		}
		byte[] delta = getDelta(reader

		long size = reader.getObjectSize(obj
		try {
			if (BinaryDelta.getResultSize(delta) != size)
		} catch (ArrayIndexOutOfBoundsException bad) {
		}

		outw.println(BinaryDelta.format(delta));
	}

	private static byte[] getDelta(ObjectReader reader
			throws IOException
			StoredObjectRepresentationNotAvailableException {
		ObjectReuseAsIs asis = (ObjectReuseAsIs) reader;
		ObjectToPack target = asis.newObjectToPack(obj

		PackWriter pw = new PackWriter(reader) {
			@Override
			public void select(ObjectToPack otp
				otp.select(next);
			}
		};

		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		asis.selectObjectRepresentation(pw
				Collections.singleton(target));
		asis.copyObjectAsIs(new PackOutputStream(NullProgressMonitor.INSTANCE
				buf

		byte[] bufArray = buf.toByteArray();
		int ptr = 0;
		while ((bufArray[ptr] & 0x80) != 0)
			ptr++;
		ptr++;

		try (TemporaryBuffer.Heap raw = new TemporaryBuffer.Heap(
				bufArray.length);
				InflaterInputStream inf = new InflaterInputStream(
						new ByteArrayInputStream(bufArray
								bufArray.length))) {
			raw.copy(inf);
			return raw.toByteArray();
		}
	}
}
