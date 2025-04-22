			try (SubmoduleWalk generator = SubmoduleWalk.forIndex(db)) {
				generator.loadModulesConfig();
				assertTrue(generator.next());
				assertEquals(path
				assertEquals(path
				assertEquals(commit
				assertEquals(uri
				assertEquals(path
				assertEquals(uri
				try (Repository subModRepo = generator.getRepository()) {
					assertNotNull(subModRepo);
					assertEquals(subCommit
				}
