	@Test
	public void testWriteObjectSizeIndex_noDeltas() throws Exception {
		config.setMinBytesForObjSizeIndex(0);
		HashSet<ObjectId> interesting = new HashSet<>();
		interesting.add(ObjectId
				.fromString("82c6b885ff600be425b4ea96dee75dca255b69e7"));

		NullProgressMonitor m1 = NullProgressMonitor.INSTANCE;
		writer = new PackWriter(config
		writer.setUseBitmaps(false);
		writer.setThin(false);
		writer.setIgnoreMissingUninteresting(false);
		writer.preparePack(m1
		writer.writePack(m1

		PackIndex idx;
		try (ByteArrayOutputStream is = new ByteArrayOutputStream()) {
			writer.writeIndex(is);
			idx = PackIndex.read(new ByteArrayInputStream(is.toByteArray()));
		}

		PackObjectSizeIndex objSizeIdx;
		try (ByteArrayOutputStream objSizeStream = new ByteArrayOutputStream()) {
			writer.writeObjectSizeIndex(objSizeStream);
			objSizeIdx = PackObjectSizeIndexLoader.load(
					new ByteArrayInputStream(objSizeStream.toByteArray()));
		}
		writer.close();

		ObjectId knownBlob1 = ObjectId
				.fromString("5b6e7c66c276e7610d4a73c70ec1a1f7c1003259");
		ObjectId knownBlob2 = ObjectId
				.fromString("6ff87c4664981e4397625791c8ea3bbb5f2279a3");
		assertEquals(18009
		assertEquals(18787
	}

