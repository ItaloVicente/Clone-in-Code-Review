	@Test
	public void readBackSmallObjectBeforeLargeObject() throws Exception {
		WindowCacheConfig wcc = new WindowCacheConfig();
		wcc.setStreamFileThreshold(1024);
		wcc.install();

		ObjectId blobId1;
		ObjectId blobId2;
		ObjectId largeId;
		byte[] blob1 = Constants.encode("blob1");
		byte[] blob2 = Constants.encode("blob2");
		byte[] largeBlob = newLargeBlob();
		try (PackInserter ins = newInserter()) {
			assertThat(blob1.length
			assertThat(largeBlob.length

			blobId1 = ins.insert(OBJ_BLOB
			largeId = ins.insert(OBJ_BLOB

			try (ObjectReader reader = ins.newReader()) {
				assertBlob(reader
			}

			blobId2 = ins.insert(OBJ_BLOB

			try (ObjectReader reader = ins.newReader()) {
				assertBlob(reader
				assertBlob(reader
				assertBlob(reader
			}

			ins.flush();
		}

		try (ObjectReader reader = db.newObjectReader()) {
				assertBlob(reader
				assertBlob(reader
				assertBlob(reader
		}
	}

