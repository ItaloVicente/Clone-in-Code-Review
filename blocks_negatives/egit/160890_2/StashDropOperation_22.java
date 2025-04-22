		IWorkspaceRunnable action = new IWorkspaceRunnable() {

			@Override
			public void run(IProgressMonitor pm) throws CoreException {
				StashDropCommand command = Git.wrap(repo).stashDrop();
				command.setStashRef(index);
				try {
					command.call();
					repo.fireEvent(new RefsChangedEvent());
				} catch (JGitInternalException | GitAPIException e) {
					throw new TeamException(e.getLocalizedMessage(),
							e.getCause());
				}
