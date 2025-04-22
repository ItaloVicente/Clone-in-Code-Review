		FileRepository r = (FileRepository) new FileRepositoryBuilder()
				.setWorkTree(repo1Parent).build();
		assertEqualsPath(theDir, r.getDirectory());
		assertEqualsPath(repo1Parent, r.getWorkTree());
		assertEqualsPath(new File(theDir, "index"), r.getIndexFile());
		assertEqualsPath(new File(theDir, Constants.OBJECTS), r.getObjectDatabase()
				.getDirectory());
