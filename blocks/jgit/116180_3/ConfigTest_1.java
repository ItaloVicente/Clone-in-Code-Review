	@Test
	public void testIncludeCaseInsensitiveSection()
			throws IOException
		File included = tmp.newFile("included");
		String content = "[foo]\nbar=true\n";
		Files.write(included.toPath()

		File config = tmp.newFile("config");
		content = "[Include]\npath=" + pathToString(included) + "\n";
		Files.write(config.toPath()

		FileBasedConfig fbConfig = new FileBasedConfig(null
				FS.DETECTED);
		fbConfig.load();
		assertTrue(fbConfig.getBoolean("foo"
	}

	@Test
	public void testIncludeCaseInsensitiveKey()
			throws IOException
		File included = tmp.newFile("included");
		String content = "[foo]\nbar=true\n";
		Files.write(included.toPath()

		File config = tmp.newFile("config");
		content = "[include]\nPath=" + pathToString(included) + "\n";
		Files.write(config.toPath()

		FileBasedConfig fbConfig = new FileBasedConfig(null
				FS.DETECTED);
		fbConfig.load();
		assertTrue(fbConfig.getBoolean("foo"
	}

	@Test
	public void testIncludeExceptionContainsLine() {
		try {
			parse("[include]\npath=\n");
			fail("Expected ConfigInvalidException");
		} catch (ConfigInvalidException e) {
			assertTrue(
					"Expected to find the problem line in the exception message"
					e.getMessage().contains("include.path"));
		}
	}

	@Test
	public void testIncludeExceptionContainsFile() throws IOException {
		File included = tmp.newFile("included");
		String includedPath = pathToString(included);
		String content = "[include]\npath=\n";
		Files.write(included.toPath()

		File config = tmp.newFile("config");
		String include = "[include]\npath=" + includedPath + "\n";
		Files.write(config.toPath()
		FileBasedConfig fbConfig = new FileBasedConfig(null
				FS.DETECTED);
		try {
			fbConfig.load();
			fail("Expected ConfigInvalidException");
		} catch (ConfigInvalidException e) {
			for (Throwable t = e; t != null; t = t.getCause()) {
				if (t.getMessage().contains(includedPath)) {
					return;
				}
			}
			fail("Expected to find the path in the exception message: "
					+ includedPath);
		}
	}

