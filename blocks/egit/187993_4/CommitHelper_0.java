
	}

	public boolean shouldUseCommitTemplate() {
		return StringUtils.isEmptyOrNull(getCommitMessage())
				&& getCommitTemplate() != null;
	}

	public String getCommitTemplate() {
		if (commitTemplate == null && repository != null) {
			CommitConfig commitConfig = repository.getConfig()
					.get(CommitConfig.KEY);
			try {
				commitTemplate = commitConfig
						.getCommitTemplateContent(repository);
			} catch (IOException | ConfigInvalidException e) {
				Activator.handleError(UIText.CommitAction_CommitTemplateFailed,
						e, true);
			}
		}

		return commitTemplate;
