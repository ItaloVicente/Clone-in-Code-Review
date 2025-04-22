
		CommitConfig commitConfig = repository.getConfig()
				.get(CommitConfig.KEY);
		commitTemplate = commitConfig.getCommitTemplateContent();
	}

	public boolean shouldUseCommitTemplate() {
		return getCommitMessage() == null
				&& getCommitTemplate().isPresent();
