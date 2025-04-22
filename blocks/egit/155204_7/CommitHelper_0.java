
		CommitConfig commitConfig = repository.getConfig()
				.get(CommitConfig.KEY);
		try {
			commitTemplate = commitConfig.getCommitTemplateContent();
		} catch (IOException e) {
			Activator.handleError(UIText.CommitAction_CommitTemplateFailed, e,
					true);
		}
	}

	public boolean shouldUseCommitTemplate() {
		return getCommitMessage() == null
				&& getCommitTemplate() != null;
