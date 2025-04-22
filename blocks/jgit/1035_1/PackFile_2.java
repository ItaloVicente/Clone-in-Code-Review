
package org.eclipse.jgit.storage.file;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.LargeObjectException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectStream;
import org.eclipse.jgit.storage.pack.DeltaStream;

class LargePackedDeltaObject extends ObjectLoader {
	private final int type;

	private final long size;

	private final long objectOffset;

	private final long baseOffset;

	private final int headerLength;

	private final PackFile pack;

	private final FileObjectDatabase db;

	LargePackedDeltaObject(int type
			long baseOffset
			FileObjectDatabase db) {
		this.type = type;
		this.size = size;
		this.objectOffset = objectOffset;
		this.baseOffset = baseOffset;
		this.headerLength = headerLength;
		this.pack = pack;
		this.db = db;
	}

	@Override
	public int getType() {
		return type;
	}

	@Override
	public long getSize() {
		return size;
	}

	@Override
	public byte[] getCachedBytes() throws LargeObjectException {
		try {
			throw new LargeObjectException(getObjectId());
		} catch (IOException cannotObtainId) {
			throw new LargeObjectException();
		}
	}

	@Override
	public ObjectStream openStream() throws MissingObjectException
		final WindowCursor wc = new WindowCursor(db);
		InputStream in = open(wc);
		in = new BufferedInputStream(in
		return new ObjectStream.Filter(type
			@Override
			public void close() throws IOException {
				wc.release();
				super.close();
			}
		};
	}

	private InputStream open(final WindowCursor wc)
			throws MissingObjectException
			IncorrectObjectTypeException {
		InputStream delta;
		try {
			delta = new PackInputStream(pack
		} catch (IOException packGone) {
			return wc.open(getObjectId()
		}
		delta = new InflaterInputStream(delta);

		final ObjectLoader base = pack.load(wc
		return new DeltaStream(delta) {
			@Override
			protected InputStream openBase() throws IOException {
				if (base instanceof LargePackedDeltaObject)
					return ((LargePackedDeltaObject) base).open(wc);
				return base.openStream();
			}

			@Override
			protected long getBaseSize() throws IOException {
				return base.getSize();
			}
		};
	}

	private ObjectId getObjectId() throws IOException {
		return pack.findObjectForOffset(objectOffset);
	}
}
