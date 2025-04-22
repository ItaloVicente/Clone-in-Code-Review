		Repository child =
			Git.cloneRepository().setURI(groupADb.getDirectory().toURI().toString())
				.setDirectory(createUniqueTestGitDir(true))
				.setBare(true).call().getRepository();
		Repository dest = Git.cloneRepository()
			.setURI(db.getDirectory().toURI().toString()).setDirectory(createUniqueTestGitDir(true))
			.setBare(true).call().getRepository();
		boolean fetchSlash = false;
		boolean baseSlash = false;
		do {
