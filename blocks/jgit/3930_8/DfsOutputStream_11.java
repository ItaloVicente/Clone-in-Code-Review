
package org.eclipse.jgit.storage.dfs;

import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.storage.pack.ObjectToPack;
import org.eclipse.jgit.storage.pack.StoredObjectRepresentation;

class DfsObjectToPack extends ObjectToPack {
	DfsPackFile pack;

	int packIndex;

	long offset;

	long length;

	DfsObjectToPack(RevObject obj) {
		super(obj);
	}

	@Override
	protected void clearReuseAsIs() {
		super.clearReuseAsIs();
		pack = null;
	}

	@Override
	public void select(StoredObjectRepresentation ref) {
		DfsObjectRepresentation ptr = (DfsObjectRepresentation) ref;
		this.pack = ptr.pack;
		this.packIndex = ptr.packIndex;
		this.offset = ptr.offset;
		this.length = ptr.length;
	}
}
