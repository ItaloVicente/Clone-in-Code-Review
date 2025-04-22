	private FileTreeEntry getBlob(IResource resource, GitSynchronizeData gsd)
			throws TeamException {
		FileTreeEntry fileEntry;
		Repository repo = gsd.getRepository();

		try {
			Tree tree = repo.mapTree(getBaseRevCommit(gsd).getTree().getId());
			String resourcePath = getResourcePath(resource, repo);
