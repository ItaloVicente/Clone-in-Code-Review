
	@Test
	public void repositoryWithRelativeUriSubmodule() throws Exception {
		writeTrashFile("file.txt"
		Git git = Git.wrap(db);
		git.add().addFilepattern("file.txt").call();
		git.commit().setMessage("create file").call();

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

		FileBasedConfig config = db.getConfig();
		config.setString(ConfigConstants.CONFIG_REMOTE_SECTION
				Constants.DEFAULT_REMOTE_NAME
				base);
		config.save();

		FileBasedConfig modulesConfig = new FileBasedConfig(new File(
				db.getWorkTree()
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_PATH
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_URL
		modulesConfig.save();

		Repository subRepo = Git.cloneRepository()
				.setURI(db.getDirectory().toURI().toString())
				.setDirectory(new File(db.getWorkTree()
				.getRepository();
		assertNotNull(subRepo);

		SubmoduleWalk generator = SubmoduleWalk.forIndex(db);
		assertTrue(generator.next());
		assertNull(generator.getConfigUrl());
		assertEquals(current

		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_URL
		modulesConfig.save();

		SubmoduleSyncCommand command = new SubmoduleSyncCommand(db);
		Map<String
		assertNotNull(synced);
		assertEquals(1
		Entry<String
		assertEquals(path

		generator = SubmoduleWalk.forIndex(db);
		assertTrue(generator.next());
		StoredConfig submoduleConfig = generator.getRepository().getConfig();
				ConfigConstants.CONFIG_REMOTE_SECTION
				Constants.DEFAULT_REMOTE_NAME
	}
