	@Test
	public void createsObjIndex() throws Exception {
		FileBasedConfig jGitConfig = mockSystemReader.getJGitConfig();
		jGitConfig.setInt(
				ConfigConstants.CONFIG_PACK_SECTION
				null
				ConfigConstants.CONFIG_KEY_MIN_BYTES_OBJ_SIZE_INDEX
		jGitConfig.save();
		byte[] oneBlob = Constants.encode("a blob with some content");
		byte[] anotherBlob = Constants.encode("some more contents");
		try (PackInserter ins = newInserter()) {
			ins.insert(OBJ_BLOB
			ins.insert(OBJ_BLOB
			ins.flush();
		}

		List<Pack> listPacks = listPacks(db);
		assertEquals(1
		assertTrue(listPacks.get(0).getPackFile()
				.create(PackExt.OBJECT_SIZE_INDEX).exists());
	}

