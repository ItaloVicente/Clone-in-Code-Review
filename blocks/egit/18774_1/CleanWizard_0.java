		final String repoName = Activator.getDefault().getRepositoryUtil()
				.getRepositoryName(repository);
		setWindowTitle(NLS.bind(UIText.CleanWizard_title, repoName));
	}

	@Override
	public void addPages() {
