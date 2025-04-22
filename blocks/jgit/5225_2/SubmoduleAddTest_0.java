
	@Test
	public void addSubmoduleWithExistingSubmoduleDefined() throws Exception {
		String path1 = "sub1";
		String path2 = "sub2";

		FileBasedConfig modulesConfig = new FileBasedConfig(new File(
				db.getWorkTree()
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				path1
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				path1
		modulesConfig.save();

		Git git = new Git(db);
		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		assertNotNull(git.commit().setMessage("create file").call());

		SubmoduleAddCommand command = new SubmoduleAddCommand(db);
		command.setPath(path2);
		String url2 = db.getDirectory().toURI().toString();
		command.setURI(url2);
		assertNotNull(command.call());

		modulesConfig.load();
		assertEquals(path1
				ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_PATH));
		assertEquals(url1
				ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_URL));
		assertEquals(path2
				ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_PATH));
		assertEquals(url2
				ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_URL));
	}
