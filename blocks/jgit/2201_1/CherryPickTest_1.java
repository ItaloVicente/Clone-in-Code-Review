	@Test
	public void testRevert() throws Exception {
		final DirCache treeB = db.readDirCache();
		final DirCache treeP = db.readDirCache();
		final DirCache treeT = db.readDirCache();
		{
			final DirCacheBuilder b = treeB.builder();
			final DirCacheBuilder p = treeP.builder();
			final DirCacheBuilder t = treeT.builder();

			b.add(makeEntry("a"

			p.add(makeEntry("a"
			p.add(makeEntry("p-fail"

			t.add(makeEntry("a"
			t.add(makeEntry("p-fail"
			t.add(makeEntry("t"

			b.finish();
			p.finish();
			t.finish();
		}

		final ObjectInserter ow = db.newObjectInserter();
		final ObjectId B = commit(ow
		final ObjectId P = commit(ow
		final ObjectId T = commit(ow

		ThreeWayMerger twm = MergeStrategy.SIMPLE_TWO_WAY_IN_CORE.newMerger(db);
		twm.setBase(P);
		boolean merge = twm.merge(new ObjectId[] { B
		assertTrue(merge);

		final TreeWalk tw = new TreeWalk(db);
		tw.setRecursive(true);
		tw.reset(twm.getResultTreeId());

		assertTrue(tw.next());
		assertEquals("a"
		assertCorrectId(treeB

		assertTrue(tw.next());
		assertEquals("t"
		assertCorrectId(treeT

		assertFalse(tw.next());
	}

