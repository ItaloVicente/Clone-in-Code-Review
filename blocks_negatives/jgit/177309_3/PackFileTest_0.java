	@Test
	public void cannotCreatePreservedNoExtFromNonPreservedNoExt()
			throws Exception {
		assertThrows(IllegalArgumentException.class, () -> TEST_PACKFILE_NO_EXT
				.createPreservedForDirectory(TEST_PRESERVED_DIR));
	}

