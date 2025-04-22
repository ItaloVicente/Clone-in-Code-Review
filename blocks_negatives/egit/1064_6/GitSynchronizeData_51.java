	/**
	 *
	 * @param resource
	 * @return <true> if given {@link IResource} is contained by this repository
	 */
	public boolean contains(IResource resource) {
		return resource.getFullPath().toString().startsWith(repoParentPath);
	}

