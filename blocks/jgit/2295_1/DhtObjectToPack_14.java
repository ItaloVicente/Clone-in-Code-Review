
package org.eclipse.jgit.storage.dht;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.storage.pack.StoredObjectRepresentation;

final class DhtObjectRepresentation extends StoredObjectRepresentation {
	private ChunkLink link;

	void set(ChunkLink link) {
		this.link = link;
	}

	ChunkKey getChunkKey() {
		return link.getChunkKey();
	}

	int getOffset() {
		return link.getOffset();
	}

	int getSize() {
		return link.getSize();
	}

	@Override
	public ObjectId getDeltaBase() {
		return link.getDeltaBase();
	}

	@Override
	public int getFormat() {
		if (link.getDeltaBase() != null)
			return PACK_DELTA;
		else
			return PACK_WHOLE;
	}

	@Override
	public int getWeight() {
		return getSize();
	}
}
