
	private void rewordCommitMessage(Shell activeShell,
			final GitFlowRepository gfRepo) throws CoreException, IOException {
		Repository repository = gfRepo.getRepository();
		CommitHelper commitHelper = new CommitHelper(repository);

		CommitMessageEditorDialog messageEditorDialog = new CommitMessageEditorDialog(
				activeShell, repository.readSquashCommitMsg(),
				UIText.FeatureFinishHandler_rewordSquashedCommitMessage);

		if (Window.OK == messageEditorDialog.open()) {
			String commitMessage = stripCommentLines(messageEditorDialog
					.getCommitMessage());
			CommitOperation commitOperation = new CommitOperation(repository,
					commitHelper.getAuthor(), commitHelper.getCommitter(),
					commitMessage);
			commitOperation.execute(null);
		}
	}

	private static String stripCommentLines(String commitMessage) {
		StringBuilder result = new StringBuilder();
		for (String line : commitMessage.split("\n")) { //$NON-NLS-1$
			if (!line.trim().startsWith("#")) //$NON-NLS-1$
				result.append(line).append("\n"); //$NON-NLS-1$
		}
		if (!commitMessage.endsWith("\n")) //$NON-NLS-1$
			result.deleteCharAt(result.length() - 1);
		return result.toString();
	}
