	@Test
	public void reftableWithoutTombstoneResurrected() throws Exception {
		RevCommit commit0 = commit().message("0").create();
		String NEXT = "refs/heads/next";
		DfsRefDatabase refdb = (DfsRefDatabase)repo.getRefDatabase();
		git.update(NEXT
		Ref next = refdb.exactRef(NEXT);
		assertNotNull(next);
		assertEquals(commit0

		git.delete(NEXT);
		refdb.clearCache();
		assertNull(refdb.exactRef(NEXT));

		DfsGarbageCollector gc = new DfsGarbageCollector(repo);
		gc.setReftableConfig(new ReftableConfig());
		gc.setIncludeDeletes(false);
		gc.setConvertToReftable(false);
		run(gc);
		assertEquals(1
		try (DfsReader ctx = odb.newReader();
			 ReftableReader rr = odb.getReftables()[0].open(ctx)) {
			rr.setIncludeDeletes(true);
			assertEquals(1
			assertEquals(2
			assertNull(rr.exactRef(NEXT));
		}

		RevCommit commit1 = commit().message("1").create();
		DfsPackDescription t1 = odb.newPack(INSERT);
		Ref newNext = new ObjectIdRef.PeeledNonTag(Ref.Storage.LOOSE
				commit1);
		try (DfsOutputStream out = odb.writeFile(t1
			ReftableWriter w = new ReftableWriter();
			w.setMinUpdateIndex(1);
			w.setMaxUpdateIndex(1);
			w.begin(out);
			w.writeRef(newNext
			w.finish();
			t1.addFileExt(REFTABLE);
			t1.setReftableStats(w.getStats());
		}
		odb.commitPack(Collections.singleton(t1)
		assertEquals(2
		refdb.clearCache();
		newNext = refdb.exactRef(NEXT);
		assertNotNull(newNext);
		assertEquals(commit1
	}

	@Test
	public void reftableWithTombstoneNotResurrected() throws Exception {
		RevCommit commit0 = commit().message("0").create();
		String NEXT = "refs/heads/next";
		DfsRefDatabase refdb = (DfsRefDatabase)repo.getRefDatabase();
		git.update(NEXT
		Ref next = refdb.exactRef(NEXT);
		assertNotNull(next);
		assertEquals(commit0

		git.delete(NEXT);
		refdb.clearCache();
		assertNull(refdb.exactRef(NEXT));

		DfsGarbageCollector gc = new DfsGarbageCollector(repo);
		gc.setReftableConfig(new ReftableConfig());
		gc.setIncludeDeletes(true);
		gc.setConvertToReftable(false);
		run(gc);
		assertEquals(1
		try (DfsReader ctx = odb.newReader();
			 ReftableReader rr = odb.getReftables()[0].open(ctx)) {
			rr.setIncludeDeletes(true);
			assertEquals(1
			assertEquals(2
			next = rr.exactRef(NEXT);
			assertNotNull(next);
			assertNull(next.getObjectId());
		}

		RevCommit commit1 = commit().message("1").create();
		DfsPackDescription t1 = odb.newPack(INSERT);
		Ref newNext = new ObjectIdRef.PeeledNonTag(Ref.Storage.LOOSE
				commit1);
		try (DfsOutputStream out = odb.writeFile(t1
			ReftableWriter w = new ReftableWriter();
			w.setMinUpdateIndex(1);
			w.setMaxUpdateIndex(1);
			w.begin(out);
			w.writeRef(newNext
			w.finish();
			t1.addFileExt(REFTABLE);
			t1.setReftableStats(w.getStats());
		}
		odb.commitPack(Collections.singleton(t1)
		assertEquals(2
		refdb.clearCache();
		assertNull(refdb.exactRef(NEXT));
	}

