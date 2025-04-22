	private void showExceptionMessage(Exception e) {
		Activator.handleError(UIText.RepositoryPropertySource_ErrorHeader, e,
				true);
	}

	private enum DisplayMode {
		EFFECTIVE(UIText.RepositoryPropertySource_EffectiveConfigurationAction),
		USER(UIText.RepositoryPropertySource_GlobalConfigurationMenu),
		REPO(UIText.RepositoryPropertySource_RepositoryConfigurationButton);

		String getText() {
			return this.text;
