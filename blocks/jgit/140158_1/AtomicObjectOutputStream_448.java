
package org.eclipse.jgit.lfs.errors;

public class LfsValidationError extends LfsException {
	private static final long serialVersionUID = 1L;

	public LfsValidationError(String message) {
		super(message);
	}
}
