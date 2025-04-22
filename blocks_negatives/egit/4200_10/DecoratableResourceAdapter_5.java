	/**
	 * Adds a filter to the specified tree walk limiting the results to only
	 * those matching the resource specified by <code>resourceToFilterBy</code>
	 * <p>
	 * If the resource does not exists in the current repository, no filter is
	 * added and the method returns <code>false</code>. If the resource is a
	 * project, no filter is added, but the operation is considered a success.
	 *
	 * @param treeWalk
	 *            the tree walk to add the filter to
	 * @param resourceToFilterBy
	 *            the resource to filter by
	 *
	 * @return <code>true</code> if the filter could be added,
	 *         <code>false</code> otherwise
	 */
	private boolean addResourceFilter(final TreeWalk treeWalk,
			final IResource resourceToFilterBy) {
		String repoRelativePath = mapping
						.getRepoRelativePath(resourceToFilterBy);
		if (repoRelativePath==null)
			return false;
		Set<String> repositoryPaths = Collections.singleton(repoRelativePath);
		if (repositoryPaths.isEmpty())
			return false;
