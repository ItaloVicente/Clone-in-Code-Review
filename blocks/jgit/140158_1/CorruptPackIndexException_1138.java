
package org.eclipse.jgit.errors;

import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectChecker;
import org.eclipse.jgit.lib.ObjectId;

public class CorruptObjectException extends IOException {
	private static final long serialVersionUID = 1L;

	private ObjectChecker.ErrorType errorType;

	public CorruptObjectException(ObjectChecker.ErrorType type
			String why) {
		super(MessageFormat.format(JGitText.get().objectIsCorrupt3
				type.getMessageId()
		this.errorType = type;
	}

	public CorruptObjectException(AnyObjectId id
		super(MessageFormat.format(JGitText.get().objectIsCorrupt
	}

	public CorruptObjectException(ObjectId id
		super(MessageFormat.format(JGitText.get().objectIsCorrupt
	}

	public CorruptObjectException(String why) {
		super(why);
	}

	public CorruptObjectException(String why
		super(why);
		initCause(cause);
	}

	@Nullable
	public ObjectChecker.ErrorType getErrorType() {
		return errorType;
	}
}
