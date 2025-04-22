	@Test
	public void emptyRefFoldersSkipFiles() throws Exception {
		FileTime fileTime = FileTime.from(Instant.now().minusSeconds(31));
		Path refFile = Files.createFile(refsDir.resolve(".DS_Store"));
		Path refDir01 = Files.createDirectories(heads.resolve(REF_FOLDER_01));
		Path refDir02 = Files.createDirectories(heads.resolve(REF_FOLDER_02));
		setLastModifiedTime(fileTime
		setLastModifiedTime(fileTime
		assertTrue(refDir01.toFile().exists());
		assertTrue(refDir02.toFile().exists());
		gc.gc();
		assertTrue(Files.exists(refFile));
	}

