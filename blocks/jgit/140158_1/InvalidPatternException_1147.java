
package org.eclipse.jgit.errors;

import static java.nio.charset.StandardCharsets.US_ASCII;

import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;

public class InvalidObjectIdException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;

	public InvalidObjectIdException(byte[] bytes
		super(msg(bytes
	}

	public InvalidObjectIdException(String id) {
		super(MessageFormat.format(JGitText.get().invalidId
	}

	private static String msg(byte[] bytes
		try {
			return MessageFormat.format(
					JGitText.get().invalidId
					new String(bytes
		} catch (StringIndexOutOfBoundsException e) {
			return JGitText.get().invalidId0;
		}
	}
}
