
package org.eclipse.jgit.internal.storage.dfs;

import org.eclipse.jgit.internal.storage.pack.StoredObjectRepresentation;
import org.eclipse.jgit.lib.ObjectId;

class DfsObjectRepresentation extends StoredObjectRepresentation {
	final DfsPackFile pack;
	int format;
	long offset;
	long length;
	ObjectId baseId;

	DfsObjectRepresentation(DfsPackFile pack) {
		this.pack = pack;
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

	@Override
	public boolean wasDeltaAttempted() {
		switch (pack.getPackDescription().getPackSource()) {
		case GC:
		case GC_REST:
		case GC_TXN:
			return true;
		default:
			return false;
		}
	}
}
