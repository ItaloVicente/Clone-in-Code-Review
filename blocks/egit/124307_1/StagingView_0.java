	private void resetCommitMessageComponentOnAuthorChange() {
		if (currentRepository != null) {
			CommitHelper helper = new CommitHelper(currentRepository);
			String newAuthor = helper.getAuthor();
			String newCommitter = helper.getCommitter();
			asyncExec(() -> {
				String currentAuthor = commitMessageComponent.getAuthor();
				String currentCommitter = commitMessageComponent.getCommitter();
				if (!currentAuthor.equals(newAuthor)
						|| !(currentCommitter.equals(newCommitter))) {
					resetCommitMessageComponent();
				}
			});
		}
	}

