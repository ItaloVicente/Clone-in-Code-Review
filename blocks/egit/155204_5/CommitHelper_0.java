
		final CommitConfig commitConfig = repository.getConfig()
				.get(CommitConfig.KEY);
		commitTemplate = commitConfig.getCommitTemplatePath()
				.map(this::getTemplateContent);
	}

	private String getTemplateContent(String templatePath) {

		File commitTemplatePath = new File(templatePath);

		if (commitTemplatePath.exists()) {
			try {
				return RawParseUtils.decode(IO.readFully(commitTemplatePath));
			} catch (IOException e) {
				return e.getMessage();
			}
		}
		return null;
	}

	public boolean shouldUseCommitTemplate() {
		return getCommitMessage() == null
				&& getCommitTemplate().isPresent();
