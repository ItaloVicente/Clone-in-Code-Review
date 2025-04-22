	@Test
	public void testRemovedPackfileShouldBeDetectedByWindowCursor() throws Exception {
		FileRepository fileRepository = setUpRepoWithMultiplePackfiles();
		Collection<Pack> packs = fileRepository.getObjectDatabase().getPacks();

		PackOutputStream out = new PackOutputStream(
				NullProgressMonitor.INSTANCE

		Pack firstPack = packs.iterator().next();
		RandomAccessFile fd = new RandomAccessFile(firstPack.getPackFile()
		firstPack.getPackFile().delete();
		WindowCache.purge(firstPack);

		WindowCursor wc = new WindowCursor(fileRepository.getObjectDatabase());
		wc.copyPackAsIs(firstPack
	}

