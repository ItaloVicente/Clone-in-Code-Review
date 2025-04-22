	@SuppressWarnings("boxing")
	@Test
	public void producesReftable() throws Exception {
		String master = "refs/heads/master";
		RevCommit commit0 = commit().message("0").create();
		RevCommit commit1 = commit().message("1").parent(commit0).create();
		git.update(master
		for (int i = 1; i <= 5100; i++) {
			git.update(String.format("refs/pulls/%04d"
		}

		DfsGarbageCollector gc = new DfsGarbageCollector(repo);
		gc.setReftableConfig(new ReftableConfig());
		run(gc);

		assertEquals(1
		DfsPackFile pack = odb.getPacks()[0];
		DfsPackDescription desc = pack.getPackDescription();
		assertEquals(GC
		assertTrue("commit0 in pack"
		assertTrue("commit1 in pack"

		assertTrue(desc.hasFileExt(PackExt.REFTABLE));
		ReftableWriter.Stats stats = desc.getReftableStats();
		assertNotNull(stats);
		assertTrue(stats.totalBytes() > 0);
		DfsReftable table = new DfsReftable(DfsBlockCache.getInstance()
		try (DfsReader ctx = odb.newReader();
				ReftableReader rr = table.open(ctx);
				RefCursor rc = rr.seek("refs/pulls/5100")) {
			assertTrue(rc.next());
			assertEquals(commit1
			assertFalse(rc.next());
		}
	}

