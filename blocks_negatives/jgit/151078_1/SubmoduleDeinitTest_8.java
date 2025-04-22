		SubmoduleWalk generator = SubmoduleWalk.forIndex(db);
		assertTrue(generator.next());
		File submoduleDir = new File(db.getWorkTree(), generator.getPath());
		assertTrue(submoduleDir.isDirectory());
		assertNotEquals(0, submoduleDir.list().length);
		return submoduleDir;
