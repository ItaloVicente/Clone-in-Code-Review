
package org.eclipse.jgit.errors;

import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.JGitText;

public class TooLargeObjectInPackException extends IOException {
	private static final long serialVersionUID = 1L;

	public TooLargeObjectInPackException(long maxObjectSizeLimit) {
		super(MessageFormat.format(JGitText.get().receivePackObjectTooLarge1
				maxObjectSizeLimit));
	}

	public TooLargeObjectInPackException(long objectSize
			long maxObjectSizeLimit) {
		super(MessageFormat.format(JGitText.get().receivePackObjectTooLarge2
				objectSize
	}
}
