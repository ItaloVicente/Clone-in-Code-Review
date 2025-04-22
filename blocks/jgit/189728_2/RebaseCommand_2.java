		String commitMessage;
		if (!isLast || sequenceContainsSquash) {
			commitMessage = rebaseState.readFile(MESSAGE_SQUASH);
		} else {
			commitMessage = rebaseState.readFile(MESSAGE_FIXUP);
		}
