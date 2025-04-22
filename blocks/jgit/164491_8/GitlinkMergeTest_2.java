	@Test
	public void testGitLinkMerging_linkBlobDeleted() throws Exception {
		DirCache treeB = db.readDirCache();
		DirCache treeO = db.readDirCache();
		DirCache treeT = db.readDirCache();

		DirCacheBuilder bTreeBuilder = treeB.builder();
		DirCacheBuilder oTreeBuilder = treeO.builder();
		DirCacheBuilder tTreeBuilder = treeT.builder();

		maybeAddLink(bTreeBuilder
		oTreeBuilder.add(
				createEntry(SUBMODULE_PATH

		bTreeBuilder.finish();
		oTreeBuilder.finish();
		tTreeBuilder.finish();

		ObjectInserter ow = db.newObjectInserter();
		ObjectId b = commit(ow
		ObjectId o = commit(ow
		ObjectId t = commit(ow

		Merger resolveMerger = MergeStrategy.RESOLVE.newMerger(db);
		boolean merge = resolveMerger.merge(new ObjectId[] { o
		assertFalse(merge);
	}

