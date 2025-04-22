	@Test
	public void testIncludeInvalidName() throws ConfigInvalidException {
		expectedEx.expect(ConfigInvalidException.class);
		expectedEx.expectMessage(JGitText.get().invalidLineInConfigFile);
		parse("[include]\nbar\n");
	}

	@Test
	public void testIncludeNoValue() throws ConfigInvalidException {
		expectedEx.expect(ConfigInvalidException.class);
		expectedEx.expectMessage(JGitText.get().invalidLineInConfigFile);
		parse("[include]\npath\n");
	}

	@Test
	public void testIncludeEmptyValue() throws ConfigInvalidException {
		expectedEx.expect(ConfigInvalidException.class);
		expectedEx.expectMessage(JGitText.get().invalidLineInConfigFile);
		parse("[include]\npath=\n");
	}

	@Test
	public void testIncludeValuePathNotFound() throws ConfigInvalidException {
		String notFound = "/not/found";
		expectedEx.expect(ConfigInvalidException.class);
		expectedEx.expectMessage(notFound);
		parse("[include]\npath=" + notFound + "\n");
	}

	@Test
	public void testIncludeTooManyRecursions() throws IOException {
		File config = tmp.newFile("config");
		String include = "[include]\npath=" + config.toPath() + "\n";
		Files.write(config.toPath()
		FileBasedConfig fbConfig = new FileBasedConfig(null
				FS.DETECTED);
		try {
			fbConfig.load();
			fail();
		} catch (ConfigInvalidException cie) {
			assertEquals(JGitText.get().tooManyIncludeRecursions
					cie.getCause().getMessage());
		}
	}

	@Test
	public void testInclude() throws IOException
		File config = tmp.newFile("config");
		File more = tmp.newFile("config.more");
		File other = tmp.newFile("config.other");

		String fooBar = "[foo]\nbar=true\n";
		String includeMore = "[include]\npath=" + more.toPath() + "\n";
		String includeOther = "path=" + other.toPath() + "\n";
		String fooPlus = fooBar + includeMore + includeOther;
		Files.write(config.toPath()

		String fooMore = "[foo]\nmore=bar\n";
		Files.write(more.toPath()

		String otherMore = "[other]\nmore=bar\n";
		Files.write(other.toPath()

		Config parsed = parse("[include]\npath=" + config.toPath() + "\n");
		assertTrue(parsed.getBoolean("foo"
		assertEquals("bar"
		assertEquals("bar"
	}

