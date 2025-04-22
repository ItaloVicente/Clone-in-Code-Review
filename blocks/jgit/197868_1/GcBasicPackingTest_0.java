	@Test
	public void testReloadPackWhenBitmapFileIsModified() throws Exception {
		BranchBuilder bb = tr.branch("refs/heads/master");
		bb.commit().message("M").add("M"
		bb.commit().message("B").add("B"
		bb.commit().message("A").add("A"

		Collection<Pack> packs = tr.getRepository().getObjectDatabase()
				.getPacks();
		assertEquals(0

		configureGc(gc
		gc.gc();
		Pack pack = getSinglePack();
		assertTrue(pack.getBitMapFileSnapshot().isPresent());

		PackFile packFile = pack.getPackFile();
		String packId = FileNameUtils.getBaseName(packFile.getName());
		Path bitmapFilePath = Paths.get(packFile.getParent() 
				+ PackExt.BITMAP_INDEX.getExtension());
		Path tmpBitmapFilePath = Paths.get(packFile.getParent()
				+ PackExt.BITMAP_INDEX.getExtension() +"_tmp");
		Files.move(bitmapFilePath

		pack = getSinglePack();
		assertFalse(pack.getBitMapFileSnapshot().isPresent());
		assertNull(pack.getBitmapIndex());

		Files.move(tmpBitmapFilePath

		tr.getRepository().getObjectDatabase().has(unknownID);
		pack = getSinglePack();
		assertTrue(pack.getBitMapFileSnapshot().isPresent());
		assertNotNull(pack.getBitmapIndex());
	}
