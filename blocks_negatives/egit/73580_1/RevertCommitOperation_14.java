				RevertCommand command = new Git(repo).revert();
				MergeStrategy strategy = Activator.getDefault()
						.getPreferredMergeStrategy();
				if (strategy != null) {
					command.setStrategy(strategy);
				}
				for (RevCommit commit : commits)
					command.include(commit);
				try {
