		Repository existingRepo = Activator
				.getDefault()
				.getRepositoryCache()
				.lookupRepository(
						new File(workdir2, Constants.DOT_GIT));
		repository2 = new TestRepository(existingRepo);
