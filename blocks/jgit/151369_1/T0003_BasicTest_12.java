		try (FileRepository r = (FileRepository) new FileRepositoryBuilder()
				.setWorkTree(repo1Parent).build()) {
			assertEqualsPath(theDir
			assertEqualsPath(repo1Parent
			assertEqualsPath(new File(theDir
			assertEqualsPath(new File(theDir
					r.getObjectDatabase().getDirectory());
		}
