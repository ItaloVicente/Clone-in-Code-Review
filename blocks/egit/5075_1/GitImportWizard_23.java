	protected void addPreClonePages() {
		if (!hasSearchResult())
			addPage(selectRepoPage);
	}

	@Override
	protected void addPostClonePages() {
