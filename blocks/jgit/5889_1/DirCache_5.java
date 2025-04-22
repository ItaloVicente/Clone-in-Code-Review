	void updateSmudgeEntries() throws IOException {
		TreeWalk walk = new TreeWalk(repository);
		try {
			DirCacheIterator iIter = new DirCacheIterator(this);
			FileTreeIterator fIter = new FileTreeIterator(repository);
			walk.addTree(iIter);
			walk.addTree(fIter);
			walk.setRecursive(true);
			while (walk.next()) {
				iIter = walk.getTree(0
				if (iIter == null)
					continue;
				fIter = walk.getTree(1
				if (fIter == null)
					continue;
				DirCacheEntry entry = iIter.getDirCacheEntry();
				if (entry.isSmudged() && iIter.idEqual(fIter)) {
					entry.setLength(fIter.getEntryLength());
					entry.setLastModified(fIter.getEntryLastModified());
				}
			}
		} finally {
			walk.release();
		}
	}

