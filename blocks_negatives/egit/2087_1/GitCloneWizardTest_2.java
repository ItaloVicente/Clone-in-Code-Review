		remoteBranches.assertRemoteBranches(SampleTestRepository.FIX, Constants.MASTER);
		remoteBranches.selectBranches(SampleTestRepository.FIX, Constants.MASTER);

		WorkingCopyPage workingCopy = remoteBranches.nextToWorkingCopy();
		workingCopy.setDirectory(destRepo.toString());

		workingCopy.assertDirectory(destRepo.toString());
		workingCopy.assertBranch(Constants.MASTER);
		workingCopy.assertRemoteName(Constants.DEFAULT_REMOTE_NAME);
		workingCopy.waitForCreate();

		Repository repository = new FileRepository(new File(destRepo, Constants.DOT_GIT));
		assertNotNull(repository.resolve("origin/master"));
		assertEquals(repository.resolve("master"), repository
				.resolve("origin/master"));
		assertNotNull(repository.resolve(Constants.R_TAGS + SampleTestRepository.v1_0_name).name());
		int refs = repository.getAllRefs().size();
		assertTrue(refs >= 4);
		assertTrue(new File(destRepo, SampleTestRepository.A_txt_name).exists());
		DirCacheEntry fileEntry = null;
		DirCache dc = repository.lockDirCache();
		fileEntry = dc.getEntry(SampleTestRepository.A_txt_name);
		dc.unlock();
		assertNotNull(fileEntry);
		assertEquals(0,
				ResourcesPlugin.getWorkspace().getRoot().getProjects().length);
