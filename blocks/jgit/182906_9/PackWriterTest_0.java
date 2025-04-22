	@Test
	public void testRetryPackWriteUntilMaxAttemptsWhenStateFileHandle()
			throws Exception {
		MockedObjectUseAsIs mockedObjectUseAsIs = new MockedObjectUseAsIs();
		PackWriter pw = new PackWriter(config

		try (FileOutputStream packOS = new FileOutputStream(getPackFile())) {
			LocalCachedPack localCachedPack =
					new LocalCachedPack(
						new ArrayList <>(db.getObjectDatabase().getPacks())
					);

			Collection<LocalCachedPack> collection = new ArrayList();
			collection.add(localCachedPack);
			pw.preparePack(collection);

			pw.writePack(NullProgressMonitor.INSTANCE
					NullProgressMonitor.INSTANCE
		}

		assertEquals(MAX_WRITE_PACK_ATTEMPTS
	}

	@Test
	public void testRetryPackNotCalledWhenNoStateFileHandle()
			throws Exception {
		MockedObjectUseAsIs mockedObjectUseAsIs = new MockedObjectUseAsIs();
		mockedObjectUseAsIs.setThrowStaleFileHandle(false);
		PackWriter pw = new PackWriter(config

		try (FileOutputStream packOS = new FileOutputStream(getPackFile())) {
			LocalCachedPack localCachedPack =
					new LocalCachedPack(
						new ArrayList <>(db.getObjectDatabase().getPacks())
					);

			Collection<LocalCachedPack> collection = new ArrayList();
			collection.add(localCachedPack);
			pw.preparePack(collection);

			pw.writePack(NullProgressMonitor.INSTANCE
					NullProgressMonitor.INSTANCE
		}

		assertEquals(0
	}

	private PackFile getPackFile() throws IOException {
		PackWriter mockedPackWriter = Mockito.spy(
				new PackWriter(config
		doNothing().when(mockedPackWriter).select(any()
		return getPackFileToWrite(db
	}

