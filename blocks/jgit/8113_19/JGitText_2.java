
package org.eclipse.jgit.errors;

import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.merge.RecursiveMerger;

public class NoMergeBaseException extends IOException {
	private static final long serialVersionUID = 1L;

	private MergeBaseFailureReason reason;

	public static enum MergeBaseFailureReason {
		MULTIPLE_MERGE_BASES_NOT_SUPPORTED

		TO_MANY_MERGE_BASES

		CONFLICTS_DURING_MERGE_BASE_CALCULATION
	}


	public NoMergeBaseException(MergeBaseFailureReason reason
		super(MessageFormat.format(JGitText.get().noMergeBase
				reason.toString()
		this.reason = reason;
	}

	public NoMergeBaseException(MergeBaseFailureReason reason
			Throwable why) {
		super(MessageFormat.format(JGitText.get().noMergeBase
				reason.toString()
		this.reason = reason;
		initCause(why);
	}

	public MergeBaseFailureReason getReason() {
		return reason;
	}
}
