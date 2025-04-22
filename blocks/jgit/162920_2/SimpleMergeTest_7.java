
	private String linkId1 = "DEADBEEFDEADBEEFBABEDEADBEEFDEADBEEFBABE";
	private String linkId2 = "DEADDEADDEADDEADDEADDEADDEADDEADDEADDEAD";
	private String linkId3 = "BEEFBEEFBEEFBEEFBEEFBEEFBEEFBEEFBEEFBEEF";

	private String SUBMODULE_PATH = "submodule";

	@Test
	public void testGitLinkMerging_AddNew() throws Exception {
		assertGitLinkValue(testGitLink(null
	}


	@Test
	public void testGitLinkMerging_Delete() throws Exception {
		assertGitLinkDoesntExist(testGitLink(linkId1
	}

	@Test
	public void testGitLinkMerging_UpdateDelete() throws Exception {
		assertGitLinkDoesntExist(testGitLink(linkId1
	}

	@Test
	public void testGitLinkMerging_DeleteUpdate() throws Exception {
		assertGitLinkDoesntExist(testGitLink(linkId1
	}

	@Test
	public void testGitLinkMerging_UpdateUpdate() throws Exception {
		assertGitLinkDoesntExist(testGitLink(linkId1
	}

	@Test
	public void testGitLinkMerging_bothAddedSameLink() throws Exception {
		assertGitLinkValue(testGitLink(null
	}

	@Test
	public void testGitLinkMerging_bothAddedDifferentLink() throws Exception {
		assertGitLinkDoesntExist(testGitLink(null
	}

	protected Merger testGitLink(@Nullable String baseLink
			@Nullable String oursLink
			boolean shouldMerge)
			throws Exception {
		DirCache treeB = db.readDirCache();
		DirCache treeO = db.readDirCache();
		DirCache treeT = db.readDirCache();

		DirCacheBuilder bTreeBuilder = treeB.builder();
		DirCacheBuilder oTreeBuilder = treeO.builder();
		DirCacheBuilder tTreeBuilder = treeT.builder();

		maybeAddLink(bTreeBuilder
		maybeAddLink(oTreeBuilder
		maybeAddLink(tTreeBuilder

		bTreeBuilder.finish();
		oTreeBuilder.finish();
		tTreeBuilder.finish();

		ObjectInserter ow = db.newObjectInserter();
		ObjectId b = commit(ow
		ObjectId o = commit(ow
		ObjectId t = commit(ow

		Merger resolveMerger = MergeStrategy.RESOLVE.newMerger(db
		boolean merge = resolveMerger.merge(new ObjectId[] { o
		assertEquals(shouldMerge

		return resolveMerger;
	}

	@Test
	public void testGitLinkMerging_blobWithLink() throws Exception {
		DirCache treeB = db.readDirCache();
		DirCache treeO = db.readDirCache();
		DirCache treeT = db.readDirCache();

		DirCacheBuilder bTreeBuilder = treeB.builder();
		DirCacheBuilder oTreeBuilder = treeO.builder();
		DirCacheBuilder tTreeBuilder = treeT.builder();

		bTreeBuilder.add(
				createEntry(SUBMODULE_PATH
		oTreeBuilder.add(
				createEntry(SUBMODULE_PATH

		maybeAddLink(tTreeBuilder

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

	@Test
	public void testGitLinkMerging_linkWithBlob() throws Exception {
		DirCache treeB = db.readDirCache();
		DirCache treeO = db.readDirCache();
		DirCache treeT = db.readDirCache();

		DirCacheBuilder bTreeBuilder = treeB.builder();
		DirCacheBuilder oTreeBuilder = treeO.builder();
		DirCacheBuilder tTreeBuilder = treeT.builder();

		maybeAddLink(bTreeBuilder
		maybeAddLink(oTreeBuilder
		tTreeBuilder.add(
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

	@Test
	public void testGitLinkMerging_linkWithLink() throws Exception {
		DirCache treeB = db.readDirCache();
		DirCache treeO = db.readDirCache();
		DirCache treeT = db.readDirCache();

		DirCacheBuilder bTreeBuilder = treeB.builder();
		DirCacheBuilder oTreeBuilder = treeO.builder();
		DirCacheBuilder tTreeBuilder = treeT.builder();

		bTreeBuilder.add(
				createEntry(SUBMODULE_PATH
		maybeAddLink(oTreeBuilder
		maybeAddLink(tTreeBuilder

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

	@Test
	public void testGitLinkMerging_blobWithBlobFromLink() throws Exception {
		DirCache treeB = db.readDirCache();
		DirCache treeO = db.readDirCache();
		DirCache treeT = db.readDirCache();

		DirCacheBuilder bTreeBuilder = treeB.builder();
		DirCacheBuilder oTreeBuilder = treeO.builder();
		DirCacheBuilder tTreeBuilder = treeT.builder();

		maybeAddLink(bTreeBuilder
		oTreeBuilder.add(
				createEntry(SUBMODULE_PATH
		tTreeBuilder.add(
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

	@Test
	public void testGitLinkMerging_linkBlobDeleted() throws Exception {
		DirCache treeB = db.readDirCache();
		DirCache treeO = db.readDirCache();
		DirCache treeT = db.readDirCache();

		DirCacheBuilder bTreeBuilder = treeB.builder();
		DirCacheBuilder oTreeBuilder = treeO.builder();
		DirCacheBuilder tTreeBuilder = treeT.builder();

		maybeAddLink(bTreeBuilder
		oTreeBuilder.add(createEntry(SUBMODULE_PATH

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

	private void maybeAddLink(DirCacheBuilder builder
			@Nullable String linkId) {
		if (linkId == null) {
			return;
		}
		DirCacheEntry newLink = createGitLink(SUBMODULE_PATH
				ObjectId.fromString(linkId));
		builder.add(newLink);
	}

	private void assertGitLinkValue(Merger resolveMerger
			throws Exception {
		try (TreeWalk tw = new TreeWalk(db)) {
			tw.setRecursive(true);
			tw.reset(resolveMerger.getResultTreeId());

			assertTrue(tw.next());
			assertEquals(SUBMODULE_PATH
			assertEquals(ObjectId.fromString(shouldChoose)

			assertFalse(tw.next());
		}
	}

	private void assertGitLinkDoesntExist(Merger resolveMerger)
			throws Exception {
		try (TreeWalk tw = new TreeWalk(db)) {
			tw.setRecursive(true);
			tw.reset(resolveMerger.getResultTreeId());

			assertFalse(tw.next());
		}
	}
