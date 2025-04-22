			try (SubmoduleWalk generator = SubmoduleWalk.forIndex(db)) {
				generator.loadModulesConfig();
				assertTrue(generator.next());
				assertEquals(name
				assertEquals(path
				assertEquals(commit
				assertEquals(uri
				assertEquals(path
				assertEquals(uri
				try (Repository subModRepo = generator.getRepository()) {
					assertNotNull(subModRepo);
					assertEquals(subCommit
				}
