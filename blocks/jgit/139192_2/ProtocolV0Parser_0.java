
package org.eclipse.jgit.transport;

import java.text.MessageFormat;

import org.eclipse.jgit.errors.PackProtocolException;
import org.eclipse.jgit.internal.JGitText;

final class FilterSpec {

	private FilterSpec() {}

	static long parseFilterLine(String filterLine)
			throws PackProtocolException {
		long blobLimit = -1;

			blobLimit = 0;
			try {
				blobLimit = Long
			} catch (NumberFormatException e) {
				throw new PackProtocolException(MessageFormat
						.format(JGitText.get().invalidFilter
			}
		}
		if (blobLimit < 0) {
			throw new PackProtocolException(
					MessageFormat.format(
							JGitText.get().invalidFilter
		}

		return blobLimit;
	}

}
