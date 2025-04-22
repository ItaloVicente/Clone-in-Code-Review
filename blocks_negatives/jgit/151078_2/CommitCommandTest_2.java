			SubmoduleWalk generator = SubmoduleWalk.forIndex(db);
			assertTrue(generator.next());
			assertEquals(path, generator.getPath());
			assertEquals(commit2, generator.getObjectId());
			assertEquals(uri, generator.getModulesUrl());
			assertEquals(path, generator.getModulesPath());
			assertEquals(uri, generator.getConfigUrl());
			try (Repository subModRepo = generator.getRepository()) {
				assertNotNull(subModRepo);
