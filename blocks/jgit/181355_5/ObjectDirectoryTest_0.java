	@Test
	public void testPackFilesAreScannedUnlessDisabled() throws Exception {
		ObjectToPack otp = setUpRepoWithPackfile();
		PackWriter mockedPackWriter = Mockito.mock(PackWriter.class);

		db.getObjectDatabase().selectObjectRepresentation(mockedPackWriter

		verify(mockedPackWriter
	}

	@Test
	public void testPackFilesAreNotScannedWhenDisabled() throws Exception {
		ObjectToPack otp = setUpRepoWithPackfile();
		FileBasedConfig config = db.getConfig();
		config.setInt(ConfigConstants.CONFIG_PACK_SECTION
				ConfigConstants.CONFIG_KEY_SEARCH_FOR_REUSE_MAX_PACKFILES_TO_SCAN
		config.save();
		PackWriter mockedPackWriter = Mockito.mock(PackWriter.class);

		db.getObjectDatabase().selectObjectRepresentation(mockedPackWriter

		verify(mockedPackWriter
	}

	@Test
	public void testPackFilesAreScannedWhenFindBestPackRepresentationIsSet() throws Exception {
		ObjectToPack otp = setUpRepoWithPackfile();
		FileBasedConfig config = db.getConfig();
		config.setInt(ConfigConstants.CONFIG_PACK_SECTION
				ConfigConstants.CONFIG_KEY_SEARCH_FOR_REUSE_MAX_PACKFILES_TO_SCAN
		config.save();
		PackWriter mockedPackWriter = Mockito.mock(PackWriter.class);
		when(mockedPackWriter.getFindBestPackRepresentation()).thenReturn(true);

		db.getObjectDatabase().selectObjectRepresentation(mockedPackWriter

		verify(mockedPackWriter
	}

	private ObjectToPack setUpRepoWithPackfile() throws IOException
		RevCommit commit = commitFile("file.txt"
		GC gc = new GC(db);
		gc.gc();
		return new ObjectToPack(commit
	}

