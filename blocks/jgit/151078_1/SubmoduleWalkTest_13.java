		try (SubmoduleWalk gen = SubmoduleWalk.forIndex(db)) {
			assertTrue(gen.next());
			assertEquals(path
			assertEquals(id
			assertEquals(new File(db.getWorkTree()
			assertNull(gen.getConfigUpdate());
			assertNull(gen.getConfigUrl());
			assertNull(gen.getModulesPath());
			assertNull(gen.getModulesUpdate());
			assertNull(gen.getModulesUrl());
			try (Repository subRepo = gen.getRepository()) {
				assertNotNull(subRepo);
				assertEquals(modulesGitDir.getAbsolutePath()
						subRepo.getDirectory().getAbsolutePath());
				assertEquals(new File(db.getWorkTree()
						subRepo.getWorkTree().getAbsolutePath());
			}
			assertFalse(gen.next());
