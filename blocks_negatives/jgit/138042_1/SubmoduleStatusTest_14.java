		TestRepository<?> subTr = new TestRepository<>(subRepo);
		ObjectId id = subTr.branch(Constants.HEAD).commit().create().copy();

		DirCache cache = db.lockDirCache();
		DirCacheEditor editor = cache.editor();
		editor.add(new PathEdit(path) {

			@Override
			public void apply(DirCacheEntry ent) {
				ent.setFileMode(FileMode.GITLINK);
				ent.setObjectId(id);
			}
		});
		editor.commit();

		StoredConfig config = db.getConfig();
		config.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION, path,
				ConfigConstants.CONFIG_KEY_URL, url);
		config.save();

		FileBasedConfig modulesConfig = new FileBasedConfig(new File(
				db.getWorkTree(), Constants.DOT_GIT_MODULES), db.getFS());
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION, path,
				ConfigConstants.CONFIG_KEY_PATH, path);
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION, path,
				ConfigConstants.CONFIG_KEY_URL, url);
		modulesConfig.save();

		ObjectId newId = subTr.branch(Constants.HEAD).commit().create().copy();

		SubmoduleStatusCommand command = new SubmoduleStatusCommand(db);
		Map<String, SubmoduleStatus> statuses = command.call();
		assertNotNull(statuses);
		assertEquals(1, statuses.size());
		Entry<String, SubmoduleStatus> module = statuses.entrySet().iterator()
				.next();
		assertNotNull(module);
		assertEquals(path, module.getKey());
		SubmoduleStatus status = module.getValue();
		assertNotNull(status);
		assertEquals(path, status.getPath());
		assertEquals(id, status.getIndexId());
		assertEquals(newId, status.getHeadId());
		assertEquals(SubmoduleStatusType.REV_CHECKED_OUT, status.getType());
