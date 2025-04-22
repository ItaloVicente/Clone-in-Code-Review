package org.eclipse.jgit.lib;

import org.eclipse.jgit.lib.AnyObjectId;

public final class GitmoduleEntry {
	private final AnyObjectId treeId;

	private final AnyObjectId blobId;

	public GitmoduleEntry(AnyObjectId treeId
		this.treeId = treeId.copy();
		this.blobId = blobId.copy();
	}

	public AnyObjectId getBlobId() {
		return blobId;
	}

	public AnyObjectId getTreeId() {
		return treeId;
	}
}
