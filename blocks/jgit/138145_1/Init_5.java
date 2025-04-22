		Repository repository;
		try {
			repository = command.call().getRepository();
			outw.println(MessageFormat.format(
					CLIText.get().initializedEmptyGitRepositoryIn
					repository.getDirectory().getAbsolutePath()));
		} catch (GitAPIException | IOException e) {
			throw die(e.getMessage()
		}
