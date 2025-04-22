		IFile iFile2 = testRepo.getIFile(iProject, file2);

		testRepo.checkoutBranch(MASTER);

		RemoteResourceMappingContext context = prepareContext(MASTER, BRANCH);
		assertFalse(context.hasRemoteChange(iFile1, new NullProgressMonitor()));
		assertTrue(context.hasRemoteChange(iFile2, new NullProgressMonitor()));
	}
