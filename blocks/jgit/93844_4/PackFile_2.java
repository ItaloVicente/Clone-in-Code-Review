
package org.eclipse.jgit.errors;

import java.io.IOException;

public class UnsupportedPackVersionException extends IOException {
	private static final long serialVersionUID = 1L;

	public UnsupportedPackVersionException(final String why) {
		super(why);
	}
}
