		SubmoduleWalk gen = SubmoduleWalk.forIndex(db);
		assertTrue(gen.next());
		assertEquals(path, gen.getPath());
		assertEquals(id, gen.getObjectId());
		assertEquals(new File(db.getWorkTree(), path), gen.getDirectory());
		assertNull(gen.getConfigUpdate());
		assertNull(gen.getConfigUrl());
		assertNull(gen.getModulesPath());
		assertNull(gen.getModulesUpdate());
		assertNull(gen.getModulesUrl());
		try (Repository subRepo = gen.getRepository()) {
			assertNotNull(subRepo);
			assertEquals(modulesGitDir.getAbsolutePath(),
					subRepo.getDirectory().getAbsolutePath());
			assertEquals(new File(db.getWorkTree(), path).getAbsolutePath(),
					subRepo.getWorkTree().getAbsolutePath());
