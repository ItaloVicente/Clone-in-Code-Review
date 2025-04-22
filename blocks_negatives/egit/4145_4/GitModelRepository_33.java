		RevWalk rw = new RevWalk(repo);
		rw.setRetainBody(true);
		if (pathFilter != null)
			rw.setTreeFilter(pathFilter);

		try {
			RevCommit srcCommit = rw.parseCommit(srcRev);

			if (includeLocal) {
				GitModelCache gitCache = new GitModelCache(this, srcCommit,
						pathFilter);
				int gitCacheLen = gitCache.getChildren().length;
