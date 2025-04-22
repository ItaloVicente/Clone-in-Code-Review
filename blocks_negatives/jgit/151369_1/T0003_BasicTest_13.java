		FileRepository r = (FileRepository) new FileRepositoryBuilder()
				.setGitDir(theDir).build();
		assertEqualsPath(theDir, r.getDirectory());
		assertEqualsPath(workdir, r.getWorkTree());
		assertEqualsPath(new File(theDir, "index"), r.getIndexFile());
		assertEqualsPath(new File(theDir, Constants.OBJECTS), r.getObjectDatabase()
				.getDirectory());
