
package org.eclipse.jgit.storage.file;

import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.storage.pack.ObjectToPack;
import org.eclipse.jgit.storage.pack.StoredObjectRepresentation;

class LocalObjectToPack extends ObjectToPack {
	PackFile pack;

	long offset;

	long length;

	LocalObjectToPack(RevObject obj) {
		super(obj);
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
