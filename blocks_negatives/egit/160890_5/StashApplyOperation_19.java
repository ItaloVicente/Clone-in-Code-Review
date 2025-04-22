		IWorkspaceRunnable action = new IWorkspaceRunnable() {

			@Override
			public void run(IProgressMonitor pm) throws CoreException {
				SubMonitor progress = SubMonitor.convert(pm, 3);
				try {
					IProject[] validProjects = ProjectUtil
							.getValidOpenProjects(repository);
					progress.worked(1);
					StashApplyCommand command = Git.wrap(repository)
							.stashApply().setStashRef(commit.name());
					MergeStrategy strategy = Activator.getDefault()
							.getPreferredMergeStrategy();
					if (strategy != null) {
						command.setStrategy(strategy);
					}
					command.call();
					progress.worked(1);
					ProjectUtil.refreshValidProjects(validProjects,
							progress.newChild(1));
				} catch (JGitInternalException | GitAPIException e) {
					throw new TeamException(e.getLocalizedMessage(),
							e.getCause());
