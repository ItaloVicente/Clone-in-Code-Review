	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(UIText.RepositorySearchDialog_AddGitRepositories);
		setTitleImage(fImageCache.createImage(UIIcons.WIZBAN_IMPORT_REPO));
	}

	@Override
	protected void okPressed() {
		fResult = new HashSet<String>();
