
	public String opaquePart()
	{
		return isHierarchical() ? null : authority;
	}

	public String authority()
	{
		return isHierarchical() ? authority : null;
