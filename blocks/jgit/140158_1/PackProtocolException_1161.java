
package org.eclipse.jgit.errors;

import java.io.IOException;

public class PackMismatchException extends IOException {
	private static final long serialVersionUID = 1L;

	public PackMismatchException(String why) {
		super(why);
	}
}
