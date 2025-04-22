	public URI resolve(URI base)
	{
		return resolve(base, true);
	}

	public URI resolve(URI base, boolean preserveRootParents)
	{
		if (!base.isHierarchical() || base.isRelative())
		{
			throw new IllegalArgumentException(
				"resolve against non-hierarchical or relative base");
		}

		if (!isRelative()) {
			return this;
		}
