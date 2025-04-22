
package org.eclipse.jgit.lfs.errors;

public class LfsInsufficientStorage extends LfsException {
	private static final long serialVersionUID = 1L;

	public LfsInsufficientStorage(String message) {
		super(message);
	}
}
