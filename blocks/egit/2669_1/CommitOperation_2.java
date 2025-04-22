		for (Repository repo : repositories) {
			Git git = new Git(repo);
			CommitCommand command = git.commit();
			command.setAmend(amending);
			command.setAuthor(authorIdent);
			command.setCommitter(committerIdent);
