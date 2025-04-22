
package org.eclipse.jgit.errors;

import java.io.IOException;

public class NotSupportedException extends IOException {
	private static final long serialVersionUID = 1L;

	public NotSupportedException(String s) {
		super(s);
	}

	public NotSupportedException(String s
		super(s);
		initCause(why);
	}
}
