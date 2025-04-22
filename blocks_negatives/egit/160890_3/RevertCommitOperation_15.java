		IWorkspaceRunnable action = new IWorkspaceRunnable() {

			@Override
			public void run(IProgressMonitor pm) throws CoreException {
				SubMonitor progress = SubMonitor.convert(pm, 2);
				progress.subTask(MessageFormat.format(
						CoreText.RevertCommitOperation_reverting,
						Integer.valueOf(commits.size())));
				try (Git git = new Git(repo)) {
					RevertCommand command = git.revert();
					MergeStrategy strategy = Activator.getDefault()
							.getPreferredMergeStrategy();
					if (strategy != null) {
						command.setStrategy(strategy);
					}
					for (RevCommit commit : commits) {
						command.include(commit);
					}
					newHead = command.call();
					reverted = command.getRevertedRefs();
					result = command.getFailingResult();
					progress.worked(1);
					ProjectUtil.refreshValidProjects(
							ProjectUtil.getValidOpenProjects(repo),
							progress.newChild(1));
				} catch (GitAPIException e) {
					throw new TeamException(e.getLocalizedMessage(),
							e.getCause());
