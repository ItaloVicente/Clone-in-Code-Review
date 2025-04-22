		Repository repository = command.call().getRepository();
		addRepoToClose(repository);
		assertNotNull(repository);
		assertEqualsFile(new File(reader.getProperty("user.dir")),
				repository.getDirectory());
		assertNull(repository.getWorkTree());
