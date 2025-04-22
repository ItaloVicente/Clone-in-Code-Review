		try (TestRepository<?> subTr = new TestRepository<>(subRepo)) {
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
			config.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
					ConfigConstants.CONFIG_KEY_URL
			config.save();

			FileBasedConfig modulesConfig = new FileBasedConfig(
					new File(db.getWorkTree()
					db.getFS());
			modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
					path
			modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
					path
			modulesConfig.save();

			ObjectId newId = subTr.branch(Constants.HEAD).commit().create()
					.copy();

			SubmoduleStatusCommand command = new SubmoduleStatusCommand(db);
			Map<String
			assertNotNull(statuses);
			assertEquals(1
			Entry<String
					.iterator().next();
			assertNotNull(module);
			assertEquals(path
			SubmoduleStatus status = module.getValue();
			assertNotNull(status);
			assertEquals(path
			assertEquals(id
			assertEquals(newId
			assertEquals(SubmoduleStatusType.REV_CHECKED_OUT
		}
