		command.setURI(uri);
		Repository repo = command.call();
		assertNotNull(repo);
		addRepoToClose(repo);
		git.add().addFilepattern(path)
				.addFilepattern(Constants.DOT_GIT_MODULES).call();
