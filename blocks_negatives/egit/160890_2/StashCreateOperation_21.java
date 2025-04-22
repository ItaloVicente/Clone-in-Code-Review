		IWorkspaceRunnable action = new IWorkspaceRunnable() {

			@Override
			public void run(IProgressMonitor pm) throws CoreException {
				try {
					StashCreateCommand command = Git.wrap(repository).stashCreate();
					if (message != null)
						command.setWorkingDirectoryMessage(message);
					command.setIncludeUntracked(includeUntracked);
					commit = command.call();
				} catch (JGitInternalException | GitAPIException e) {
					throw new TeamException(e.getLocalizedMessage(),
							e.getCause());
				} finally {
					if (commit != null) {
						repository.notifyIndexChanged(true);
					}
