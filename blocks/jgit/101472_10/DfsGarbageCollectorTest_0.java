	@SuppressWarnings("boxing")
	@Test
	public void producesNewReftable() throws Exception {
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

		assertTrue(desc.hasFileExt(REFTABLE));
		ReftableWriter.Stats stats = desc.getReftableStats();
		assertNotNull(stats);
		assertTrue(stats.totalBytes() > 0);
		assertEquals(5101
		assertEquals(1
		assertEquals(1

		DfsReftable table = new DfsReftable(DfsBlockCache.getInstance()
		try (DfsReader ctx = odb.newReader();
				ReftableReader rr = table.open(ctx);
				RefCursor rc = rr.seek("refs/pulls/5100")) {
			assertTrue(rc.next());
			assertEquals(commit1
			assertFalse(rc.next());
		}
	}

	@Test
	public void prunesNonGcReftables() throws Exception {
		String master = "refs/heads/master";
		RevCommit commit0 = commit().message("0").create();
		RevCommit commit1 = commit().message("1").parent(commit0).create();
		git.update(master

		MemObjDatabase objdb = repo.getObjectDatabase();
		DfsPackDescription t1 = objdb.newPack(INSERT);
		try (DfsOutputStream out = objdb.writeFile(t1
			out.write("ignored".getBytes(StandardCharsets.UTF_8));
		}
		objdb.commitPack(Collections.singleton(t1)

		DfsGarbageCollector gc = new DfsGarbageCollector(repo);
		gc.setReftableConfig(new ReftableConfig());
		run(gc);

		assertEquals(1
		DfsPackFile pack = odb.getPacks()[0];
		DfsPackDescription desc = pack.getPackDescription();
		assertEquals(GC
		assertTrue("commit0 in pack"
		assertTrue("commit1 in pack"

		DfsReftable[] tables = odb.getReftables();
		assertEquals(1
		assertEquals(desc
		assertTrue(desc.hasFileExt(REFTABLE));
	}

	@Test
	public void compactsReftables() throws Exception {
		String master = "refs/heads/master";
		RevCommit commit0 = commit().message("0").create();
		RevCommit commit1 = commit().message("1").parent(commit0).create();
		git.update(master

		DfsGarbageCollector gc = new DfsGarbageCollector(repo);
		gc.setReftableConfig(new ReftableConfig());
		run(gc);

		MemObjDatabase objdb = repo.getObjectDatabase();
		DfsPackDescription t1 = objdb.newPack(INSERT);
		Ref next = new ObjectIdRef.PeeledNonTag(Ref.Storage.LOOSE
				"refs/heads/next"
		try (DfsOutputStream out = objdb.writeFile(t1
			ReftableWriter w = new ReftableWriter();
			w.setMinUpdateIndex(42);
			w.setMaxUpdateIndex(42);
			w.begin(out);
			w.sortAndWriteRefs(Collections.singleton(next));
			w.finish();
			t1.addFileExt(REFTABLE);
			t1.setReftableStats(w.getStats());
		}
		objdb.commitPack(Collections.singleton(t1)

		gc = new DfsGarbageCollector(repo);
		gc.setReftableConfig(new ReftableConfig());
		run(gc);

		assertEquals(1
		DfsPackFile pack = odb.getPacks()[0];
		DfsPackDescription desc = pack.getPackDescription();
		assertEquals(GC
		assertTrue("commit0 in pack"
		assertTrue("commit1 in pack"

		DfsReftable[] tables = odb.getReftables();
		assertEquals(1
		assertEquals(desc
		assertTrue(desc.hasFileExt(REFTABLE));

		DfsReftable table = new DfsReftable(DfsBlockCache.getInstance()
		try (DfsReader ctx = odb.newReader();
				ReftableReader rr = table.open(ctx);
				RefCursor rc = rr.allRefs()) {
			assertEquals(1
			assertEquals(42

			assertTrue(rc.next());
			assertEquals(master
			assertEquals(commit1

			assertTrue(rc.next());
			assertEquals(next.getName()
			assertEquals(commit0

			assertFalse(rc.next());
		}
	}

