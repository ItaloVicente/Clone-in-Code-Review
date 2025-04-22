	private void saveOriginalChangeId() {
		int changeIdOffset = findOffsetOfChangeIdLine(previousCommitMessage);
		if (changeIdOffset > 0) {
			int endOfChangeId = findNextEOL(changeIdOffset, previousCommitMessage);
			int sha1Offset = changeIdOffset + "\nChange-Id: I".length(); //$NON-NLS-1$
			try {
				originalChangeId = ObjectId.fromString(previousCommitMessage.substring(sha1Offset, endOfChangeId));
			} catch (IllegalArgumentException e) {
				originalChangeId = null;
			}
		} else
			originalChangeId = null;
	}

	private int findNextEOL(int oldPos, String message) {
		return message.indexOf("\n", oldPos + 1); //$NON-NLS-1$
	}

	private int findOffsetOfChangeIdLine(String message) {
		return message.indexOf("\nChange-Id: I"); //$NON-NLS-1$
	}

