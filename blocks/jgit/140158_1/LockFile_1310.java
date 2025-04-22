
package org.eclipse.jgit.internal.storage.file;

import org.eclipse.jgit.internal.storage.pack.ObjectToPack;
import org.eclipse.jgit.internal.storage.pack.StoredObjectRepresentation;
import org.eclipse.jgit.lib.AnyObjectId;

class LocalObjectToPack extends ObjectToPack {
	PackFile pack;

	long offset;

	long length;

	LocalObjectToPack(AnyObjectId src
		super(src
	}

	@Override
	protected void clearReuseAsIs() {
		super.clearReuseAsIs();
		pack = null;
	}

	@Override
	public void select(StoredObjectRepresentation ref) {
		LocalObjectRepresentation ptr = (LocalObjectRepresentation) ref;
		this.pack = ptr.pack;
		this.offset = ptr.offset;
		this.length = ptr.length;
	}
}
