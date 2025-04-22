		try (SubmoduleWalk generator = SubmoduleWalk.forIndex(db)) {
			assertTrue(generator.next());
			File submoduleDir = new File(db.getWorkTree()
			assertTrue(submoduleDir.isDirectory());
			assertNotEquals(0
			return submoduleDir;
		}
