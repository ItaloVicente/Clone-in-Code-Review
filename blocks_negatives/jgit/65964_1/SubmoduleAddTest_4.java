		SubmoduleAddCommand command = new SubmoduleAddCommand(db);
		command.setPath(path2);
		String url2 = db.getDirectory().toURI().toString();
		command.setURI(url2);
		Repository r = command.call();
		assertNotNull(r);
		addRepoToClose(r);
