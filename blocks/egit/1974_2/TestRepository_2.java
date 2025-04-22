		ObjectId headId = repository.resolve(Constants.HEAD);
		RevWalk rw = new RevWalk(repository);
		TreeWalk tw = null;
		try {
			tw = TreeWalk.forPath(repository, path, rw.parseTree(headId));
			return tw != null;
		} finally {
			rw.release();
			rw.dispose();
			if (tw != null)
				tw.release();
		}
