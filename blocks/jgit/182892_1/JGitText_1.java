
package org.eclipse.jgit.errors;

import java.io.IOException;

public class CommitGraphFormatException extends IOException {

	public CommitGraphFormatException(String why) {
		super(why);
	}
}
