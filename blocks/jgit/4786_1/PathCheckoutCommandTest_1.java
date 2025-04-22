
	public void testCheckoutMixedNewlines() throws Exception {
		StoredConfig config = git.getRepository().getConfig();
		config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_AUTOCRLF
		config.save();
		File written = writeTrashFile(FILE1
		assertEquals("4\r\n4"
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("CRLF").call();
		written = writeTrashFile(FILE1
		assertEquals("4\n4"
		git.add().addFilepattern(FILE1).call();
		git.checkout().addPath(FILE1).call();
		Status status = git.status().call();
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
	}
