	@Test
	public void testEstimateGcPackSizeInNewRepo() throws Exception {
		RevCommit commit0 = commit().message("0").create();
		RevCommit commit1 = commit().message("1").parent(commit0).create();
		git.update("master"

		long inputPacksSize = 32;
		assertEquals(2
		for (DfsPackFile pack : odb.getPacks()) {
			assertEquals(INSERT
			inputPacksSize += pack.getPackDescription().getFileSize(PACK) - 32;
		}

		gcNoTtl();

		assertEquals(1
		DfsPackFile pack = odb.getPacks()[0];
		assertEquals(GC
		assertEquals(inputPacksSize
				pack.getPackDescription().getEstimatedPackSize());
	}

	@Test
	public void testEstimateGcPackSizeWithAnExistingGcPack() throws Exception {
		RevCommit commit0 = commit().message("0").create();
		RevCommit commit1 = commit().message("1").parent(commit0).create();
		git.update("master"

		gcNoTtl();

		RevCommit commit2 = commit().message("2").parent(commit1).create();
		git.update("master"

		assertEquals(2
		boolean gcPackFound = false;
		boolean insertPackFound = false;
		long inputPacksSize = 32;
		for (DfsPackFile pack : odb.getPacks()) {
			DfsPackDescription d = pack.getPackDescription();
			if (d.getPackSource() == GC) {
				gcPackFound = true;
			} else if (d.getPackSource() == INSERT) {
				insertPackFound = true;
			} else {
				fail("unexpected " + d.getPackSource());
			}
			inputPacksSize += d.getFileSize(PACK) - 32;
		}
		assertTrue(gcPackFound);
		assertTrue(insertPackFound);

		gcNoTtl();

		DfsPackFile pack = odb.getPacks()[0];
		assertEquals(GC
		assertEquals(inputPacksSize
				pack.getPackDescription().getEstimatedPackSize());
	}

	@Test
	public void testEstimateGcRestPackSizeInNewRepo() throws Exception {
		RevCommit commit0 = commit().message("0").create();
		RevCommit commit1 = commit().message("1").parent(commit0).create();
		git.update("refs/notes/note1"

		long inputPacksSize = 32;
		assertEquals(2
		for (DfsPackFile pack : odb.getPacks()) {
			assertEquals(INSERT
			inputPacksSize += pack.getPackDescription().getFileSize(PACK) - 32;
		}

		gcNoTtl();

		assertEquals(1
		DfsPackFile pack = odb.getPacks()[0];
		assertEquals(GC_REST
		assertEquals(inputPacksSize
				pack.getPackDescription().getEstimatedPackSize());
	}

	@Test
	public void testEstimateGcRestPackSizeWithAnExistingGcPack()
			throws Exception {
		RevCommit commit0 = commit().message("0").create();
		RevCommit commit1 = commit().message("1").parent(commit0).create();
		git.update("refs/notes/note1"

		gcNoTtl();

		RevCommit commit2 = commit().message("2").parent(commit1).create();
		git.update("refs/notes/note2"

		assertEquals(2
		boolean gcRestPackFound = false;
		boolean insertPackFound = false;
		long inputPacksSize = 32;
		for (DfsPackFile pack : odb.getPacks()) {
			DfsPackDescription d = pack.getPackDescription();
			if (d.getPackSource() == GC_REST) {
				gcRestPackFound = true;
			} else if (d.getPackSource() == INSERT) {
				insertPackFound = true;
			} else {
				fail("unexpected " + d.getPackSource());
			}
			inputPacksSize += d.getFileSize(PACK) - 32;
		}
		assertTrue(gcRestPackFound);
		assertTrue(insertPackFound);

		gcNoTtl();

		DfsPackFile pack = odb.getPacks()[0];
		assertEquals(GC_REST
		assertEquals(inputPacksSize
				pack.getPackDescription().getEstimatedPackSize());
	}

	@Test
	public void testEstimateGcPackSizesWithGcAndGcRestPacks() throws Exception {
		RevCommit commit0 = commit().message("0").create();
		git.update("head"
		RevCommit commit1 = commit().message("1").parent(commit0).create();
		git.update("refs/notes/note1"

		gcNoTtl();

		RevCommit commit2 = commit().message("2").parent(commit1).create();
		git.update("refs/notes/note2"

		assertEquals(3
		boolean gcPackFound = false;
		boolean gcRestPackFound = false;
		boolean insertPackFound = false;
		long gcPackSize = 0;
		long gcRestPackSize = 0;
		long insertPackSize = 0;
		for (DfsPackFile pack : odb.getPacks()) {
			DfsPackDescription d = pack.getPackDescription();
			if (d.getPackSource() == GC) {
				gcPackFound = true;
				gcPackSize = d.getFileSize(PACK);
			} else if (d.getPackSource() == GC_REST) {
				gcRestPackFound = true;
				gcRestPackSize = d.getFileSize(PACK);
			} else if (d.getPackSource() == INSERT) {
				insertPackFound = true;
				insertPackSize = d.getFileSize(PACK);
			} else {
				fail("unexpected " + d.getPackSource());
			}
		}
		assertTrue(gcPackFound);
		assertTrue(gcRestPackFound);
		assertTrue(insertPackFound);

		gcNoTtl();

		assertEquals(2
		gcPackFound = false;
		gcRestPackFound = false;
		for (DfsPackFile pack : odb.getPacks()) {
			DfsPackDescription d = pack.getPackDescription();
			if (d.getPackSource() == GC) {
				gcPackFound = true;
				assertEquals(gcPackSize + insertPackSize - 32
						pack.getPackDescription().getEstimatedPackSize());
			} else if (d.getPackSource() == GC_REST) {
				gcRestPackFound = true;
				assertEquals(gcRestPackSize + insertPackSize - 32
						pack.getPackDescription().getEstimatedPackSize());
			} else {
				fail("unexpected " + d.getPackSource());
			}
		}
		assertTrue(gcPackFound);
		assertTrue(gcRestPackFound);
	}

	@Test
	public void testEstimateUnreachableGarbagePackSize() throws Exception {
		RevCommit commit0 = commit().message("0").create();
		RevCommit commit1 = commit().message("1").parent(commit0).create();
		git.update("master"

		assertTrue("commit0 reachable"
		assertFalse("commit1 garbage"

		long packSize0 = 0;
		long packSize1 = 0;
		assertEquals(2
		for (DfsPackFile pack : odb.getPacks()) {
			DfsPackDescription d = pack.getPackDescription();
			assertEquals(INSERT
			if (isObjectInPack(commit0
				packSize0 = d.getFileSize(PACK);
			} else if (isObjectInPack(commit1
				packSize1 = d.getFileSize(PACK);
			} else {
				fail("expected object not found in the pack");
			}
		}

		gcNoTtl();

		assertEquals(2
		for (DfsPackFile pack : odb.getPacks()) {
			DfsPackDescription d = pack.getPackDescription();
			if (d.getPackSource() == GC) {
				assertEquals(packSize0 + packSize1 - 32
						d.getEstimatedPackSize());
			} else if (d.getPackSource() == UNREACHABLE_GARBAGE) {
				assertEquals(packSize1
			} else {
				fail("unexpected " + d.getPackSource());
			}
		}
	}

