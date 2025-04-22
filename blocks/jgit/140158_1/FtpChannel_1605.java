
package org.eclipse.jgit.transport;

import java.text.MessageFormat;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.errors.PackProtocolException;
import org.eclipse.jgit.internal.JGitText;

public final class FilterSpec {

	private final long blobLimit;

	private FilterSpec(long blobLimit) {
		this.blobLimit = blobLimit;
	}

	public static FilterSpec fromFilterLine(String filterLine)
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

		return new FilterSpec(blobLimit);
	}

	static FilterSpec withBlobLimit(long blobLimit) {
		if (blobLimit < 0) {
			throw new IllegalArgumentException(
		}
		return new FilterSpec(blobLimit);
	}

	public static final FilterSpec NO_FILTER = new FilterSpec(-1);

	public long getBlobLimit() {
		return blobLimit;
	}

	public boolean isNoOp() {
		return blobLimit == -1;
	}

	@Nullable
	public String filterLine() {
		if (blobLimit == 0) {
		}

		if (blobLimit > 0) {
		}

		return null;
	}
}
