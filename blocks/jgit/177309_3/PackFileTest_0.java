	public void idIsSameFromFileOrDirAndName() throws Exception {
		File pack = new File(TEST_PACK_DIR
		PackFile pf = new PackFile(pack);
		PackFile pfFromDirAndName = new PackFile(TEST_PACK_DIR
				TEST_PACK_FILE_NAME);
		assertEquals(pf.getId()
