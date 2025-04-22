	@Test
	public void keepPreventsDeletionMissingPack() throws Exception {
		createFileInPackFolder(BITMAP_File_1);
		createFileInPackFolder(IDX_File_2);
		createFileInPackFolder(BITMAP_File_2);
		createFileInPackFolder(KEEP_File_2);
		createFileInPackFolder(PACK_File_3);
		gc.gc();
		assertFalse(new File(packDir
		assertTrue(new File(packDir
		assertTrue(new File(packDir
		assertTrue(new File(packDir
		assertTrue(new File(packDir
	}

