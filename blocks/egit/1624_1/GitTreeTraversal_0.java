	private static IResource[] getResourcesImpl(Repository repo,
			RevCommit revCommit, IPath path) {
		ObjectId remoteId;
		RevCommit[] parents = revCommit.getParents();
		if (parents.length > 0)
			remoteId = revCommit.getParent(0).getTree().getId();
		else
