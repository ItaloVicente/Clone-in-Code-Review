
package org.eclipse.jgit.storage.file;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.DataFormatException;
import java.util.zip.InflaterInputStream;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.LargeObjectException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectStream;
import org.eclipse.jgit.storage.pack.BinaryDelta;
import org.eclipse.jgit.storage.pack.DeltaStream;

class LargePackedDeltaObject extends ObjectLoader {
	private static final long SIZE_UNKNOWN = -1;

	private int type;

	private long size;

	private final long objectOffset;

	private final long baseOffset;

	private final int headerLength;

	private final PackFile pack;

	private final FileObjectDatabase db;

	LargePackedDeltaObject(long objectOffset
			long baseOffset
			FileObjectDatabase db) {
		this.type = Constants.OBJ_BAD;
		this.size = SIZE_UNKNOWN;
		this.objectOffset = objectOffset;
		this.baseOffset = baseOffset;
		this.headerLength = headerLength;
		this.pack = pack;
		this.db = db;
	}

	@Override
	public int getType() {
		if (type == Constants.OBJ_BAD) {
			WindowCursor wc = new WindowCursor(db);
			try {
				type = pack.getObjectType(wc
			} catch (IOException packGone) {
				try {
					type = wc.open(getObjectId()).getType();
				} catch (IOException packGone2) {
				}
			} finally {
				wc.release();
			}
		}
		return type;
	}

	@Override
	public long getSize() {
		if (size == SIZE_UNKNOWN) {
			WindowCursor wc = new WindowCursor(db);
			try {
				byte[] b = pack.getDeltaHeader(wc
				size = BinaryDelta.getResultSize(b);
			} catch (DataFormatException objectCorrupt) {
			} catch (IOException packGone) {
				try {
					size = wc.open(getObjectId()).getSize();
				} catch (IOException packGone2) {
				}
			} finally {
				wc.release();
			}
		}
		return size;
	}

	@Override
	public boolean isLarge() {
		return true;
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
		return new ObjectStream.Filter(getType()
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
			return wc.open(getObjectId()).openStream();
		}
		delta = new InflaterInputStream(delta);

		final ObjectLoader base = pack.load(wc
		DeltaStream ds = new DeltaStream(delta) {
			private long baseSize = SIZE_UNKNOWN;

			@Override
			protected InputStream openBase() throws IOException {
				InputStream in;
				if (base instanceof LargePackedDeltaObject)
					in = ((LargePackedDeltaObject) base).open(wc);
				else
					in = base.openStream();
				if (baseSize == SIZE_UNKNOWN) {
					if (in instanceof DeltaStream)
						baseSize = ((DeltaStream) in).getSize();
					else if (in instanceof ObjectStream)
						baseSize = ((ObjectStream) in).getSize();
				}
				return in;
			}

			@Override
			protected long getBaseSize() throws IOException {
				if (baseSize == SIZE_UNKNOWN) {
					baseSize = base.getSize();
				}
				return baseSize;
			}
		};
		if (size == SIZE_UNKNOWN)
			size = ds.getSize();
		return ds;
	}

	private ObjectId getObjectId() throws IOException {
		return pack.findObjectForOffset(objectOffset);
	}
}
