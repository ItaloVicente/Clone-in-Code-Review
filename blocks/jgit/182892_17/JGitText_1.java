
package org.eclipse.jgit.errors;

import java.io.IOException;

public class CommitGraphFormatException extends IOException {

	private static final long serialVersionUID = 1L;

	public CommitGraphFormatException(String why) {
		super(why);
	}
}
