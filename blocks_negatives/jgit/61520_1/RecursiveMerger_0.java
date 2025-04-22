
	/**
	 * Create a new in memory dircache which has the same content as a given
	 * tree.
	 *
	 * @param treeId
	 *            the tree which should be used to fill the dircache
	 * @return a new in memory dircache
	 * @throws IOException
	 */
	private DirCache dircacheFromTree(ObjectId treeId) throws IOException {
		DirCache ret = DirCache.newInCore();
		DirCacheBuilder aBuilder = ret.builder();
		try (TreeWalk atw = new TreeWalk(reader)) {
			atw.addTree(treeId);
			atw.setRecursive(true);
			while (atw.next()) {
				DirCacheEntry e = new DirCacheEntry(atw.getRawPath());
				e.setFileMode(atw.getFileMode(0));
				e.setObjectId(atw.getObjectId(0));
				aBuilder.add(e);
			}
		}
		aBuilder.finish();
		return ret;
	}
