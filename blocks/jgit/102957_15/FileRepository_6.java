
	@Override
	public RepositoryShallow getRepositoryShallowHandler() {
		return new FileBasedShallow(this.getDirectory());
	}

