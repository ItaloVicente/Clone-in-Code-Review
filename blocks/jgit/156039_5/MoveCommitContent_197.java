package org.eclipse.jgit.niofs.internal.op.model;

import java.util.Date;
import java.util.TimeZone;

public class MessageCommitInfo extends CommitInfo {

	public static final String MERGE_MESSAGE = "Merge branch '%s'";
	public static final String REVERT_MERGE_MESSAGE = "Revert merge from branch '%s'";
	public static final String FIX_REVERT_MERGE_MESSAGE = "Fix after merge reversion";

	public MessageCommitInfo(final String message) {
		this(null
	}

	private MessageCommitInfo(final String sessionId
			final TimeZone timeZone
		super(sessionId
	}

	public static MessageCommitInfo createMergeMessage(final String sourceBranch) {
		return new MessageCommitInfo(String.format(MERGE_MESSAGE
	}

	public static MessageCommitInfo createRevertMergeMessage(final String sourceBranch) {
		return new MessageCommitInfo(String.format(REVERT_MERGE_MESSAGE
	}

	public static MessageCommitInfo createFixMergeReversionMessage() {
		return new MessageCommitInfo(FIX_REVERT_MERGE_MESSAGE);
	}
}
