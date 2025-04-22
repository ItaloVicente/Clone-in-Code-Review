
package org.eclipse.jgit.errors;

import org.eclipse.jgit.lib.ObjectId;

public class LargeObjectException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public LargeObjectException() {
	}

	public LargeObjectException(ObjectId id) {
		super(id.name());
	}
}
