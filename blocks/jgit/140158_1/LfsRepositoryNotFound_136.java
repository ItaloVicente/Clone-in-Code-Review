
package org.eclipse.jgit.lfs.errors;

public class LfsRateLimitExceeded extends LfsException {
	private static final long serialVersionUID = 1L;

	public LfsRateLimitExceeded(String message) {
		super(message);
	}
}
