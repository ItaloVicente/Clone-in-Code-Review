	@SuppressWarnings("resource")
	@Test
	public void highConcurrencyParallelReads_oneRepoParallelReverseIndex()
			throws Exception {
		InMemoryRepository r1 = createRepoWithBitmap("test");
		resetCache();

		DfsReader reader = (DfsReader) r1.newObjectReader();
		reader.getOptions().setLoadRevIndexInParallel(true);
		for (DfsPackFile pack : r1.getObjectDatabase().getPacks()) {
			if (pack.isGarbage()) {
				continue;
			}
			asyncRun(() -> pack.getBitmapIndex(reader));
			asyncRun(() -> pack.getPackIndex(reader));
			asyncRun(() -> pack.getBitmapIndex(reader));
		}
		waitForExecutorPoolTermination();

		assertEquals(1
		assertEquals(1
		assertEquals(1
	}

