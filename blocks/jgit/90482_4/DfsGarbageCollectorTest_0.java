	@Test
	public void testRacyNoReusePrefersSmaller() throws Exception {
		StringBuilder msg = new StringBuilder();
		for (int i = 0; i < 100; i++) {
			msg.append(i).append(": i am a teapot\n");
		}
		RevBlob a = git.blob(msg.toString());
		RevCommit c0 = git.commit()
				.add("tea"
				.message("0")
				.create();

		msg.append("short and stout\n");
		RevBlob b = git.blob(msg.toString());
		RevCommit c1 = git.commit().parent(c0).tick(1)
				.add("tea"
				.message("1")
				.create();
		git.update("master"

		PackConfig cfg = new PackConfig();
		cfg.setReuseObjects(false);
		cfg.setReuseDeltas(false);
		cfg.setDeltaCompress(false);
		cfg.setThreads(1);
		DfsGarbageCollector gc = new DfsGarbageCollector(repo);
		gc.setGarbageTtl(0
		gc.setPackConfig(cfg);
		run(gc);

		assertEquals(1
		DfsPackDescription large = odb.getPacks()[0].getPackDescription();
		assertSame(PackSource.GC

		cfg.setDeltaCompress(true);
		gc = new DfsGarbageCollector(repo);
		gc.setGarbageTtl(0
		gc.setPackConfig(cfg);
		run(gc);

		assertEquals(1
		DfsPackDescription small = odb.getPacks()[0].getPackDescription();
		assertSame(PackSource.GC
		assertTrue(
				String.format(
						"delta compression pack is smaller s:%d l:%d"
						small.getFileSize(PACK)
				small.getFileSize(PACK) < large.getFileSize(PACK));
		assertTrue("large pack is older"
				large.getLastModified() < small.getLastModified());

		odb.commitPack(Collections.singleton(large)
		odb.clearCache();
		assertEquals(2

		gc = new DfsGarbageCollector(repo);
		gc.setGarbageTtl(0
		run(gc);

		assertEquals(1
		DfsPackDescription rebuilt = odb.getPacks()[0].getPackDescription();
		assertEquals(small.getFileSize(PACK)
	}

