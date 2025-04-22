		try (FileRepository r = (FileRepository) new FileRepositoryBuilder()
				.setGitDir(theDir).build()) {
			assertEqualsPath(theDir
			assertEqualsPath(workdir
			assertEqualsPath(new File(theDir
			assertEqualsPath(new File(theDir
					r.getObjectDatabase().getDirectory());
		}
