
package org.eclipse.jgit.errors;

import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;

public class TooLargePackException extends IOException {
	private static final long serialVersionUID = 1L;

	public TooLargePackException(long packSizeLimit) {
		super(MessageFormat.format(JGitText.get().receivePackTooLarge
				Long.valueOf(packSizeLimit)));
	}
}
