
package org.eclipse.jgit.errors;

import java.io.IOException;

public class ObjectWritingException extends IOException {
	private static final long serialVersionUID = 1L;

	public ObjectWritingException(String s) {
		super(s);
	}

	public ObjectWritingException(String s
		super(s);
		initCause(cause);
	}
}
