	@Test
	public void testRemovedPackfileShouldBeDetectedByWindowCursor() throws Exception {
		FileRepository fileRepository = setUpRepoWithMultiplePackfiles();
		Collection<Pack> packs = fileRepository.getObjectDatabase().getPacks();

		Pack firstPack = packs.iterator().next();

		List<Pack> actualList = new ArrayList<Pack>();
		packs.iterator().forEachRemaining(actualList::add);

		PackWriter packWriter = new PackWriter(config
		 fileRepository.newObjectReader());


		Set<ObjectId> all = new HashSet<>();
		all.add(firstPack.findObjectForOffset(0));

		packWriter.preparePack(NullProgressMonitor.INSTANCE
				PackWriter.NONE);
		PackOutputStream out = new PackOutputStream(
				NullProgressMonitor.INSTANCE

		firstPack.getPackFile().delete();
		WindowCache.purge(firstPack);
		packWriter.writePack(NullProgressMonitor.INSTANCE
				NullProgressMonitor.INSTANCE

	}

