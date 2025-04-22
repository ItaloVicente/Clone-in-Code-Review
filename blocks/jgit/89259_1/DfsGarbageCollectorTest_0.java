	@Test
	public void testEstimateGcPackSizeInNewRepo() throws Exception {
		RevCommit commit0 = commit().message("0").create();
		RevCommit commit1 = commit().message("1").parent(commit0).create();
		git.update("master"

		long inputPacksSize = 0;
		assertEquals(2
		for (DfsPackFile pack : odb.getPacks()) {
			assertEquals(INSERT
			inputPacksSize += pack.getPackDescription().getFileSize(PACK);
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
		long inputPacksSize = 0;
		for (DfsPackFile pack : odb.getPacks()) {
			DfsPackDescription d = pack.getPackDescription();
			if (d.getPackSource() == GC) {
				gcPackFound = true;
			} else if (d.getPackSource() == INSERT) {
				insertPackFound = true;
			} else {
				fail("unexpected " + d.getPackSource());
			}
			inputPacksSize += d.getFileSize(PACK);
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

		long inputPacksSize = 0;
		assertEquals(2
		for (DfsPackFile pack : odb.getPacks()) {
			assertEquals(INSERT
			inputPacksSize += pack.getPackDescription().getFileSize(PACK);
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
		long inputPacksSize = 0;
		for (DfsPackFile pack : odb.getPacks()) {
			DfsPackDescription d = pack.getPackDescription();
			if (d.getPackSource() == GC_REST) {
				gcRestPackFound = true;
			} else if (d.getPackSource() == INSERT) {
				insertPackFound = true;
			} else {
				fail("unexpected " + d.getPackSource());
			}
			inputPacksSize += d.getFileSize(PACK);
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
	public void testEstimateGcPackSizesWithGcAndGcRestPAcks() throws Exception {
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
				assertEquals(gcPackSize + insertPackSize
						pack.getPackDescription().getEstimatedPackSize());
			} else if (d.getPackSource() == GC_REST) {
				gcRestPackFound = true;
				assertEquals(gcRestPackSize + insertPackSize
						pack.getPackDescription().getEstimatedPackSize());
			} else {
				fail("unexpected " + d.getPackSource());
			}
		}
		assertTrue(gcPackFound);
		assertTrue(gcRestPackFound);
	}

