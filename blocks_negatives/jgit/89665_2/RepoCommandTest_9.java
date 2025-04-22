			RepoCommand command = new RepoCommand(remoteDb);
			command.setPath(tempDb.getWorkTree().getAbsolutePath() + "/manifest.xml")
				.setURI(rootUri)
				.setRecordSubmoduleLabels(true)
				.call();
			File directory = createTempDirectory("testBareRepo");
			try (Repository localDb = Git.cloneRepository()
					.setDirectory(directory)
					.setURI(remoteDb.getDirectory().toURI().toString()).call()
					.getRepository();) {
				File gitattributes = new File(localDb.getWorkTree(),
