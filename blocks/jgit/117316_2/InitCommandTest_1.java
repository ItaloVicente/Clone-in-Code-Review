		try (Git git = command.call()) {
			Repository repository = git.getRepository();
			assertNotNull(repository);
			assertEqualsFile(gitDir
			assertEqualsFile(new File(reader.getProperty("user.dir"))
					repository.getWorkTree());
		}
