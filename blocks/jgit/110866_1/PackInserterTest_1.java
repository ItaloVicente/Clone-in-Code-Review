	@Test
	public void readBackSmallFiles() throws Exception {
		ObjectId blobId1;
		ObjectId blobId2;
		ObjectId blobId3;
		byte[] blob1 = Constants.encode("blob1");
		byte[] blob2 = Constants.encode("blob2");
		byte[] blob3 = Constants.encode("blob3");
		try (PackInserter ins = newInserter()) {
			assertThat(blob1.length
			blobId1 = ins.insert(OBJ_BLOB

			try (ObjectReader reader = ins.newReader()) {
				assertBlob(reader
			}

			blobId2 = ins.insert(OBJ_BLOB
			ins.flush();

			blobId3 = ins.insert(OBJ_BLOB
		}

		assertPacksOnly();
		List<PackFile> packs = listPacks();
		assertEquals(1
		assertEquals(2

		try (ObjectReader reader = db.newObjectReader()) {
			assertBlob(reader
			assertBlob(reader

			try {
				reader.open(blobId3);
				fail("Expected MissingObjectException");
			} catch (MissingObjectException expected) {
			}
		}
	}

	@Test
	public void readBackLargeFile() throws Exception {
		ObjectId blobId;
		byte[] blob = newLargeBlob();

		WindowCacheConfig wcc = new WindowCacheConfig();
		wcc.setStreamFileThreshold(1024);
		wcc.install();
		try (ObjectReader reader = db.newObjectReader()) {
			assertThat(blob.length
		}

		try (PackInserter ins = newInserter()) {
			blobId = ins.insert(OBJ_BLOB

			try (ObjectReader reader = ins.newReader()) {
				assertThat(blob.length
				assertBlob(reader
			}
		}

		assertPacksOnly();
		assertEquals(0

		try (ObjectReader reader = db.newObjectReader()) {
			try {
				reader.open(blobId);
				fail("Expected MissingObjectException");
			} catch (MissingObjectException expected) {
			}
		}
	}

	@Test
	public void readBackFallsBackToRepo() throws Exception {
		ObjectId blobId;
		byte[] blob = Constants.encode("foo contents");
		try (PackInserter ins = newInserter()) {
			assertThat(blob.length
			blobId = ins.insert(OBJ_BLOB
			ins.flush();
		}

		try (PackInserter ins = newInserter();
				ObjectReader reader = ins.newReader()) {
			assertBlob(reader
		}
	}

