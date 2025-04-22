
package org.eclipse.jgit.errors;

import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;

public class CheckoutConflictException extends IOException {
	private static final long serialVersionUID = 1L;

	private final String[] conflicting;

	public CheckoutConflictException(String file) {
		super(MessageFormat.format(JGitText.get().checkoutConflictWithFile
		conflicting = new String[] { file };
	}

	public CheckoutConflictException(String[] files) {
		super(MessageFormat.format(JGitText.get().checkoutConflictWithFiles
		conflicting = files;
	}

	public String[] getConflictingFiles() {
		return conflicting;
	}

	private static String buildList(String[] files) {
		StringBuilder builder = new StringBuilder();
		for (String f : files) {
			builder.append(f);
		}
		return builder.toString();
	}
}
