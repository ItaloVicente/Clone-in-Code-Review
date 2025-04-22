
package org.eclipse.jgit.transport;

import java.text.MessageFormat;

import org.eclipse.jgit.errors.PackProtocolException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AnyObjectId;

public class WantNotValidException extends PackProtocolException {
	private static final long serialVersionUID = 1L;

	public WantNotValidException(AnyObjectId id) {
		super(msg(id));
	}

	public WantNotValidException(AnyObjectId id
		super(msg(id)
	}

	private static String msg(AnyObjectId id) {
		return MessageFormat.format(JGitText.get().wantNotValid
	}
}
