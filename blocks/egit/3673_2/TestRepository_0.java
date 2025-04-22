		DirCacheEntry dcEntry = dc.getEntry(repoPath);
		if (dcEntry == null)
			return false;

		Ref headRef = repository.getRef(HEAD);
		RevCommit commit = new RevWalk(repository).parseCommit(headRef.getObjectId());

		TreeWalk tw = TreeWalk.forPath(repository, repoPath, commit.getTree());
		return tw == null || !dcEntry.getObjectId().equals(tw.getObjectId(0));
