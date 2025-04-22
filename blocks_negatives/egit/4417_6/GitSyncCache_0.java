		SubMonitor m = SubMonitor.convert(monitor, gsds.size());
		for (GitSynchronizeData gsd : gsds) {
			Repository repo = gsd.getRepository();
			ObjectId baseTree = getTree(gsd.getSrcRevCommit());
			ObjectId remoteTree = getTree(gsd.getDstRevCommit());
			GitSyncObjectCache repoCache = cache
					.put(repo, baseTree, remoteTree);
