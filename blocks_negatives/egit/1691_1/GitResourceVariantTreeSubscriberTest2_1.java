	private ObjectId findFileId(RevCommit commit, IFile mainJava)
			throws Exception {
		TreeWalk tw = new TreeWalk(repo);
		tw.reset();
		tw.setRecursive(true);
		String path = Repository.stripWorkDir(repo.getWorkTree(), mainJava
				.getLocation().toFile());
		tw.setFilter(PathFilter.create(path));
		int nth = tw.addTree(commit.getTree());
		tw.next();

		return tw.getObjectId(nth);
	}

