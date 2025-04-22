		try (Repository subRepo = gen.getRepository()) {
			assertNotNull(subRepo);
			assertEquals(modulesGitDir.getAbsolutePath()
					subRepo.getDirectory().getAbsolutePath());
			assertEquals(new File(db.getWorkTree()
					subRepo.getWorkTree().getAbsolutePath());
		}
