		try (Repository subRepo = gen.getRepository()) {
			assertNotNull(subRepo);
			assertEqualsFile(modulesGitDir
			assertEqualsFile(new File(db.getWorkTree()
					subRepo.getWorkTree());
			subRepo.close();
			assertFalse(gen.next());
		}
