	@SuppressWarnings("boxing")
	@Test
	public void producesNewReftable() throws Exception {
		String master = "refs/heads/master";
		RevCommit commit0 = commit().message("0").create();
		RevCommit commit1 = commit().message("1").parent(commit0).create();

		BatchRefUpdate bru = git.getRepository().getRefDatabase()
				.newBatchUpdate();
		bru.addCommand(new ReceiveCommand(ObjectId.zeroId()
		for (int i = 1; i <= 5100; i++) {
			bru.addCommand(new ReceiveCommand(ObjectId.zeroId()
					String.format("refs/pulls/%04d"
		}
		try (RevWalk rw = new RevWalk(git.getRepository())) {
			bru.execute(rw
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
				RefCursor rc = rr.seekRef("refs/pulls/5100")) {
			assertTrue(rc.next());
			assertEquals(commit0
			assertFalse(rc.next());
		}
	}

	@Test
	public void leavesNonGcReftablesIfNotConfigured() throws Exception {
		String master = "refs/heads/master";
		RevCommit commit0 = commit().message("0").create();
		RevCommit commit1 = commit().message("1").parent(commit0).create();
		git.update(master

		DfsPackDescription t1 = odb.newPack(INSERT);
		try (DfsOutputStream out = odb.writeFile(t1
			out.write("ignored".getBytes(StandardCharsets.UTF_8));
			t1.addFileExt(REFTABLE);
		}
		odb.commitPack(Collections.singleton(t1)

		DfsGarbageCollector gc = new DfsGarbageCollector(repo);
		gc.setReftableConfig(null);
		run(gc);

		assertEquals(1
		DfsPackFile pack = odb.getPacks()[0];
		DfsPackDescription desc = pack.getPackDescription();
		assertEquals(GC
		assertTrue("commit0 in pack"
		assertTrue("commit1 in pack"

		DfsReftable[] tables = odb.getReftables();
		assertEquals(1
		assertEquals(t1
	}

	@Test
	public void prunesNonGcReftables() throws Exception {
		String master = "refs/heads/master";
		RevCommit commit0 = commit().message("0").create();
		RevCommit commit1 = commit().message("1").parent(commit0).create();
		git.update(master

		DfsPackDescription t1 = odb.newPack(INSERT);
		try (DfsOutputStream out = odb.writeFile(t1
			out.write("ignored".getBytes(StandardCharsets.UTF_8));
			t1.addFileExt(REFTABLE);
		}
		odb.commitPack(Collections.singleton(t1)
		odb.clearCache();

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

		DfsPackDescription t1 = odb.newPack(INSERT);
		Ref next = new ObjectIdRef.PeeledNonTag(Ref.Storage.LOOSE
				"refs/heads/next"
		try (DfsOutputStream out = odb.writeFile(t1
			ReftableWriter w = new ReftableWriter();
			w.setMinUpdateIndex(42);
			w.setMaxUpdateIndex(42);
			w.begin(out);
			w.sortAndWriteRefs(Collections.singleton(next));
			w.finish();
			t1.addFileExt(REFTABLE);
			t1.setReftableStats(w.getStats());
		}
		odb.commitPack(Collections.singleton(t1)

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

