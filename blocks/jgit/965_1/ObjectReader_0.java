
package org.eclipse.jgit.lib;

import java.io.IOException;

import org.eclipse.jgit.revwalk.RevObject;

class LocalObjectToPack extends ObjectToPack {
	private PackFile copyFromPack;

	private long copyOffset;

	LocalObjectToPack(RevObject obj) {
		super(obj);
	}

	boolean isCopyable() {
		return copyFromPack != null;
	}

	PackedObjectLoader getCopyLoader(WindowCursor curs) throws IOException {
		return copyFromPack.resolveBase(curs
	}

	void setCopyFromPack(PackedObjectLoader loader) {
		this.copyFromPack = loader.pack;
		this.copyOffset = loader.objectOffset;
	}

	void clearSourcePack() {
		copyFromPack = null;
	}
}
