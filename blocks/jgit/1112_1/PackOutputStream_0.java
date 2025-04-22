
package org.eclipse.jgit.pgm.debug;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.InflaterInputStream;

import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StoredObjectRepresentationNotAvailableException;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.pgm.TextBuiltin;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.pack.BinaryDelta;
import org.eclipse.jgit.storage.pack.ObjectReuseAsIs;
import org.eclipse.jgit.storage.pack.ObjectToPack;
import org.eclipse.jgit.storage.pack.PackOutputStream;
import org.eclipse.jgit.storage.pack.PackWriter;
import org.eclipse.jgit.storage.pack.StoredObjectRepresentation;
import org.eclipse.jgit.util.TemporaryBuffer;
import org.kohsuke.args4j.Argument;

class ShowPackDelta extends TextBuiltin {
	@Argument(index = 0)
	private ObjectId objectId;

	@Override
	protected void run() throws Exception {
		ObjectReader reader = db.newObjectReader();
		RevObject obj = new RevWalk(reader).parseAny(objectId);
		byte[] delta = getDelta(reader

		long size = reader.getObjectSize(obj
		try {
			if (BinaryDelta.getResultSize(delta) != size)
				throw die("Object " + obj.name() + " is not a delta");
		} catch (ArrayIndexOutOfBoundsException bad) {
			throw die("Object " + obj.name() + " is not a delta");
		}

		out.println(BinaryDelta.format(delta));
	}

	private byte[] getDelta(ObjectReader reader
			throws IOException
			StoredObjectRepresentationNotAvailableException {
		ObjectReuseAsIs asis = (ObjectReuseAsIs) reader;
		ObjectToPack target = asis.newObjectToPack(obj);

		PackWriter pw = new PackWriter(reader) {
			@Override
			public void select(ObjectToPack otp
				otp.select(next);
			}
		};

		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		asis.selectObjectRepresentation(pw
		asis.copyObjectAsIs(new PackOutputStream(NullProgressMonitor.INSTANCE
				buf

		byte[] bufArray = buf.toByteArray();
		int ptr = 0;
		while ((bufArray[ptr] & 0x80) != 0)
			ptr++;
		ptr++;

		TemporaryBuffer.Heap raw = new TemporaryBuffer.Heap(bufArray.length);
		InflaterInputStream inf = new InflaterInputStream(
				new ByteArrayInputStream(bufArray
		raw.copy(inf);
		inf.close();
		return raw.toByteArray();
	}
}
