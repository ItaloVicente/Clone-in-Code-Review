	@Test
	public void testDeltaStatistics() throws Exception {
		config.setDeltaCompress(true);
		FileRepository repo = createBareRepository();
		TestRepository<FileRepository> testRepo = new TestRepository<FileRepository>(repo);
		ArrayList<RevObject> blobs = new ArrayList<>();
		blobs.add(testRepo.blob(genDeltableData(1000)));
		blobs.add(testRepo.blob(genDeltableData(1005)));

		try (PackWriter pw = new PackWriter(repo)) {
			NullProgressMonitor m = NullProgressMonitor.INSTANCE;
			pw.preparePack(blobs.iterator());
			pw.writePack(m
			PackStatistics stats = pw.getStatistics();
			assertEquals(1
			assertTrue("Delta bytes not set."
					stats.byObjectType(OBJ_BLOB).getDeltaBytes() > 0);
		}
	}

	private String genDeltableData(int length) {
		assertTrue("Generated data must have a length > 0"
		char[] data = {'a'
		StringBuilder builder = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			builder.append(data[i % 4]);
		}
		return builder.toString();
	}


