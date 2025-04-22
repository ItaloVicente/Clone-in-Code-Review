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
