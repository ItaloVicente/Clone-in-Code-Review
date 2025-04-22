	/**
	 * @param repo
	 *            repository in with given files should be removed from index
	 * @param resources
	 *            list of resources that should be removed from index
	 * @deprecated use {@link #RemoveFromIndexOperation(Collection)} instead
	 */
	@Deprecated
	public RemoveFromIndexOperation(Repository repo, IResource[] resources) {
		this.pathsByRepository = ResourceUtil.splitResourcesByRepository(resources);
	}

