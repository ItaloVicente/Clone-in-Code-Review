		CommitMessageWithCaretPosition commitMessageWithCaretPosition = new CommitMessageCalculator(
				repository, filesToCommit).calculateCommitMessageAndCaretPosition();

		String calculatedCommitMessage = calculateCommitMessage(
				commitMessageWithCaretPosition);
		int calculatedCaretPosition = calculateCaretPosition(
				commitMessageWithCaretPosition);
