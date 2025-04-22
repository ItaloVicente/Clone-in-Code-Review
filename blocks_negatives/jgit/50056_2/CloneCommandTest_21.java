		CloneCommand command = Git.cloneRepository();
		command.setDirectory(directory);
		command.setURI(fileUri());
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());
		assertFalse(git2
				.getRepository()
				.getConfig()
				.getBoolean(ConfigConstants.CONFIG_BRANCH_SECTION, "test",
						ConfigConstants.CONFIG_KEY_REBASE, false));
