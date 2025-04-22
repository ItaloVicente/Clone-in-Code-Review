		CloneCommand command = Git.cloneRepository();
		command.setBranch("tag-initial");
		command.setDirectory(directory);
		command.setURI(fileUri());
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());

		assertNotNull(git2);
		ObjectId taggedCommit = db.resolve("tag-initial^{commit}");
		assertEquals(taggedCommit.name(), git2
				.getRepository().getFullBranch());
