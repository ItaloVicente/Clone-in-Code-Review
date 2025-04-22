		assertEquals(1
	}

	@SuppressWarnings("resource")
	@Test
	public void noConcurrencySerializedReads_twoRepos() throws Exception {
		InMemoryRepository r1 = createRepoWithBitmap("test1");
		InMemoryRepository r2 = createRepoWithBitmap("test2");
		resetCache(1);

		DfsReader reader = (DfsReader) r1.newObjectReader();
		DfsPackFile[] r1Packs = r1.getObjectDatabase().getPacks();
		DfsPackFile[] r2Packs = r2.getObjectDatabase().getPacks();
		assertEquals(r1Packs.length

		for (int i = 0; i < r1.getObjectDatabase().getPacks().length; ++i) {
			DfsPackFile pack1 = r1Packs[i];
			DfsPackFile pack2 = r2Packs[i];
			if (pack1.isGarbage() || pack2.isGarbage()) {
				continue;
			}
			asyncRun(() -> pack1.getBitmapIndex(reader));
			asyncRun(() -> pack2.getBitmapIndex(reader));
		}

		waitForExecutorPoolTermination();
		assertEquals(2
		assertEquals(2
		assertEquals(2
	}

	@SuppressWarnings("resource")
	@Test
	public void highConcurrencySerializedReads_oneRepo() throws Exception {
		InMemoryRepository r1 = createRepoWithBitmap("test");
		resetCache();

		DfsReader reader = (DfsReader) r1.newObjectReader();
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

	@SuppressWarnings("resource")
	@Test
	public void lowConcurrencySerializedReads_twoRepos() throws Exception {
		InMemoryRepository r1 = createRepoWithBitmap("test1");
		InMemoryRepository r2 = createRepoWithBitmap("test2");
		resetCache(2);

		DfsReader reader = (DfsReader) r1.newObjectReader();
		DfsPackFile[] r1Packs = r1.getObjectDatabase().getPacks();
		DfsPackFile[] r2Packs = r2.getObjectDatabase().getPacks();
		assertEquals(r1Packs.length

		for (int i = 0; i < r1.getObjectDatabase().getPacks().length; ++i) {
			DfsPackFile pack1 = r1Packs[i];
			DfsPackFile pack2 = r2Packs[i];
			if (pack1.isGarbage() || pack2.isGarbage()) {
				continue;
			}
			asyncRun(() -> pack1.getBitmapIndex(reader));
			asyncRun(() -> pack2.getBitmapIndex(reader));
		}

		waitForExecutorPoolTermination();
		assertEquals(2
		assertEquals(2
		assertEquals(2
	}

	@SuppressWarnings("resource")
	@Test
	public void lowConcurrencySerializedReads_twoReposLoadIndex()
			throws Exception {
		InMemoryRepository r1 = createRepoWithBitmap("test1");
		InMemoryRepository r2 = createRepoWithBitmap("test2");
		resetCache(2);

		DfsReader reader = (DfsReader) r1.newObjectReader();
		DfsPackFile[] r1Packs = r1.getObjectDatabase().getPacks();
		DfsPackFile[] r2Packs = r2.getObjectDatabase().getPacks();
		assertEquals(r1Packs.length

		for (int i = 0; i < r1.getObjectDatabase().getPacks().length; ++i) {
			DfsPackFile pack1 = r1Packs[i];
			DfsPackFile pack2 = r2Packs[i];
			if (pack1.isGarbage() || pack2.isGarbage()) {
				continue;
			}
			asyncRun(() -> pack1.getBitmapIndex(reader));
			asyncRun(() -> pack1.getPackIndex(reader));
			asyncRun(() -> pack2.getBitmapIndex(reader));
		}
		waitForExecutorPoolTermination();

		assertEquals(2
		assertEquals(2
		assertEquals(2
