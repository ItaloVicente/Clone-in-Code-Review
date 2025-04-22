		try (Git git = command.call()) {
			Repository repository = git.getRepository();
			assertNotNull(repository);
			assertEqualsFile(wt
			assertEqualsFile(gitDir
		}
