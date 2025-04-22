
package org.eclipse.jgit.lfs.errors;

public class LfsBandwidthLimitExceeded extends LfsException {
	private static final long serialVersionUID = 1L;

	public LfsBandwidthLimitExceeded(String message) {
		super(message);
	}
}
