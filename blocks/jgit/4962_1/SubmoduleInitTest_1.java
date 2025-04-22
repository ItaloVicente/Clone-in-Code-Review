
	@Test
	public void resolveSameLevelRelativeUrl() throws Exception {
		final String path = addSubmoduleToIndex();

		FileBasedConfig config = db.getConfig();
		config.setString(ConfigConstants.CONFIG_REMOTE_SECTION
				Constants.DEFAULT_REMOTE_NAME
				base);
		config.save();

		SubmoduleWalk generator = SubmoduleWalk.forIndex(db);
		assertTrue(generator.next());
		assertNull(generator.getConfigUrl());
		assertNull(generator.getConfigUpdate());

		FileBasedConfig modulesConfig = new FileBasedConfig(new File(
				db.getWorkTree()
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_PATH
		String url = "./sub.git";
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_URL
		String update = "rebase";
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_UPDATE
		modulesConfig.save();

		SubmoduleInitCommand command = new SubmoduleInitCommand(db);
		Collection<String> modules = command.call();
		assertNotNull(modules);
		assertEquals(1
		assertEquals(path

		generator = SubmoduleWalk.forIndex(db);
		assertTrue(generator.next());
		assertEquals(update
	}

	@Test
	public void resolveOneLevelHigherRelativeUrl() throws IOException
			ConfigInvalidException {
		final String path = addSubmoduleToIndex();

		FileBasedConfig config = db.getConfig();
		config.setString(ConfigConstants.CONFIG_REMOTE_SECTION
				Constants.DEFAULT_REMOTE_NAME
				base);
		config.save();

		SubmoduleWalk generator = SubmoduleWalk.forIndex(db);
		assertTrue(generator.next());
		assertNull(generator.getConfigUrl());
		assertNull(generator.getConfigUpdate());

		FileBasedConfig modulesConfig = new FileBasedConfig(new File(
				db.getWorkTree()
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_PATH
		String url = "../sub.git";
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_URL
		String update = "rebase";
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_UPDATE
		modulesConfig.save();

		SubmoduleInitCommand command = new SubmoduleInitCommand(db);
		Collection<String> modules = command.call();
		assertNotNull(modules);
		assertEquals(1
		assertEquals(path

		generator = SubmoduleWalk.forIndex(db);
		assertTrue(generator.next());
		assertEquals(update
	}

	@Test
	public void resolveTwoLevelHigherRelativeUrl() throws IOException
			ConfigInvalidException {
		final String path = addSubmoduleToIndex();

		FileBasedConfig config = db.getConfig();
		config.setString(ConfigConstants.CONFIG_REMOTE_SECTION
				Constants.DEFAULT_REMOTE_NAME
				base);
		config.save();

		SubmoduleWalk generator = SubmoduleWalk.forIndex(db);
		assertTrue(generator.next());
		assertNull(generator.getConfigUrl());
		assertNull(generator.getConfigUpdate());

		FileBasedConfig modulesConfig = new FileBasedConfig(new File(
				db.getWorkTree()
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_PATH
		String url = "../../sub.git";
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_URL
		String update = "rebase";
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_UPDATE
		modulesConfig.save();

		SubmoduleInitCommand command = new SubmoduleInitCommand(db);
		Collection<String> modules = command.call();
		assertNotNull(modules);
		assertEquals(1
		assertEquals(path

		generator = SubmoduleWalk.forIndex(db);
		assertTrue(generator.next());
		assertEquals(update
	}

	@Test
	public void resolveWorkingDirectoryRelativeUrl() throws IOException
			ConfigInvalidException {
		final String path = addSubmoduleToIndex();

		String base = db.getWorkTree().getAbsolutePath();
		if (File.separatorChar == '\\')
			base = base.replace('\\'
		FileBasedConfig config = db.getConfig();
		config.setString(ConfigConstants.CONFIG_REMOTE_SECTION
				Constants.DEFAULT_REMOTE_NAME
				null);
		config.save();

		SubmoduleWalk generator = SubmoduleWalk.forIndex(db);
		assertTrue(generator.next());
		assertNull(generator.getConfigUrl());
		assertNull(generator.getConfigUpdate());

		FileBasedConfig modulesConfig = new FileBasedConfig(new File(
				db.getWorkTree()
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_PATH
		String url = "./sub.git";
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_URL
		String update = "rebase";
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_UPDATE
		modulesConfig.save();

		SubmoduleInitCommand command = new SubmoduleInitCommand(db);
		Collection<String> modules = command.call();
		assertNotNull(modules);
		assertEquals(1
		assertEquals(path

		generator = SubmoduleWalk.forIndex(db);
		assertTrue(generator.next());
		assertEquals(base + "/sub.git"
		assertEquals(update
	}

	@Test
	public void resolveInvalidParentUrl() throws IOException
			ConfigInvalidException {
		final String path = addSubmoduleToIndex();

		String base = "no_slash";
		FileBasedConfig config = db.getConfig();
		config.setString(ConfigConstants.CONFIG_REMOTE_SECTION
				Constants.DEFAULT_REMOTE_NAME
				base);
		config.save();

		SubmoduleWalk generator = SubmoduleWalk.forIndex(db);
		assertTrue(generator.next());
		assertNull(generator.getConfigUrl());
		assertNull(generator.getConfigUpdate());

		FileBasedConfig modulesConfig = new FileBasedConfig(new File(
				db.getWorkTree()
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_PATH
		String url = "../sub.git";
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_URL
		modulesConfig.save();

		try {
			new SubmoduleInitCommand(db).call();
			fail("Exception not thrown");
		} catch (JGitInternalException e) {
			assertTrue(e.getCause() instanceof IOException);
		}
	}

	private String addSubmoduleToIndex() throws IOException {
		final ObjectId id = ObjectId
				.fromString("abcd1234abcd1234abcd1234abcd1234abcd1234");
		final String path = "sub";
		DirCache cache = db.lockDirCache();
		DirCacheEditor editor = cache.editor();
		editor.add(new PathEdit(path) {

			public void apply(DirCacheEntry ent) {
				ent.setFileMode(FileMode.GITLINK);
				ent.setObjectId(id);
			}
		});
		editor.commit();
		return path;
	}
