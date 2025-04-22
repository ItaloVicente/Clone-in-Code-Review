		File directory = createTempDirectory("testCloneRepositoryWithShallowSince");
		CloneCommand command = Git.cloneRepository();
		command.setDirectory(directory);
		command.setURI(fileUri());
		command.setShallowSince(Instant.ofEpochSecond(commit.getCommitTime()));
		command.setBranchesToClone(Set.of("refs/heads/test"));
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());
