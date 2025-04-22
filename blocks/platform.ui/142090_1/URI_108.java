	private boolean hasCollapsableSegments(boolean preserveRootParents)
	{
		if (hasRelativePath())
		{
			throw new IllegalStateException("test collapsability of relative path");
		}
