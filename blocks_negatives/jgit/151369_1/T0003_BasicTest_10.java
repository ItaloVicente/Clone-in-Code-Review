				.build();
		assertEqualsPath(theDir, r.getDirectory());
		assertEqualsPath(repo1Parent.getParentFile(), r.getWorkTree());
		assertEqualsPath(new File(theDir, "index"), r.getIndexFile());
		assertEqualsPath(new File(theDir, Constants.OBJECTS), r.getObjectDatabase()
				.getDirectory());
