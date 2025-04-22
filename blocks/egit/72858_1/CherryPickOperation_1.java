				try (Git git = new Git(repo)) {
					CherryPickCommand command = git.cherryPick()
							.include(commit.getId());
					MergeStrategy strategy = Activator.getDefault()
							.getPreferredMergeStrategy();
					if (strategy != null) {
						command.setStrategy(strategy);
					}
