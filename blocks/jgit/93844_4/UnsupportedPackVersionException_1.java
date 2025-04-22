
package org.eclipse.jgit.errors;

import java.io.IOException;

public class UnsupportedPackIndexVersionException extends IOException {
	private static final long serialVersionUID = 1L;

	public UnsupportedPackIndexVersionException(final String why) {
		super(why);
	}
}
