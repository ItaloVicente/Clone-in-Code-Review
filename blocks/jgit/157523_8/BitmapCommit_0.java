package org.eclipse.jgit.internal.storage.pack;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;

public final class BitmapCommit extends ObjectId {
	private final boolean reuseWalker;
	private final int flags;

	BitmapCommit(AnyObjectId objectId
		super(objectId);
		this.reuseWalker = reuseWalker;
		this.flags = flags;
	}

	boolean isReuseWalker() {
		return reuseWalker;
	}

	int getFlags() {
		return flags;
	}
}
