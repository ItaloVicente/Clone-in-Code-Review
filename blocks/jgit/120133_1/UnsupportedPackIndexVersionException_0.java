
package org.eclipse.jgit.errors;

import java.io.IOException;

public class NoPackSignatureException extends IOException {
	private static final long serialVersionUID = 1L;

	public NoPackSignatureException(final String why) {
		super(why);
	}
}
