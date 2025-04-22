		SubmoduleAddCommand command = new SubmoduleAddCommand(db);
		String path = "sub";
		String uri = "./.git";
		command.setPath(path);
		command.setURI(uri);
		Repository repo = command.call();
		assertNotNull(repo);
		addRepoToClose(repo);
