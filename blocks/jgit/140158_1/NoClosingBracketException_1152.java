
package org.eclipse.jgit.errors;

import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;

public class MissingObjectException extends IOException {
	private static final long serialVersionUID = 1L;

	private final ObjectId missing;

	public MissingObjectException(ObjectId id
		super(MessageFormat.format(JGitText.get().missingObject
		missing = id.copy();
	}

	public MissingObjectException(ObjectId id
		this(id
	}

	public MissingObjectException(AbbreviatedObjectId id
		super(MessageFormat.format(JGitText.get().missingObject
				.typeString(type)
		missing = null;
	}

	public ObjectId getObjectId() {
		return missing;
	}
}
