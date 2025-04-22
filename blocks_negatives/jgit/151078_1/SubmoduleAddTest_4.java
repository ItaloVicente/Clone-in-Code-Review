			SubmoduleWalk generator = SubmoduleWalk.forIndex(db);
			generator.loadModulesConfig();
			assertTrue(generator.next());
			assertEquals(path, generator.getModuleName());
			assertEquals(path, generator.getPath());
			assertEquals(commit, generator.getObjectId());
			assertEquals(uri, generator.getModulesUrl());
			assertEquals(path, generator.getModulesPath());
			assertEquals(uri, generator.getConfigUrl());
			try (Repository subModRepo = generator.getRepository()) {
				assertNotNull(subModRepo);
				assertEquals(subCommit, commit);
