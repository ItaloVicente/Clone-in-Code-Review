		CommitMessageWithCaretPosition commitMessageWithCaretPosition = new CommitMessageBuilder(
				repository, filesToCommit).build();

		String calculatedCommitMessage = calculateCommitMessage(
				commitMessageWithCaretPosition);
		int calculatedCaretPosition = calculateCaretPosition(
				commitMessageWithCaretPosition);
