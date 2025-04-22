
package org.eclipse.jgit.errors;

import java.io.IOException;

public class SymlinksNotSupportedException extends IOException {
	private static final long serialVersionUID = 1L;

	public SymlinksNotSupportedException(String s) {
		super(s);
	}
}
