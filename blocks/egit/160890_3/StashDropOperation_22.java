		IWorkspaceRunnable action = pm -> {
			StashDropCommand command = Git.wrap(repo).stashDrop();
			command.setStashRef(index);
			try {
				command.call();
				repo.fireEvent(new RefsChangedEvent());
			} catch (JGitInternalException | GitAPIException e) {
				throw new TeamException(e.getLocalizedMessage(),
						e.getCause());
