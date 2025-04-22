	private AbstractTreeIterator resolveNewTree(RevWalk rw) throws IOException {
		if (cached) {
			return new DirCacheIterator(repo.readDirCache());
		} else if (newTree != null) {
			return newTree;
		} else if (newTreeish != null) {
			return getTreeIterator(rw
		} else {
			return new FileTreeIterator(repo);
		}
	}

	private static AbstractTreeIterator getTreeIterator(RevWalk rw
			throws IOException {
		RevTree tree = rw.parseTree(id);
		CanonicalTreeParser p = new CanonicalTreeParser();
		p.reset(rw.getObjectReader()
		return p;
	}

