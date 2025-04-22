
package org.eclipse.jgit.internal.storage.file;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;

import org.eclipse.jgit.errors.LargeObjectException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectStream;

class LargePackedWholeObject extends ObjectLoader {
	private final int type;

	private final long size;

	private final long objectOffset;

	private final int headerLength;

	private final PackFile pack;

	private final FileObjectDatabase db;

	LargePackedWholeObject(int type
			int headerLength
		this.type = type;
		this.size = size;
		this.objectOffset = objectOffset;
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
	public boolean isLarge() {
		return true;
	}

	@Override
	public byte[] getCachedBytes() throws LargeObjectException {
		try {
			throw new LargeObjectException(getObjectId());
		} catch (IOException cannotObtainId) {
			throw new LargeObjectException(cannotObtainId);
		}
	}

	@Override
	public ObjectStream openStream() throws MissingObjectException
		WindowCursor wc = new WindowCursor(db);
		InputStream in;
		try {
			in = new PackInputStream(pack
		} catch (IOException packGone) {
			return wc.open(getObjectId()
		}

						in
						wc.inflater()
						8192)
				8192);
		return new ObjectStream.Filter(type
	}

	private ObjectId getObjectId() throws IOException {
		return pack.findObjectForOffset(objectOffset);
	}
}
