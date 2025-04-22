		comparator = new GitResourceVariantComparator(dataSet);
	}

	@After
	public void clearGitResources() throws Exception {
		testRepo.disconnect(iProject);
		testRepo.dispose();
		repo = null;
		super.tearDown();
