
package org.eclipse.jgit.errors;

import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.transport.URIish;

public class TooLargePackException extends TransportException {
	private static final long serialVersionUID = 1L;

	public TooLargePackException(long packSizeLimit) {
		super(MessageFormat.format(JGitText.get().receivePackTooLarge
				Long.valueOf(packSizeLimit)));
	}

	public TooLargePackException(URIish uri
	}
}
