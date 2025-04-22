			RepositoryState state = repo.getRepositoryState();
			if (!state.canCommit())
				throw new WrongRepositoryStateException(MessageFormat.format(
						JGitText.get().cannotCommitOnARepoWithState
						state.name()));
			processOptions(state);

