
package org.eclipse.jgit.storage.dfs;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;

import org.eclipse.jgit.errors.LargeObjectException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectStream;

final class LargePackedWholeObject extends ObjectLoader {
	private final int type;

	private final long size;

	private final long objectOffset;

	private final int headerLength;

	private final DfsPackFile pack;

	private final DfsObjDatabase db;

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
		throw new LargeObjectException();
	}

	@Override
	public ObjectStream openStream() throws MissingObjectException
		DfsReader ctx = new DfsReader(db);
		InputStream in;
		try {
			in = new PackInputStream(pack
		} catch (IOException packGone) {
			try {
				ObjectId obj = pack.findObjectForOffset(ctx
				return ctx.open(obj
			} finally {
				ctx.release();
			}
		}

						in
						ctx.inflater()
						8192)
				8192);
		return new ObjectStream.Filter(type
	}
}
