		SubmoduleWalk generator = SubmoduleWalk.forIndex(db);
		assertTrue(generator.next());
		assertEquals(path, generator.getPath());
		assertEquals(commit, generator.getObjectId());
		assertEquals(uri, generator.getModulesUrl());
		assertEquals(path, generator.getModulesPath());
		String fullUri = db.getDirectory().getAbsolutePath();
		if (File.separatorChar == '\\')
			fullUri = fullUri.replace('\\', '/');
		assertEquals(fullUri, generator.getConfigUrl());
		Repository subModRepo = generator.getRepository();
		assertNotNull(subModRepo);
		assertEquals(
				fullUri,
				subModRepo
						.getConfig()
						.getString(ConfigConstants.CONFIG_REMOTE_SECTION,
								Constants.DEFAULT_REMOTE_NAME,
								ConfigConstants.CONFIG_KEY_URL));
		subModRepo.close();
		assertEquals(commit, repo.resolve(Constants.HEAD));
