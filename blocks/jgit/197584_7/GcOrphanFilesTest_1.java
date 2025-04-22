		assertFalse(new File(packDir
	}

	@Test
	public void noPacks_allIndexesDeleted() throws Exception {
		createFileInPackFolder(BITMAP_File_1);
		createFileInPackFolder(IDX_File_2);
		createFileInPackFolder(REVERSE_File_4);
		gc.gc().get();
		assertFalse(new File(packDir
		assertFalse(new File(packDir
		assertFalse(new File(packDir
