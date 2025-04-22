
package org.eclipse.jgit.storage.dht;

import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.storage.pack.ObjectToPack;
import org.eclipse.jgit.storage.pack.StoredObjectRepresentation;

final class DhtObjectToPack extends ObjectToPack {
	ChunkKey chunk;

	int offset;

	int size;

	int visitOrder;

	DhtObjectToPack(RevObject obj) {
		super(obj);
	}

	@Override
	public void select(StoredObjectRepresentation ref) {
		DhtObjectRepresentation rep = (DhtObjectRepresentation) ref;
		offset = rep.getOffset();
		chunk = rep.getChunkKey();
		size = rep.getSize();
	}
}
