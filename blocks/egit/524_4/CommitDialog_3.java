	private void saveOriginalChangeId() {
		int oldPos = findOffsetOfChangeIdLine();
		if (oldPos > 0) {
			int oldPosEol = previousCommitMessage.indexOf(Text.DELIMITER, oldPos + Text.DELIMITER.length());
			int sha1Offset = oldPos + Text.DELIMITER.length() + "Change-Id: I".length(); //$NON-NLS-1$
			try {
				originalChangeId = ObjectId.fromString(previousCommitMessage.substring(sha1Offset, oldPosEol));
			} catch (IllegalArgumentException e) {
				originalChangeId = null;
			}
		} else
			originalChangeId = null;
	}

	private int findOffsetOfChangeIdLine() {
		int oldPos = previousCommitMessage.indexOf(Text.DELIMITER + "Change-Id: I"); //$NON-NLS-1$
		return oldPos;
	}

