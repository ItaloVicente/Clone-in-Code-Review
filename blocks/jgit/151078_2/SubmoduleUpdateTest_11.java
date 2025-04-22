		try (SubmoduleWalk generator = SubmoduleWalk.forIndex(db)) {
			assertTrue(generator.next());
			try (Repository subRepo = generator.getRepository()) {
				assertNotNull(subRepo);
				assertEquals(commit
			}
