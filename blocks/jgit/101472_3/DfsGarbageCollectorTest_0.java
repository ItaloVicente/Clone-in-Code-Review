	@Test
	public void producesReftable() throws Exception {
		String master = "refs/heads/master";
		RevCommit commit0 = commit().message("0").create();
		RevCommit commit1 = commit().message("1").parent(commit0).create();
		git.update(master

		DfsGarbageCollector gc = new DfsGarbageCollector(repo);
		gc.setWriteReftable(true);
		run(gc);

		assertEquals(1
		DfsPackFile pack = odb.getPacks()[0];
		DfsPackDescription desc = pack.getPackDescription();
		assertEquals(GC
		assertTrue("commit0 in pack"
		assertTrue("commit1 in pack"

		assertTrue(desc.hasFileExt(PackExt.REFTABLE));
		DfsReftable table = new DfsReftable(DfsBlockCache.getInstance()
		try (DfsReader ctx = odb.newReader(); RefCursor rc = table.open(ctx)) {
			rc.seek(master);
			assertTrue(rc.next());
			assertEquals(commit1
			assertFalse(rc.next());
		}
	}

