
package org.eclipse.jgit.storage.dfs;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.storage.pack.ObjectToPack;
import org.eclipse.jgit.storage.pack.StoredObjectRepresentation;

class DfsObjectRepresentation extends StoredObjectRepresentation {
	final ObjectToPack object;

	DfsPackFile pack;

	int packIndex;

	long offset;

	int format;

	long length;

	ObjectId baseId;

	DfsObjectRepresentation(ObjectToPack object) {
		this.object = object;
	}

	@Override
	public int getFormat() {
		return format;
	}

	@Override
	public int getWeight() {
		return (int) Math.min(length
	}

	@Override
	public ObjectId getDeltaBase() {
		return baseId;
	}
}
