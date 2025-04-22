		Repository subRepo = gen.getRepository();
		assertNotNull(subRepo);
		assertEqualsFile(modulesGitDir, subRepo.getDirectory());
		assertEqualsFile(new File(db.getWorkTree(), path),
				subRepo.getWorkTree());
		subRepo.close();
		assertFalse(gen.next());
