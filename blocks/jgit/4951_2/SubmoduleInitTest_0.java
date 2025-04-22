
	@Test
	public void repositoryWithUninitializedModuleRelativePath()
			throws IOException
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

		SubmoduleWalk generator = SubmoduleWalk.forIndex(db);
		assertTrue(generator.next());
		assertNull(generator.getConfigUrl());
		assertNull(generator.getConfigUpdate());

		FileBasedConfig modulesConfig = new FileBasedConfig(new File(
				db.getWorkTree()
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_PATH
		String url = "../submodule-remote";
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
		assertEquals(new File(db.getWorkTree()
				generator.getConfigUrl());
		assertEquals(update
	}

