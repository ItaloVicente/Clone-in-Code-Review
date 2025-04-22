		IWorkspaceRunnable action = pm -> {
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
