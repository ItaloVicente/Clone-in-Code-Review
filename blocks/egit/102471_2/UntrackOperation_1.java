		locations = Collections.emptyList();
		edits = new IdentityHashMap<Repository, DirCacheEditor>();
	}

	public UntrackOperation(final Repository repository,
			final Collection<IPath> locations) {
		rsrcList = Collections.emptyList();
		this.locations = locations;
		this.db = repository;
