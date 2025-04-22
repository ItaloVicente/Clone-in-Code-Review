		}
		String warning = new StringBuilder()
				.append(commitMessageComponent.getCommentChar()).append(' ')
				.append(UIText.StagingView_headCommitChanged).toString();
		String message = commitMessageComponent.getCommitMessage()
				.replace(warning, ""); //$NON-NLS-1$
		if (message.trim().isEmpty()) {
