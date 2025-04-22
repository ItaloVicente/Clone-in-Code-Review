	private void saveOriginalChangeId() {
		int changeIdOffset = findOffsetOfChangeIdLine(previousCommitMessage);
		if (changeIdOffset > 0) {
			int endOfChangeId = findNextEOL(changeIdOffset, previousCommitMessage);
			int sha1Offset = changeIdOffset + Text.DELIMITER.length() + "Change-Id: I".length(); //$NON-NLS-1$
			try {
				originalChangeId = ObjectId.fromString(previousCommitMessage.substring(sha1Offset, endOfChangeId));
			} catch (IllegalArgumentException e) {
				originalChangeId = null;
			}
		} else
			originalChangeId = null;
	}

	private int findNextEOL(int oldPos, String message) {
		return message.indexOf(Text.DELIMITER, oldPos + Text.DELIMITER.length());
	}

	private int findOffsetOfChangeIdLine(String message) {
		return message.indexOf(Text.DELIMITER + "Change-Id: I"); //$NON-NLS-1$
	}

