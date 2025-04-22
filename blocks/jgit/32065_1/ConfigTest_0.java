	@Test
	public void testReadUserConfigWithInvalidCharactersStripped() {
		final MockSystemReader mockSystemReader = new MockSystemReader();
		final Config localConfig = new Config(mockSystemReader.openUserConfig(
				null

		localConfig.setString("user"
		localConfig.setString("user"

		UserConfig userConfig = localConfig.get(UserConfig.KEY);
		assertEquals("foobar"
		assertEquals("bazqux@example.com"
	}

