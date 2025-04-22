
package org.eclipse.jgit.errors;

import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;

public class UnsupportedPackVersionException extends IOException {
	private static final long serialVersionUID = 1L;

	public UnsupportedPackVersionException(final long version) {
		super(MessageFormat.format(JGitText.get().unsupportedPackVersion
				Long.valueOf(version)));
	}
}
