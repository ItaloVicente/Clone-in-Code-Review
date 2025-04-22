	public RepositorySearchDialog(Collection<String> existingDirs) {
		this(existingDirs, false);
	}

	public RepositorySearchDialog(Collection<String> existingDirs,
			boolean fillSearch) {
		super(
				"searchPage", UIText.RepositorySearchDialog_SearchTitle, UIIcons.WIZBAN_IMPORT_REPO); //$NON-NLS-1$
