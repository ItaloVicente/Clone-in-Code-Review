		Repository localDb = Git
			.cloneRepository()
			.setDirectory(directory)
			.setURI(remoteDb.getDirectory().toURI().toString())
			.call()
			.getRepository();
