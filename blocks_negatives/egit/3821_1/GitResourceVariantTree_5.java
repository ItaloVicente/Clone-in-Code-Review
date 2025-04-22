	/**
	 *
	 * @param gsd
	 * @return instance of {@link RevTree} for given {@link GitSynchronizeData}
	 *         or <code>null</code> if rev tree was not found
	 * @throws TeamException
	 */
	protected abstract RevCommit getRevCommit(GitSynchronizeData gsd)
			throws TeamException;

	private IResourceVariant handleRepositoryRoot(final IResource resource,
			Repository repo, RevCommit revCommit) /*throws TeamException*/ {
		String path = RepositoryMapping.findRepositoryMapping(repo).getRepoRelativePath(resource);
			return new GitRemoteFolder(repo, revCommit, revCommit.getTree(), path);
	}

	private TreeWalk initializeTreeWalk(Repository repo, String path) {
		TreeWalk tw = new TreeWalk(repo);
		tw.reset();

		tw.setFilter(PathFilter.create(path));

		return tw;
	}

