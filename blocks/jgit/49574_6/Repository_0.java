		return findRef(name);
	}

	@Nullable
	public Ref exactRef(String name) throws IOException {
		return getRefDatabase().exactRef(name);
	}

	@Nullable
	public Ref findRef(String name) throws IOException {
