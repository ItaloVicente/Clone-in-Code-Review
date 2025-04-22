	@Test
	public void idIsSameFromFileWithOrWithoutPrefix() throws Exception {
		PackFile packWithoutPrefix = new PackFile(
				new File(TEST_PACK_DIR, TEST_ID));
		assertEquals(packWithoutPrefix.getId(), TEST_PACKFILE_NO_EXT.getId());
	}

