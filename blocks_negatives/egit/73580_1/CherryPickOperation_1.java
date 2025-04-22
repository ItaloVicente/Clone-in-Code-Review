				CherryPickCommand command = new Git(repo).cherryPick().include(
						commit.getId());
				MergeStrategy strategy = Activator.getDefault()
						.getPreferredMergeStrategy();
				if (strategy != null) {
					command.setStrategy(strategy);
				}
				try {
