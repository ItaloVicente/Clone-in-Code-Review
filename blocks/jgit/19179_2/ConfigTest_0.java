	@Test
	public void test_ReadNamesInSectionRecursive()
			throws ConfigInvalidException {
		String baseConfigString = "[core]\n" + "logAllRefUpdates = true\n";
		String configString = "[core]\n" + "repositoryFormatVersion = 0\n"
				+ "filemode = false\n";
		final Config c = parse(configString
		Set<String> names = c.getNames("core"
		assertEquals("Core section size"
		assertTrue("Core section should contain \"filemode\""
				names.contains("filemode"));
		assertTrue("Core section should contain \"repositoryFormatVersion\""
				names.contains("repositoryFormatVersion"));
		assertTrue("Core section should contain \"logAllRefUpdates\""
				names.contains("logAllRefUpdates"));

		Iterator<String> itr = names.iterator();
		assertEquals("filemode"
		assertEquals("repositoryFormatVersion"
		assertEquals("logAllRefUpdates"
		assertFalse(itr.hasNext());
	}

