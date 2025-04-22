
	private String linkId1 = "DEADBEEFDEADBEEFBABEDEADBEEFDEADBEEFBABE";
	private String linkId2 = "DEADDEADDEADDEADDEADDEADDEADDEADDEADDEAD";
	private String linkId3 = "BEEFBEEFBEEFBEEFBEEFBEEFBEEFBEEFBEEFBEEF";

	private String SUBMODULE_PATH = "submodule";

	@Test
	public void testGitLinkMerging_AddNew() throws Exception {
		testGitLink(null
	}


	@Test
	public void testGitLinkMerging_Delete() throws Exception {
		testGitLink(linkId1
	}

	@Test
	public void testGitLinkMerging_UpdateDelete() throws Exception {
		testGitLink(linkId1
	}

	@Test
	public void testGitLinkMerging_DeleteUpdate() throws Exception {
		testGitLink(linkId1
	}

	@Test
	public void testGitLinkMerging_UpdateUpdate() throws Exception {
		testGitLink(linkId1
	}

	@Test
	public void testGitLinkMerging_bothAddedSameLink() throws Exception {
		testGitLink(null
	}

	@Test
	public void testGitLinkMerging_bothAddedDifferentLink() throws Exception {
		testGitLink(null
	}

	protected void testGitLink(@Nullable String baseLink
			@Nullable String oursLink
			boolean shouldMerge
			throws Exception {
		final DirCache treeB = db.readDirCache();
		final DirCache treeO = db.readDirCache();
		final DirCache treeT = db.readDirCache();
		{
			final DirCacheBuilder b = treeB.builder();
			final DirCacheBuilder o = treeO.builder();
			final DirCacheBuilder t = treeT.builder();

			maybeAddLink(b
			maybeAddLink(o
			maybeAddLink(t

			b.finish();
			o.finish();
			t.finish();
		}

		final ObjectInserter ow = db.newObjectInserter();
		final ObjectId b = commit(ow
		final ObjectId o = commit(ow
		final ObjectId t = commit(ow

		Merger resolveMerger = MergeStrategy.RESOLVE.newMerger(db
		boolean merge = resolveMerger.merge(new ObjectId[] { o
		assertEquals(shouldMerge

		if (shouldMerge) {
			try (TreeWalk tw = new TreeWalk(db)) {
				tw.setRecursive(true);
				tw.reset(resolveMerger.getResultTreeId());

				if (shouldChoose != null) {
					assertTrue(tw.next());
					assertEquals(SUBMODULE_PATH
					assertEquals(ObjectId.fromString(shouldChoose)
							tw.getObjectId(0));
				}

				assertFalse(tw.next());
			}
		}
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

	@Test
	public void testGitLinkMerging_blobWithLink() throws Exception {
		final DirCache treeB = db.readDirCache();
		final DirCache treeO = db.readDirCache();
		final DirCache treeT = db.readDirCache();
		{
			final DirCacheBuilder b = treeB.builder();
			final DirCacheBuilder o = treeO.builder();
			final DirCacheBuilder t = treeT.builder();

			b.add(createEntry(SUBMODULE_PATH
			o.add(createEntry(SUBMODULE_PATH

			maybeAddLink(t

			b.finish();
			o.finish();
			t.finish();
		}

		final ObjectInserter ow = db.newObjectInserter();
		final ObjectId b = commit(ow
		final ObjectId o = commit(ow
		final ObjectId t = commit(ow

		Merger resolveMerger = MergeStrategy.RESOLVE.newMerger(db);
		boolean merge = resolveMerger.merge(new ObjectId[] { o
		assertFalse(merge);
	}

	@Test
	public void testGitLinkMerging_linkWithBlob() throws Exception {
		final DirCache treeB = db.readDirCache();
		final DirCache treeO = db.readDirCache();
		final DirCache treeT = db.readDirCache();
		{
			final DirCacheBuilder b = treeB.builder();
			final DirCacheBuilder o = treeO.builder();
			final DirCacheBuilder t = treeT.builder();

			maybeAddLink(b
			maybeAddLink(o
			t.add(createEntry(SUBMODULE_PATH

			b.finish();
			o.finish();
			t.finish();
		}

		final ObjectInserter ow = db.newObjectInserter();
		final ObjectId b = commit(ow
		final ObjectId o = commit(ow
		final ObjectId t = commit(ow

		Merger resolveMerger = MergeStrategy.RESOLVE.newMerger(db);
		boolean merge = resolveMerger.merge(new ObjectId[] { o
		assertFalse(merge);
	}

	@Test
	public void testGitLinkMerging_linkWithLink() throws Exception {
		final DirCache treeB = db.readDirCache();
		final DirCache treeO = db.readDirCache();
		final DirCache treeT = db.readDirCache();
		{
			final DirCacheBuilder b = treeB.builder();
			final DirCacheBuilder o = treeO.builder();
			final DirCacheBuilder t = treeT.builder();

			b.add(createEntry(SUBMODULE_PATH
			maybeAddLink(o
			maybeAddLink(t

			b.finish();
			o.finish();
			t.finish();
		}

		final ObjectInserter ow = db.newObjectInserter();
		final ObjectId b = commit(ow
		final ObjectId o = commit(ow
		final ObjectId t = commit(ow

		Merger resolveMerger = MergeStrategy.RESOLVE.newMerger(db);
		boolean merge = resolveMerger.merge(new ObjectId[] { o
		assertFalse(merge);
	}

	@Test
	public void testGitLinkMerging_blobWithBlobFromLink() throws Exception {
		final DirCache treeB = db.readDirCache();
		final DirCache treeO = db.readDirCache();
		final DirCache treeT = db.readDirCache();
		{
			final DirCacheBuilder b = treeB.builder();
			final DirCacheBuilder o = treeO.builder();
			final DirCacheBuilder t = treeT.builder();

			maybeAddLink(b
			o.add(createEntry(SUBMODULE_PATH
			t.add(createEntry(SUBMODULE_PATH

			b.finish();
			o.finish();
			t.finish();
		}

		final ObjectInserter ow = db.newObjectInserter();
		final ObjectId b = commit(ow
		final ObjectId o = commit(ow
		final ObjectId t = commit(ow

		Merger resolveMerger = MergeStrategy.RESOLVE.newMerger(db);
		boolean merge = resolveMerger.merge(new ObjectId[] { o
		assertFalse(merge);
	}

	@Test
	public void testGitLinkMerging_linkBlobDeleted() throws Exception {
		final DirCache treeB = db.readDirCache();
		final DirCache treeO = db.readDirCache();
		final DirCache treeT = db.readDirCache();
		{
			final DirCacheBuilder b = treeB.builder();
			final DirCacheBuilder o = treeO.builder();
			final DirCacheBuilder t = treeT.builder();

			maybeAddLink(b
			o.add(createEntry(SUBMODULE_PATH

			b.finish();
			o.finish();
			t.finish();
		}

		final ObjectInserter ow = db.newObjectInserter();
		final ObjectId b = commit(ow
		final ObjectId o = commit(ow
		final ObjectId t = commit(ow

		Merger resolveMerger = MergeStrategy.RESOLVE.newMerger(db);
		boolean merge = resolveMerger.merge(new ObjectId[] { o
		assertFalse(merge);
	}
