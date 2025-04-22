			@Override
			public void run(IProgressMonitor pm) throws CoreException {
				SubMonitor progress = SubMonitor.convert(pm, 2);

				progress.subTask(MessageFormat.format(
						CoreText.CherryPickOperation_cherryPicking,
						commit.name()));
				try (Git git = new Git(repo)) {
					CherryPickCommand command = git.cherryPick()
							.include(commit.getId());
					MergeStrategy strategy = Activator.getDefault()
							.getPreferredMergeStrategy();
					if (strategy != null) {
						command.setStrategy(strategy);
					}
					if (parentIndex >= 0
							&& parentIndex < commit.getParentCount()) {
						command.setMainlineParentNumber(parentIndex + 1);
					}
					result = command.call();
				} catch (GitAPIException e) {
					throw new TeamException(e.getLocalizedMessage(),
							e.getCause());
