
package org.eclipse.jgit.errors;

import java.io.IOException;

public class CancelledException extends IOException {
	private static final long serialVersionUID = 1L;

	public CancelledException(String message) {
		super(message);
	}
}
