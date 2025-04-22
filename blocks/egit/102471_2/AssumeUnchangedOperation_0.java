		locations = Collections.emptyList();
		caches = new IdentityHashMap<Repository, DirCache>();
		this.assumeUnchanged = assumeUnchanged;
	}

	public AssumeUnchangedOperation(final Repository repository,
			final Collection<IPath> locations,
			boolean assumeUnchanged) {
		this.db = repository;
		this.locations = locations;
		this.rsrcList = Collections.emptyList();
