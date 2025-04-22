		Repository repository = command.call().getRepository();
		addRepoToClose(repository);
		assertNotNull(repository);
		assertEqualsFile(gitDir, repository.getDirectory());
		assertEqualsFile(new File(reader.getProperty("user.dir")),
				repository.getWorkTree());
