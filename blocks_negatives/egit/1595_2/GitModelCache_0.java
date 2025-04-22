			TreeWalk tw = createTreeWalk();
			tw.setRecursive(true);
			tw.addTree(baseCommit.getTree());

			Repository repo = getRepository();
			DirCache index = repo.readDirCache();
			ObjectId headId = repo.getRef(Constants.HEAD).getObjectId();
			int cacheNth = tw.addTree(new DirCacheIterator(index));
			int repoNth = tw.addTree(new RevWalk(repo).parseTree(headId));
