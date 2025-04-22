	private static GitSyncCache getAllData(GitSynchronizeData gsd,
			Collection<String> paths) {
		GitSyncCache cache = new GitSyncCache();
		TreeFilter filter = paths.isEmpty() ? null : createPathFilter(paths);

		Repository repo = gsd.getRepository();
		ObjectId baseTree = getTree(gsd.getSrcRevCommit());
		ObjectId remoteTree = getTree(gsd.getDstRevCommit());
		GitSyncObjectCache repoCache = cache.put(repo, baseTree, remoteTree);

		TreeFilter gsdFilter = gsd.getPathFilter();
		if (filter == null)
			loadDataFromGit(gsd, gsdFilter, repoCache);
		else if (gsdFilter == null)
			loadDataFromGit(gsd, filter, repoCache);
		else
			loadDataFromGit(gsd, AndTreeFilter.create(filter, gsdFilter),
					repoCache);
