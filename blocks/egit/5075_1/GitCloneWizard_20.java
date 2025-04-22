	public GitCloneWizard(String presetUri) {
		super(new RepositorySelectionPage(true, presetUri));
		initialize();
	}

	public GitCloneWizard(IRepositorySearchResult searchResult) {
		super(searchResult);
		initialize();
	}

	private void initialize() {
