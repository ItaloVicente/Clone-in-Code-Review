		return getDirCacheEntry(path) != null;
	}

	public boolean removedFromIndex(String path) throws IOException {
		DirCacheEntry dc = getDirCacheEntry(path);
		if (dc == null)
			return true;

		Ref ref = repository.getRef(Constants.HEAD);
		RevCommit c = new RevWalk(repository).parseCommit(ref.getObjectId());
		TreeWalk tw = TreeWalk.forPath(repository, path, c.getTree());
