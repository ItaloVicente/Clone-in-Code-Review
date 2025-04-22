	public HistoryPageInput(final Repository repository,
			final IResource[] resourceItems) {
		this.repo = repository;
		list = resourceItems;
		files = null;
	}

	public HistoryPageInput(final Repository repository, final File[] fileItems) {
		this.repo = repository;
		list = null;
		files = fileItems;
	}

