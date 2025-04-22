
package org.eclipse.jgit.lfs.errors;

import org.eclipse.jgit.lfs.lib.AnyLongObjectId;

public class CorruptLongObjectException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	private AnyLongObjectId id;

	private AnyLongObjectId contentHash;

	public CorruptLongObjectException(AnyLongObjectId id
			AnyLongObjectId contentHash
			String message) {
		super(message);
		this.id = id;
		this.contentHash = contentHash;
	}

	public AnyLongObjectId getId() {
		return id;
	}

	public AnyLongObjectId getContentHash() {
		return contentHash;
	}
}
