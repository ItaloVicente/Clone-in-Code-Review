	public Ref exactRef(String name) throws IOException {
		return getRefDatabase().exactRef(name);
	}

	public Ref findRef(String name) throws IOException {
		return getRefDatabase().findRef(name);
	}

