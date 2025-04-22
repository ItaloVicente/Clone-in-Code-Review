package org.eclipse.jgit.lib;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

import org.eclipse.jgit.internal.JGitText;

public class RepositoryShallow {



	protected static final int HASH_LENGTH = 40;

	public void lock() throws IOException {
	}

	public List<ObjectId> read() throws IOException {
		return Collections.emptyList();
	}

	public boolean parseShallowUnshallowLine(final String line)
			throws IOException {
		final int length = line.length();
		if (length == 0) {
			return false;
		}
		if (line.startsWith(PREFIX_SHALLOW)) {
			convertStringToObjectId(line
					PREFIX_SHALLOW.length() + HASH_LENGTH);
		} else if (line.startsWith(PREFIX_UNSHALLOW)) {
			convertStringToObjectId(line
					PREFIX_UNSHALLOW.length() + HASH_LENGTH);
		} else {
			throw new RepositoryShallowException(
					JGitText.get().expectedShallowUnshallowGot
		}
		return true;
	}

	protected ObjectId convertStringToObjectId(final String string
			int beginIndex
		final String objectIdAsString = string.substring(beginIndex
		try {
			final ObjectId result = ObjectId.fromString(objectIdAsString);
			return result;
		} catch (IllegalArgumentException ex) {
			throw new IOException(
					MessageFormat.format(JGitText.get().badShallowLine
							objectIdAsString));
		}
	}

	public void unlock(final boolean writeChanges) throws IOException {
	}

	protected class RepositoryShallowException extends IOException {

		private static final long serialVersionUID = -6812015161109424512L;

		public RepositoryShallowException(final String message) {
			super(message);
		}

		public RepositoryShallowException(final String title
				final String argument) {
			super(MessageFormat.format(title
		}

	}

}
