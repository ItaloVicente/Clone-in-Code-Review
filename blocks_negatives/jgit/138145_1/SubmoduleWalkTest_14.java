		Repository subRepo = gen.getRepository();
		assertNotNull(subRepo);
		assertEquals(modulesGitDir.getAbsolutePath(),
				subRepo.getDirectory().getAbsolutePath());
		assertEquals(new File(db.getWorkTree(), path).getAbsolutePath(),
				subRepo.getWorkTree().getAbsolutePath());
		subRepo.close();
