	private GitTreeTraversal(Repository repo, RevCommit commit, IPath path) {
		super(getResourcesImpl(repo, commit, path), IResource.DEPTH_INFINITE,
				IResource.NONE);
	}

	private static IResource[] getResourcesImpl(Repository repo,
			RevCommit commit, IPath path) {
		AnyObjectId baseId;
		RevCommit[] parents = commit.getParents();
		if (parents.length > 0)
			baseId = parents[0].getTree().getId();
		else
			baseId = zeroId();

		AnyObjectId remoteId = commit.getTree().getId();

		return getResourcesImpl(repo, baseId, remoteId, path);
	}

	private static IResource[] getResourcesImpl(Repository repo,
			AnyObjectId baseId, AnyObjectId remoteId, IPath path) {
