		String repoName = Activator.getDefault().getRepositoryUtil()
				.getRepositoryName(repo);
		return NLS.bind(UIText.ResetTargetSelectionDialog_ResetTitle, repoName);
	}

	@Override
	protected String getWindowTitle() {
		return UIText.ResetTargetSelectionDialog_WindowTitle;
