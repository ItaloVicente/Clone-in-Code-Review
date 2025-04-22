		Repository repository = command.call().getRepository();
		addRepoToClose(repository);
		assertNotNull(repository);
		assertEqualsFile(new File(reader.getProperty("user.dir"), ".git"),
				repository.getDirectory());
		assertEqualsFile(new File(reader.getProperty("user.dir")),
				repository.getWorkTree());
