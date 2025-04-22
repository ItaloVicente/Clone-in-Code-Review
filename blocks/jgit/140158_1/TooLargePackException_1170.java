
package org.eclipse.jgit.errors;

import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.transport.URIish;

public class TooLargeObjectInPackException extends TransportException {
	private static final long serialVersionUID = 1L;

	public TooLargeObjectInPackException(long maxObjectSizeLimit) {
		super(MessageFormat.format(JGitText.get().receivePackObjectTooLarge1
				Long.valueOf(maxObjectSizeLimit)));
	}

	public TooLargeObjectInPackException(long objectSize
			long maxObjectSizeLimit) {
		super(MessageFormat.format(JGitText.get().receivePackObjectTooLarge2
				Long.valueOf(objectSize)
	}

	public TooLargeObjectInPackException(URIish uri
	}
}
