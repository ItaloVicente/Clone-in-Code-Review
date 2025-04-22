	private void updateCommitAuthorAndCommitter(Repository repository) {
		CommitHelper helper = new CommitHelper(repository);
		asyncExec(() -> {
			boolean authorEqualsCommitter = commitMessageComponent.getAuthor()
					.equals(commitMessageComponent.getCommitter());
			if (authorEqualsCommitter) {
				commitMessageComponent.setAuthor(helper.getAuthor());
			}
			commitMessageComponent.setCommitter(helper.getCommitter());
			commitMessageComponent.updateUIFromState(false);
		});
	}

