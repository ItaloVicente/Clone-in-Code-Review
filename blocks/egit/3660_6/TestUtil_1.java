package org.eclipse.egit.ui.test;

import org.eclipse.swt.widgets.Text;

public class CommitMessageUtil {

	public static String extractChangeId(String commitMessage) {
		int changeIdOffset = findOffsetOfChangeIdLine(commitMessage);
		if (changeIdOffset <= 0)
			return null;
		int endOfChangeId = findNextEOL(changeIdOffset, commitMessage);
		return commitMessage.substring(
				changeIdOffset + Text.DELIMITER.length(), endOfChangeId);
	}

	private static int findNextEOL(int oldPos, String message) {
		return message.indexOf(Text.DELIMITER, oldPos + 1);
	}

	private static int findOffsetOfChangeIdLine(String message) {
		return message.indexOf(Text.DELIMITER + "Change-Id: I"); //$NON-NLS-1$
	}

}
