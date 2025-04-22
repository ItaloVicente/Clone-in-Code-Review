
package org.eclipse.jgit.errors;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collection;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.ObjectId;

public class AmbiguousObjectException extends IOException {
	private static final long serialVersionUID = 1L;

	private final AbbreviatedObjectId missing;

	private final Collection<ObjectId> candidates;

	public AmbiguousObjectException(final AbbreviatedObjectId id
			final Collection<ObjectId> candidates) {
		super(MessageFormat.format(JGitText.get().ambiguousObjectAbbreviation
				id.name()));
		this.missing = id;
		this.candidates = candidates;
	}

	public AbbreviatedObjectId getAbbreviatedObjectId() {
		return missing;
	}

	public Collection<ObjectId> getCandidates() {
		return candidates;
	}
}
