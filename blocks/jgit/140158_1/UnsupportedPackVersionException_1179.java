
package org.eclipse.jgit.errors;

import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;

public class UnsupportedPackIndexVersionException extends IOException {
	private static final long serialVersionUID = 1L;

	public UnsupportedPackIndexVersionException(int version) {
		super(MessageFormat.format(JGitText.get().unsupportedPackIndexVersion
				Integer.valueOf(version)));
	}
}
