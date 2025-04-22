
package org.eclipse.jgit.internal.storage.dfs;

import org.eclipse.jgit.internal.storage.pack.ObjectToPack;
import org.eclipse.jgit.internal.storage.pack.StoredObjectRepresentation;
import org.eclipse.jgit.lib.AnyObjectId;

class DfsObjectToPack extends ObjectToPack {
	private static final int FLAG_FOUND = 1 << 0;

	DfsPackFile pack;

	long offset;

	long length;

	DfsObjectToPack(AnyObjectId src
		super(src
	}

	final boolean isFound() {
		return isExtendedFlag(FLAG_FOUND);
	}

	final void setFound() {
		setExtendedFlag(FLAG_FOUND);
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
		this.offset = ptr.offset;
		this.length = ptr.length;
	}
}
