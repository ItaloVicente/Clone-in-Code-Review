		IPath location = resource.getLocation();
		if (location == null)
			return null;
		return getRepository(location);
	}

	public Repository getRepository(final IPath location) {
