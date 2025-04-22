
package org.eclipse.jgit.internal.storage.commitgraph;

import java.io.IOException;

public class CommitGraphFormatException extends IOException {

	private static final long serialVersionUID = 1L;

	CommitGraphFormatException(String why) {
		super(why);
	}
}
