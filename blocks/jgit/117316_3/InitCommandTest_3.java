		try (Git git = command.call()) {
			Repository repository = git.getRepository();
			assertNotNull(repository);
			assertEqualsFile(new File(reader.getProperty("user.dir"))
					repository.getDirectory());
			assertNull(repository.getWorkTree());
		}
