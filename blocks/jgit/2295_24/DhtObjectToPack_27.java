
package org.eclipse.jgit.storage.dht;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.storage.pack.StoredObjectRepresentation;

final class DhtObjectRepresentation extends StoredObjectRepresentation {
	private ObjectInfo info;

	void set(ObjectInfo link) {
		this.info = link;
	}

	ChunkKey getChunkKey() {
		return info.getChunkKey();
	}

	int getOffset() {
		return info.getOffset();
	}

	long getPackedSize() {
		return info.getPackedSize();
	}

	boolean isFragmented() {
		return info.isFragmented();
	}

	@Override
	public ObjectId getDeltaBase() {
		return info.getDeltaBase();
	}

	@Override
	public int getFormat() {
		if (info.getDeltaBase() != null)
			return PACK_DELTA;
		return PACK_WHOLE;
	}

	@Override
	public int getWeight() {
		long size = info.getPackedSize();
		return (int) Math.min(size
	}
}
