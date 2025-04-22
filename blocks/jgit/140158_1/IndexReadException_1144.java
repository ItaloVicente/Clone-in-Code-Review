
package org.eclipse.jgit.errors;

import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;

public class IncorrectObjectTypeException extends IOException {
	private static final long serialVersionUID = 1L;

	public IncorrectObjectTypeException(ObjectId id
		super(MessageFormat.format(JGitText.get().objectIsNotA
	}

	public IncorrectObjectTypeException(ObjectId id
		this(id
	}
}
