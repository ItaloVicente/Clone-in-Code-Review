		RevWalk rw = new RevWalk(repo);
		rw.setRetainBody(true);
		if (pathFilter != null)
			rw.setTreeFilter(pathFilter);

		try {
			RevCommit srcCommit = rw.parseCommit(srcRev);
